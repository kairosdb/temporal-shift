package org.kairosdb.temporal.h2;

import com.google.inject.Inject;
import io.temporal.api.enums.v1.WorkflowIdConflictPolicy;
import io.temporal.api.enums.v1.WorkflowIdReusePolicy;
import io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest;
import org.h2.jdbcx.JdbcDataSource;
import org.kairosdb.temporal.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.UUID;

public class DataStoreImpl implements DataStore
{
	private static final Logger logger = LoggerFactory.getLogger(DataStoreImpl.class);

	private static final String CREATE_TABLE = """
			CREATE TABLE IF NOT EXISTS workflow_executions (
			    namespace              VARCHAR(255)     NOT NULL,
			    workflow_id            VARCHAR(255)     NOT NULL,
			    run_id                 CHAR(36)         NOT NULL,
			    workflow_type_name     VARCHAR(255)     NOT NULL,
			    task_queue_name        VARCHAR(255)     NOT NULL,
			    input                  BINARY VARYING,
			    execution_timeout_secs BIGINT,
			    run_timeout_secs       BIGINT,
			    task_timeout_secs      BIGINT,
			    status                 VARCHAR(50)      NOT NULL DEFAULT 'RUNNING',
			    start_time             TIMESTAMP        NOT NULL,
			    request_id             VARCHAR(255),
			    PRIMARY KEY (namespace, workflow_id, run_id)
			)""";

	private static final String CREATE_RUNNING_IDX = """
			CREATE INDEX IF NOT EXISTS idx_wf_running
			    ON workflow_executions(namespace, workflow_id, status)""";

	private static final String SELECT_RUNNING = """
			SELECT run_id, request_id FROM workflow_executions
			WHERE namespace = ? AND workflow_id = ? AND status = 'RUNNING'""";

	private static final String INSERT_EXECUTION = """
			INSERT INTO workflow_executions
			    (namespace, workflow_id, run_id, workflow_type_name, task_queue_name,
			     input, execution_timeout_secs, run_timeout_secs, task_timeout_secs,
			     status, start_time, request_id)
			VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'RUNNING', ?, ?)""";

	private static final String UPDATE_STATUS = """
			UPDATE workflow_executions SET status = ?
			WHERE namespace = ? AND workflow_id = ? AND run_id = ?""";

	private final DataSource dataSource;

	@Inject
	public DataStoreImpl()
	{
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:./data/temporal;DB_CLOSE_DELAY=-1");
		ds.setUser("sa");
		ds.setPassword("");
		this.dataSource = ds;
		initSchema();
	}

	// Package-private for testing
	DataStoreImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
		initSchema();
	}

	private void initSchema()
	{
		try (Connection conn = dataSource.getConnection();
			 Statement stmt = conn.createStatement())
		{
			stmt.execute(CREATE_TABLE);
			stmt.execute(CREATE_RUNNING_IDX);
		}
		catch (SQLException e)
		{
			throw new RuntimeException("Failed to initialize H2 schema", e);
		}
	}

	@Override
	public WorkflowStartResult startWorkflowExecution(StartWorkflowExecutionRequest request)
	{
		String namespace = request.getNamespace();
		String workflowId = request.getWorkflowId();
		String requestId = request.getRequestId();
		WorkflowIdConflictPolicy conflictPolicy = request.getWorkflowIdConflictPolicy();
		WorkflowIdReusePolicy reusePolicy = request.getWorkflowIdReusePolicy();

		try (Connection conn = dataSource.getConnection())
		{
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			try
			{
				RunningExecution existing = findRunning(conn, namespace, workflowId);

				if (existing != null)
				{
					// Idempotent re-submit: same requestId as the running execution
					if (!requestId.isEmpty() && requestId.equals(existing.requestId))
					{
						conn.commit();
						return new WorkflowStartResult(existing.runId, false);
					}

					boolean useExisting =
							conflictPolicy == WorkflowIdConflictPolicy.WORKFLOW_ID_CONFLICT_POLICY_USE_EXISTING;
					boolean terminateExisting =
							conflictPolicy == WorkflowIdConflictPolicy.WORKFLOW_ID_CONFLICT_POLICY_TERMINATE_EXISTING
							|| reusePolicy == WorkflowIdReusePolicy.WORKFLOW_ID_REUSE_POLICY_TERMINATE_IF_RUNNING;
					boolean fail =
							conflictPolicy == WorkflowIdConflictPolicy.WORKFLOW_ID_CONFLICT_POLICY_FAIL
							|| reusePolicy == WorkflowIdReusePolicy.WORKFLOW_ID_REUSE_POLICY_REJECT_DUPLICATE;

					if (useExisting)
					{
						conn.commit();
						return new WorkflowStartResult(existing.runId, false);
					}

					if (terminateExisting)
					{
						updateStatus(conn, namespace, workflowId, existing.runId, "TERMINATED");
					}
					else if (fail)
					{
						conn.commit();
						throw new WorkflowAlreadyExistsException(existing.runId);
					}
					else
					{
						// Default: reject — a running workflow must be explicitly terminated
						conn.commit();
						throw new WorkflowAlreadyExistsException(existing.runId);
					}
				}

				String newRunId = UUID.randomUUID().toString();
				insertExecution(conn, request, newRunId);
				conn.commit();
				logger.info("Started workflow execution namespace={} workflowId={} runId={}", namespace, workflowId, newRunId);
				return new WorkflowStartResult(newRunId, true);
			}
			catch (WorkflowAlreadyExistsException e)
			{
				conn.rollback();
				throw e;
			}
			catch (SQLException e)
			{
				conn.rollback();
				throw new RuntimeException("Failed to start workflow execution", e);
			}
		}
		catch (SQLException e)
		{
			throw new RuntimeException("Failed to obtain database connection", e);
		}
	}

	private RunningExecution findRunning(Connection conn, String namespace, String workflowId) throws SQLException
	{
		try (PreparedStatement ps = conn.prepareStatement(SELECT_RUNNING))
		{
			ps.setString(1, namespace);
			ps.setString(2, workflowId);
			try (ResultSet rs = ps.executeQuery())
			{
				if (rs.next())
					return new RunningExecution(rs.getString("run_id"), rs.getString("request_id"));
				return null;
			}
		}
	}

	private void updateStatus(Connection conn, String namespace, String workflowId, String runId, String status)
			throws SQLException
	{
		try (PreparedStatement ps = conn.prepareStatement(UPDATE_STATUS))
		{
			ps.setString(1, status);
			ps.setString(2, namespace);
			ps.setString(3, workflowId);
			ps.setString(4, runId);
			ps.executeUpdate();
		}
	}

	private void insertExecution(Connection conn, StartWorkflowExecutionRequest request, String runId)
			throws SQLException
	{
		try (PreparedStatement ps = conn.prepareStatement(INSERT_EXECUTION))
		{
			ps.setString(1, request.getNamespace());
			ps.setString(2, request.getWorkflowId());
			ps.setString(3, runId);
			ps.setString(4, request.getWorkflowType().getName());
			ps.setString(5, request.getTaskQueue().getName());
			ps.setBytes(6, request.hasInput() ? request.getInput().toByteArray() : null);
			ps.setLong(7, request.getWorkflowExecutionTimeout().getSeconds());
			ps.setLong(8, request.getWorkflowRunTimeout().getSeconds());
			ps.setLong(9, request.getWorkflowTaskTimeout().getSeconds());
			ps.setTimestamp(10, Timestamp.from(Instant.now()));
			ps.setString(11, request.getRequestId().isEmpty() ? null : request.getRequestId());
			ps.executeUpdate();
		}
	}

	private record RunningExecution(String runId, String requestId) {}
}
