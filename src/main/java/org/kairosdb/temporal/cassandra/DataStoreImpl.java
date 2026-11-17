package org.kairosdb.temporal.cassandra;

import io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest;
import org.kairosdb.temporal.DataStore;

public class DataStoreImpl implements DataStore
{
	@Override
	public WorkflowStartResult startWorkflowExecution(StartWorkflowExecutionRequest request)
	{
		throw new UnsupportedOperationException("Cassandra DataStore not yet implemented");
	}
}
