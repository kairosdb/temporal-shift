package org.kairosdb.temporal;

import io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest;

public interface DataStore
{
	record WorkflowStartResult(String runId, boolean started) {}

	class WorkflowAlreadyExistsException extends RuntimeException
	{
		private final String existingRunId;

		public WorkflowAlreadyExistsException(String existingRunId)
		{
			super("Workflow execution already running with runId: " + existingRunId);
			this.existingRunId = existingRunId;
		}

		public String getExistingRunId()
		{
			return existingRunId;
		}
	}

	WorkflowStartResult startWorkflowExecution(StartWorkflowExecutionRequest request);
}
