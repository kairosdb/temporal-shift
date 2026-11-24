package org.kairosdb.temporal;

import io.grpc.Status;
import io.grpc.StatusException;
import io.temporal.api.enums.v1.WorkflowIdConflictPolicy;
import io.temporal.api.enums.v1.WorkflowIdReusePolicy;
import io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest;

import java.util.HashMap;
import java.util.Map;

/**
 This class tracks workflows and activities.  Coordinates assigning work to workers.
 */
public class Orchestrator
{
	private Map<String, WorkManager> m_namespaceWorkManagers = new HashMap<>();


	private WorkManager getWorkManager(String namespace) throws StatusException
	{
		WorkManager workManager = m_namespaceWorkManagers.get(namespace);
		if (workManager == null)
			throw Status.NOT_FOUND.withDescription("Namespace not found: "+namespace)
					.asException();

	}

	public DataStore.WorkflowStartResult startWorkflowExecution(StartWorkflowExecutionRequest request) throws StatusException
	{
		String namespace = request.getNamespace();
		String workflowId = request.getWorkflowId();
		String requestId = request.getRequestId();
		WorkflowIdConflictPolicy conflictPolicy = request.getWorkflowIdConflictPolicy();
		WorkflowIdReusePolicy reusePolicy = request.getWorkflowIdReusePolicy();

		WorkManager workManager = getWorkManager(namespace);

		//Verify workflow is not already running - get a run id if it isn't and set it to running
		//Return some kind of workflow lock,

		//Log action to database - if failure remove running state and unlock

		//Submit action to workManager
	}
}
