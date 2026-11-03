package org.kairosdb.temporal;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.temporal.api.workflowservice.v1.*;

/**
 Implementation of the Temporal frontend gRPC service
 (temporal.api.workflowservice.v1.WorkflowService).

 Every RPC is currently stubbed out and responds with UNIMPLEMENTED.
 */
public class FrontendService extends WorkflowServiceGrpc.WorkflowServiceImplBase
{
	private static void unimplemented(String method, StreamObserver<?> responseObserver)
	{
		responseObserver.onError(Status.UNIMPLEMENTED
				.withDescription("Method temporal.api.workflowservice.v1.WorkflowService/" + method + " is not implemented")
				.asRuntimeException());
	}

	@Override
	public void registerNamespace(RegisterNamespaceRequest request, StreamObserver<RegisterNamespaceResponse> responseObserver)
	{
		unimplemented("RegisterNamespace", responseObserver);
	}

	@Override
	public void describeNamespace(DescribeNamespaceRequest request, StreamObserver<DescribeNamespaceResponse> responseObserver)
	{
		unimplemented("DescribeNamespace", responseObserver);
	}

	@Override
	public void listNamespaces(ListNamespacesRequest request, StreamObserver<ListNamespacesResponse> responseObserver)
	{
		unimplemented("ListNamespaces", responseObserver);
	}

	@Override
	public void updateNamespace(UpdateNamespaceRequest request, StreamObserver<UpdateNamespaceResponse> responseObserver)
	{
		unimplemented("UpdateNamespace", responseObserver);
	}

	@Override
	public void deprecateNamespace(DeprecateNamespaceRequest request, StreamObserver<DeprecateNamespaceResponse> responseObserver)
	{
		unimplemented("DeprecateNamespace", responseObserver);
	}

	@Override
	public void startWorkflowExecution(StartWorkflowExecutionRequest request, StreamObserver<StartWorkflowExecutionResponse> responseObserver)
	{
		unimplemented("StartWorkflowExecution", responseObserver);
	}

	@Override
	public void executeMultiOperation(ExecuteMultiOperationRequest request, StreamObserver<ExecuteMultiOperationResponse> responseObserver)
	{
		unimplemented("ExecuteMultiOperation", responseObserver);
	}

	@Override
	public void getWorkflowExecutionHistory(GetWorkflowExecutionHistoryRequest request, StreamObserver<GetWorkflowExecutionHistoryResponse> responseObserver)
	{
		unimplemented("GetWorkflowExecutionHistory", responseObserver);
	}

	@Override
	public void getWorkflowExecutionHistoryReverse(GetWorkflowExecutionHistoryReverseRequest request, StreamObserver<GetWorkflowExecutionHistoryReverseResponse> responseObserver)
	{
		unimplemented("GetWorkflowExecutionHistoryReverse", responseObserver);
	}

	@Override
	public void pollWorkflowTaskQueue(PollWorkflowTaskQueueRequest request, StreamObserver<PollWorkflowTaskQueueResponse> responseObserver)
	{
		unimplemented("PollWorkflowTaskQueue", responseObserver);
	}

	@Override
	public void respondWorkflowTaskCompleted(RespondWorkflowTaskCompletedRequest request, StreamObserver<RespondWorkflowTaskCompletedResponse> responseObserver)
	{
		unimplemented("RespondWorkflowTaskCompleted", responseObserver);
	}

	@Override
	public void respondWorkflowTaskFailed(RespondWorkflowTaskFailedRequest request, StreamObserver<RespondWorkflowTaskFailedResponse> responseObserver)
	{
		unimplemented("RespondWorkflowTaskFailed", responseObserver);
	}

	@Override
	public void pollActivityTaskQueue(PollActivityTaskQueueRequest request, StreamObserver<PollActivityTaskQueueResponse> responseObserver)
	{
		unimplemented("PollActivityTaskQueue", responseObserver);
	}

	@Override
	public void recordActivityTaskHeartbeat(RecordActivityTaskHeartbeatRequest request, StreamObserver<RecordActivityTaskHeartbeatResponse> responseObserver)
	{
		unimplemented("RecordActivityTaskHeartbeat", responseObserver);
	}

	@Override
	public void recordActivityTaskHeartbeatById(RecordActivityTaskHeartbeatByIdRequest request, StreamObserver<RecordActivityTaskHeartbeatByIdResponse> responseObserver)
	{
		unimplemented("RecordActivityTaskHeartbeatById", responseObserver);
	}

	@Override
	public void respondActivityTaskCompleted(RespondActivityTaskCompletedRequest request, StreamObserver<RespondActivityTaskCompletedResponse> responseObserver)
	{
		unimplemented("RespondActivityTaskCompleted", responseObserver);
	}

	@Override
	public void respondActivityTaskCompletedById(RespondActivityTaskCompletedByIdRequest request, StreamObserver<RespondActivityTaskCompletedByIdResponse> responseObserver)
	{
		unimplemented("RespondActivityTaskCompletedById", responseObserver);
	}

	@Override
	public void respondActivityTaskFailed(RespondActivityTaskFailedRequest request, StreamObserver<RespondActivityTaskFailedResponse> responseObserver)
	{
		unimplemented("RespondActivityTaskFailed", responseObserver);
	}

	@Override
	public void respondActivityTaskFailedById(RespondActivityTaskFailedByIdRequest request, StreamObserver<RespondActivityTaskFailedByIdResponse> responseObserver)
	{
		unimplemented("RespondActivityTaskFailedById", responseObserver);
	}

	@Override
	public void respondActivityTaskCanceled(RespondActivityTaskCanceledRequest request, StreamObserver<RespondActivityTaskCanceledResponse> responseObserver)
	{
		unimplemented("RespondActivityTaskCanceled", responseObserver);
	}

	@Override
	public void respondActivityTaskCanceledById(RespondActivityTaskCanceledByIdRequest request, StreamObserver<RespondActivityTaskCanceledByIdResponse> responseObserver)
	{
		unimplemented("RespondActivityTaskCanceledById", responseObserver);
	}

	@Override
	public void requestCancelWorkflowExecution(RequestCancelWorkflowExecutionRequest request, StreamObserver<RequestCancelWorkflowExecutionResponse> responseObserver)
	{
		unimplemented("RequestCancelWorkflowExecution", responseObserver);
	}

	@Override
	public void signalWorkflowExecution(SignalWorkflowExecutionRequest request, StreamObserver<SignalWorkflowExecutionResponse> responseObserver)
	{
		unimplemented("SignalWorkflowExecution", responseObserver);
	}

	@Override
	public void signalWithStartWorkflowExecution(SignalWithStartWorkflowExecutionRequest request, StreamObserver<SignalWithStartWorkflowExecutionResponse> responseObserver)
	{
		unimplemented("SignalWithStartWorkflowExecution", responseObserver);
	}

	@Override
	public void resetWorkflowExecution(ResetWorkflowExecutionRequest request, StreamObserver<ResetWorkflowExecutionResponse> responseObserver)
	{
		unimplemented("ResetWorkflowExecution", responseObserver);
	}

	@Override
	public void terminateWorkflowExecution(TerminateWorkflowExecutionRequest request, StreamObserver<TerminateWorkflowExecutionResponse> responseObserver)
	{
		unimplemented("TerminateWorkflowExecution", responseObserver);
	}

	@Override
	public void deleteWorkflowExecution(DeleteWorkflowExecutionRequest request, StreamObserver<DeleteWorkflowExecutionResponse> responseObserver)
	{
		unimplemented("DeleteWorkflowExecution", responseObserver);
	}

	@Override
	public void listOpenWorkflowExecutions(ListOpenWorkflowExecutionsRequest request, StreamObserver<ListOpenWorkflowExecutionsResponse> responseObserver)
	{
		unimplemented("ListOpenWorkflowExecutions", responseObserver);
	}

	@Override
	public void listClosedWorkflowExecutions(ListClosedWorkflowExecutionsRequest request, StreamObserver<ListClosedWorkflowExecutionsResponse> responseObserver)
	{
		unimplemented("ListClosedWorkflowExecutions", responseObserver);
	}

	@Override
	public void listWorkflowExecutions(ListWorkflowExecutionsRequest request, StreamObserver<ListWorkflowExecutionsResponse> responseObserver)
	{
		unimplemented("ListWorkflowExecutions", responseObserver);
	}

	@Override
	public void listArchivedWorkflowExecutions(ListArchivedWorkflowExecutionsRequest request, StreamObserver<ListArchivedWorkflowExecutionsResponse> responseObserver)
	{
		unimplemented("ListArchivedWorkflowExecutions", responseObserver);
	}

	@Override
	public void scanWorkflowExecutions(ScanWorkflowExecutionsRequest request, StreamObserver<ScanWorkflowExecutionsResponse> responseObserver)
	{
		unimplemented("ScanWorkflowExecutions", responseObserver);
	}

	@Override
	public void countWorkflowExecutions(CountWorkflowExecutionsRequest request, StreamObserver<CountWorkflowExecutionsResponse> responseObserver)
	{
		unimplemented("CountWorkflowExecutions", responseObserver);
	}

	@Override
	public void getSearchAttributes(GetSearchAttributesRequest request, StreamObserver<GetSearchAttributesResponse> responseObserver)
	{
		unimplemented("GetSearchAttributes", responseObserver);
	}

	@Override
	public void respondQueryTaskCompleted(RespondQueryTaskCompletedRequest request, StreamObserver<RespondQueryTaskCompletedResponse> responseObserver)
	{
		unimplemented("RespondQueryTaskCompleted", responseObserver);
	}

	@Override
	public void resetStickyTaskQueue(ResetStickyTaskQueueRequest request, StreamObserver<ResetStickyTaskQueueResponse> responseObserver)
	{
		unimplemented("ResetStickyTaskQueue", responseObserver);
	}

	@Override
	public void shutdownWorker(ShutdownWorkerRequest request, StreamObserver<ShutdownWorkerResponse> responseObserver)
	{
		unimplemented("ShutdownWorker", responseObserver);
	}

	@Override
	public void queryWorkflow(QueryWorkflowRequest request, StreamObserver<QueryWorkflowResponse> responseObserver)
	{
		unimplemented("QueryWorkflow", responseObserver);
	}

	@Override
	public void describeWorkflowExecution(DescribeWorkflowExecutionRequest request, StreamObserver<DescribeWorkflowExecutionResponse> responseObserver)
	{
		unimplemented("DescribeWorkflowExecution", responseObserver);
	}

	@Override
	public void describeTaskQueue(DescribeTaskQueueRequest request, StreamObserver<DescribeTaskQueueResponse> responseObserver)
	{
		unimplemented("DescribeTaskQueue", responseObserver);
	}

	@Override
	public void getClusterInfo(GetClusterInfoRequest request, StreamObserver<GetClusterInfoResponse> responseObserver)
	{
		unimplemented("GetClusterInfo", responseObserver);
	}

	@Override
	public void getSystemInfo(GetSystemInfoRequest request, StreamObserver<GetSystemInfoResponse> responseObserver)
	{
		unimplemented("GetSystemInfo", responseObserver);
	}

	@Override
	public void listTaskQueuePartitions(ListTaskQueuePartitionsRequest request, StreamObserver<ListTaskQueuePartitionsResponse> responseObserver)
	{
		unimplemented("ListTaskQueuePartitions", responseObserver);
	}

	@Override
	public void createSchedule(CreateScheduleRequest request, StreamObserver<CreateScheduleResponse> responseObserver)
	{
		unimplemented("CreateSchedule", responseObserver);
	}

	@Override
	public void describeSchedule(DescribeScheduleRequest request, StreamObserver<DescribeScheduleResponse> responseObserver)
	{
		unimplemented("DescribeSchedule", responseObserver);
	}

	@Override
	public void updateSchedule(UpdateScheduleRequest request, StreamObserver<UpdateScheduleResponse> responseObserver)
	{
		unimplemented("UpdateSchedule", responseObserver);
	}

	@Override
	public void patchSchedule(PatchScheduleRequest request, StreamObserver<PatchScheduleResponse> responseObserver)
	{
		unimplemented("PatchSchedule", responseObserver);
	}

	@Override
	public void listScheduleMatchingTimes(ListScheduleMatchingTimesRequest request, StreamObserver<ListScheduleMatchingTimesResponse> responseObserver)
	{
		unimplemented("ListScheduleMatchingTimes", responseObserver);
	}

	@Override
	public void deleteSchedule(DeleteScheduleRequest request, StreamObserver<DeleteScheduleResponse> responseObserver)
	{
		unimplemented("DeleteSchedule", responseObserver);
	}

	@Override
	public void listSchedules(ListSchedulesRequest request, StreamObserver<ListSchedulesResponse> responseObserver)
	{
		unimplemented("ListSchedules", responseObserver);
	}

	@Override
	public void countSchedules(CountSchedulesRequest request, StreamObserver<CountSchedulesResponse> responseObserver)
	{
		unimplemented("CountSchedules", responseObserver);
	}

	@Override
	public void updateWorkerBuildIdCompatibility(UpdateWorkerBuildIdCompatibilityRequest request, StreamObserver<UpdateWorkerBuildIdCompatibilityResponse> responseObserver)
	{
		unimplemented("UpdateWorkerBuildIdCompatibility", responseObserver);
	}

	@Override
	public void getWorkerBuildIdCompatibility(GetWorkerBuildIdCompatibilityRequest request, StreamObserver<GetWorkerBuildIdCompatibilityResponse> responseObserver)
	{
		unimplemented("GetWorkerBuildIdCompatibility", responseObserver);
	}

	@Override
	public void updateWorkerVersioningRules(UpdateWorkerVersioningRulesRequest request, StreamObserver<UpdateWorkerVersioningRulesResponse> responseObserver)
	{
		unimplemented("UpdateWorkerVersioningRules", responseObserver);
	}

	@Override
	public void getWorkerVersioningRules(GetWorkerVersioningRulesRequest request, StreamObserver<GetWorkerVersioningRulesResponse> responseObserver)
	{
		unimplemented("GetWorkerVersioningRules", responseObserver);
	}

	@Override
	public void getWorkerTaskReachability(GetWorkerTaskReachabilityRequest request, StreamObserver<GetWorkerTaskReachabilityResponse> responseObserver)
	{
		unimplemented("GetWorkerTaskReachability", responseObserver);
	}

	@Override
	public void describeDeployment(DescribeDeploymentRequest request, StreamObserver<DescribeDeploymentResponse> responseObserver)
	{
		unimplemented("DescribeDeployment", responseObserver);
	}

	@Override
	public void describeWorkerDeploymentVersion(DescribeWorkerDeploymentVersionRequest request, StreamObserver<DescribeWorkerDeploymentVersionResponse> responseObserver)
	{
		unimplemented("DescribeWorkerDeploymentVersion", responseObserver);
	}

	@Override
	public void listDeployments(ListDeploymentsRequest request, StreamObserver<ListDeploymentsResponse> responseObserver)
	{
		unimplemented("ListDeployments", responseObserver);
	}

	@Override
	public void getDeploymentReachability(GetDeploymentReachabilityRequest request, StreamObserver<GetDeploymentReachabilityResponse> responseObserver)
	{
		unimplemented("GetDeploymentReachability", responseObserver);
	}

	@Override
	public void getCurrentDeployment(GetCurrentDeploymentRequest request, StreamObserver<GetCurrentDeploymentResponse> responseObserver)
	{
		unimplemented("GetCurrentDeployment", responseObserver);
	}

	@Override
	public void setCurrentDeployment(SetCurrentDeploymentRequest request, StreamObserver<SetCurrentDeploymentResponse> responseObserver)
	{
		unimplemented("SetCurrentDeployment", responseObserver);
	}

	@Override
	public void setWorkerDeploymentCurrentVersion(SetWorkerDeploymentCurrentVersionRequest request, StreamObserver<SetWorkerDeploymentCurrentVersionResponse> responseObserver)
	{
		unimplemented("SetWorkerDeploymentCurrentVersion", responseObserver);
	}

	@Override
	public void describeWorkerDeployment(DescribeWorkerDeploymentRequest request, StreamObserver<DescribeWorkerDeploymentResponse> responseObserver)
	{
		unimplemented("DescribeWorkerDeployment", responseObserver);
	}

	@Override
	public void deleteWorkerDeployment(DeleteWorkerDeploymentRequest request, StreamObserver<DeleteWorkerDeploymentResponse> responseObserver)
	{
		unimplemented("DeleteWorkerDeployment", responseObserver);
	}

	@Override
	public void deleteWorkerDeploymentVersion(DeleteWorkerDeploymentVersionRequest request, StreamObserver<DeleteWorkerDeploymentVersionResponse> responseObserver)
	{
		unimplemented("DeleteWorkerDeploymentVersion", responseObserver);
	}

	@Override
	public void setWorkerDeploymentRampingVersion(SetWorkerDeploymentRampingVersionRequest request, StreamObserver<SetWorkerDeploymentRampingVersionResponse> responseObserver)
	{
		unimplemented("SetWorkerDeploymentRampingVersion", responseObserver);
	}

	@Override
	public void listWorkerDeployments(ListWorkerDeploymentsRequest request, StreamObserver<ListWorkerDeploymentsResponse> responseObserver)
	{
		unimplemented("ListWorkerDeployments", responseObserver);
	}

	@Override
	public void createWorkerDeployment(CreateWorkerDeploymentRequest request, StreamObserver<CreateWorkerDeploymentResponse> responseObserver)
	{
		unimplemented("CreateWorkerDeployment", responseObserver);
	}

	@Override
	public void createWorkerDeploymentVersion(CreateWorkerDeploymentVersionRequest request, StreamObserver<CreateWorkerDeploymentVersionResponse> responseObserver)
	{
		unimplemented("CreateWorkerDeploymentVersion", responseObserver);
	}

	@Override
	public void updateWorkerDeploymentVersionComputeConfig(UpdateWorkerDeploymentVersionComputeConfigRequest request, StreamObserver<UpdateWorkerDeploymentVersionComputeConfigResponse> responseObserver)
	{
		unimplemented("UpdateWorkerDeploymentVersionComputeConfig", responseObserver);
	}

	@Override
	public void validateWorkerDeploymentVersionComputeConfig(ValidateWorkerDeploymentVersionComputeConfigRequest request, StreamObserver<ValidateWorkerDeploymentVersionComputeConfigResponse> responseObserver)
	{
		unimplemented("ValidateWorkerDeploymentVersionComputeConfig", responseObserver);
	}

	@Override
	public void updateWorkerDeploymentVersionMetadata(UpdateWorkerDeploymentVersionMetadataRequest request, StreamObserver<UpdateWorkerDeploymentVersionMetadataResponse> responseObserver)
	{
		unimplemented("UpdateWorkerDeploymentVersionMetadata", responseObserver);
	}

	@Override
	public void setWorkerDeploymentManager(SetWorkerDeploymentManagerRequest request, StreamObserver<SetWorkerDeploymentManagerResponse> responseObserver)
	{
		unimplemented("SetWorkerDeploymentManager", responseObserver);
	}

	@Override
	public void updateWorkflowExecution(UpdateWorkflowExecutionRequest request, StreamObserver<UpdateWorkflowExecutionResponse> responseObserver)
	{
		unimplemented("UpdateWorkflowExecution", responseObserver);
	}

	@Override
	public void pollWorkflowExecutionUpdate(PollWorkflowExecutionUpdateRequest request, StreamObserver<PollWorkflowExecutionUpdateResponse> responseObserver)
	{
		unimplemented("PollWorkflowExecutionUpdate", responseObserver);
	}

	@Override
	public void startBatchOperation(StartBatchOperationRequest request, StreamObserver<StartBatchOperationResponse> responseObserver)
	{
		unimplemented("StartBatchOperation", responseObserver);
	}

	@Override
	public void stopBatchOperation(StopBatchOperationRequest request, StreamObserver<StopBatchOperationResponse> responseObserver)
	{
		unimplemented("StopBatchOperation", responseObserver);
	}

	@Override
	public void describeBatchOperation(DescribeBatchOperationRequest request, StreamObserver<DescribeBatchOperationResponse> responseObserver)
	{
		unimplemented("DescribeBatchOperation", responseObserver);
	}

	@Override
	public void listBatchOperations(ListBatchOperationsRequest request, StreamObserver<ListBatchOperationsResponse> responseObserver)
	{
		unimplemented("ListBatchOperations", responseObserver);
	}

	@Override
	public void pollNexusTaskQueue(PollNexusTaskQueueRequest request, StreamObserver<PollNexusTaskQueueResponse> responseObserver)
	{
		unimplemented("PollNexusTaskQueue", responseObserver);
	}

	@Override
	public void respondNexusTaskCompleted(RespondNexusTaskCompletedRequest request, StreamObserver<RespondNexusTaskCompletedResponse> responseObserver)
	{
		unimplemented("RespondNexusTaskCompleted", responseObserver);
	}

	@Override
	public void respondNexusTaskFailed(RespondNexusTaskFailedRequest request, StreamObserver<RespondNexusTaskFailedResponse> responseObserver)
	{
		unimplemented("RespondNexusTaskFailed", responseObserver);
	}

	@Override
	public void updateActivityOptions(UpdateActivityOptionsRequest request, StreamObserver<UpdateActivityOptionsResponse> responseObserver)
	{
		unimplemented("UpdateActivityOptions", responseObserver);
	}

	@Override
	public void updateWorkflowExecutionOptions(UpdateWorkflowExecutionOptionsRequest request, StreamObserver<UpdateWorkflowExecutionOptionsResponse> responseObserver)
	{
		unimplemented("UpdateWorkflowExecutionOptions", responseObserver);
	}

	@Override
	public void pauseActivity(PauseActivityRequest request, StreamObserver<PauseActivityResponse> responseObserver)
	{
		unimplemented("PauseActivity", responseObserver);
	}

	@Override
	public void unpauseActivity(UnpauseActivityRequest request, StreamObserver<UnpauseActivityResponse> responseObserver)
	{
		unimplemented("UnpauseActivity", responseObserver);
	}

	@Override
	public void resetActivity(ResetActivityRequest request, StreamObserver<ResetActivityResponse> responseObserver)
	{
		unimplemented("ResetActivity", responseObserver);
	}

	@Override
	public void createWorkflowRule(CreateWorkflowRuleRequest request, StreamObserver<CreateWorkflowRuleResponse> responseObserver)
	{
		unimplemented("CreateWorkflowRule", responseObserver);
	}

	@Override
	public void describeWorkflowRule(DescribeWorkflowRuleRequest request, StreamObserver<DescribeWorkflowRuleResponse> responseObserver)
	{
		unimplemented("DescribeWorkflowRule", responseObserver);
	}

	@Override
	public void deleteWorkflowRule(DeleteWorkflowRuleRequest request, StreamObserver<DeleteWorkflowRuleResponse> responseObserver)
	{
		unimplemented("DeleteWorkflowRule", responseObserver);
	}

	@Override
	public void listWorkflowRules(ListWorkflowRulesRequest request, StreamObserver<ListWorkflowRulesResponse> responseObserver)
	{
		unimplemented("ListWorkflowRules", responseObserver);
	}

	@Override
	public void triggerWorkflowRule(TriggerWorkflowRuleRequest request, StreamObserver<TriggerWorkflowRuleResponse> responseObserver)
	{
		unimplemented("TriggerWorkflowRule", responseObserver);
	}

	@Override
	public void recordWorkerHeartbeat(RecordWorkerHeartbeatRequest request, StreamObserver<RecordWorkerHeartbeatResponse> responseObserver)
	{
		unimplemented("RecordWorkerHeartbeat", responseObserver);
	}

	@Override
	public void listWorkers(ListWorkersRequest request, StreamObserver<ListWorkersResponse> responseObserver)
	{
		unimplemented("ListWorkers", responseObserver);
	}

	@Override
	public void countWorkers(CountWorkersRequest request, StreamObserver<CountWorkersResponse> responseObserver)
	{
		unimplemented("CountWorkers", responseObserver);
	}

	@Override
	public void updateTaskQueueConfig(UpdateTaskQueueConfigRequest request, StreamObserver<UpdateTaskQueueConfigResponse> responseObserver)
	{
		unimplemented("UpdateTaskQueueConfig", responseObserver);
	}

	@Override
	public void fetchWorkerConfig(FetchWorkerConfigRequest request, StreamObserver<FetchWorkerConfigResponse> responseObserver)
	{
		unimplemented("FetchWorkerConfig", responseObserver);
	}

	@Override
	public void updateWorkerConfig(UpdateWorkerConfigRequest request, StreamObserver<UpdateWorkerConfigResponse> responseObserver)
	{
		unimplemented("UpdateWorkerConfig", responseObserver);
	}

	@Override
	public void describeWorker(DescribeWorkerRequest request, StreamObserver<DescribeWorkerResponse> responseObserver)
	{
		unimplemented("DescribeWorker", responseObserver);
	}

	@Override
	public void pauseWorkflowExecution(PauseWorkflowExecutionRequest request, StreamObserver<PauseWorkflowExecutionResponse> responseObserver)
	{
		unimplemented("PauseWorkflowExecution", responseObserver);
	}

	@Override
	public void unpauseWorkflowExecution(UnpauseWorkflowExecutionRequest request, StreamObserver<UnpauseWorkflowExecutionResponse> responseObserver)
	{
		unimplemented("UnpauseWorkflowExecution", responseObserver);
	}

	@Override
	public void startActivityExecution(StartActivityExecutionRequest request, StreamObserver<StartActivityExecutionResponse> responseObserver)
	{
		unimplemented("StartActivityExecution", responseObserver);
	}

	@Override
	public void startNexusOperationExecution(StartNexusOperationExecutionRequest request, StreamObserver<StartNexusOperationExecutionResponse> responseObserver)
	{
		unimplemented("StartNexusOperationExecution", responseObserver);
	}

	@Override
	public void describeActivityExecution(DescribeActivityExecutionRequest request, StreamObserver<DescribeActivityExecutionResponse> responseObserver)
	{
		unimplemented("DescribeActivityExecution", responseObserver);
	}

	@Override
	public void describeNexusOperationExecution(DescribeNexusOperationExecutionRequest request, StreamObserver<DescribeNexusOperationExecutionResponse> responseObserver)
	{
		unimplemented("DescribeNexusOperationExecution", responseObserver);
	}

	@Override
	public void pollActivityExecution(PollActivityExecutionRequest request, StreamObserver<PollActivityExecutionResponse> responseObserver)
	{
		unimplemented("PollActivityExecution", responseObserver);
	}

	@Override
	public void pollNexusOperationExecution(PollNexusOperationExecutionRequest request, StreamObserver<PollNexusOperationExecutionResponse> responseObserver)
	{
		unimplemented("PollNexusOperationExecution", responseObserver);
	}

	@Override
	public void listActivityExecutions(ListActivityExecutionsRequest request, StreamObserver<ListActivityExecutionsResponse> responseObserver)
	{
		unimplemented("ListActivityExecutions", responseObserver);
	}

	@Override
	public void listNexusOperationExecutions(ListNexusOperationExecutionsRequest request, StreamObserver<ListNexusOperationExecutionsResponse> responseObserver)
	{
		unimplemented("ListNexusOperationExecutions", responseObserver);
	}

	@Override
	public void countActivityExecutions(CountActivityExecutionsRequest request, StreamObserver<CountActivityExecutionsResponse> responseObserver)
	{
		unimplemented("CountActivityExecutions", responseObserver);
	}

	@Override
	public void countNexusOperationExecutions(CountNexusOperationExecutionsRequest request, StreamObserver<CountNexusOperationExecutionsResponse> responseObserver)
	{
		unimplemented("CountNexusOperationExecutions", responseObserver);
	}

	@Override
	public void requestCancelActivityExecution(RequestCancelActivityExecutionRequest request, StreamObserver<RequestCancelActivityExecutionResponse> responseObserver)
	{
		unimplemented("RequestCancelActivityExecution", responseObserver);
	}

	@Override
	public void requestCancelNexusOperationExecution(RequestCancelNexusOperationExecutionRequest request, StreamObserver<RequestCancelNexusOperationExecutionResponse> responseObserver)
	{
		unimplemented("RequestCancelNexusOperationExecution", responseObserver);
	}

	@Override
	public void terminateActivityExecution(TerminateActivityExecutionRequest request, StreamObserver<TerminateActivityExecutionResponse> responseObserver)
	{
		unimplemented("TerminateActivityExecution", responseObserver);
	}

	@Override
	public void deleteActivityExecution(DeleteActivityExecutionRequest request, StreamObserver<DeleteActivityExecutionResponse> responseObserver)
	{
		unimplemented("DeleteActivityExecution", responseObserver);
	}

	@Override
	public void pauseActivityExecution(PauseActivityExecutionRequest request, StreamObserver<PauseActivityExecutionResponse> responseObserver)
	{
		unimplemented("PauseActivityExecution", responseObserver);
	}

	@Override
	public void resetActivityExecution(ResetActivityExecutionRequest request, StreamObserver<ResetActivityExecutionResponse> responseObserver)
	{
		unimplemented("ResetActivityExecution", responseObserver);
	}

	@Override
	public void unpauseActivityExecution(UnpauseActivityExecutionRequest request, StreamObserver<UnpauseActivityExecutionResponse> responseObserver)
	{
		unimplemented("UnpauseActivityExecution", responseObserver);
	}

	@Override
	public void updateActivityExecutionOptions(UpdateActivityExecutionOptionsRequest request, StreamObserver<UpdateActivityExecutionOptionsResponse> responseObserver)
	{
		unimplemented("UpdateActivityExecutionOptions", responseObserver);
	}

	@Override
	public void terminateNexusOperationExecution(TerminateNexusOperationExecutionRequest request, StreamObserver<TerminateNexusOperationExecutionResponse> responseObserver)
	{
		unimplemented("TerminateNexusOperationExecution", responseObserver);
	}

	@Override
	public void deleteNexusOperationExecution(DeleteNexusOperationExecutionRequest request, StreamObserver<DeleteNexusOperationExecutionResponse> responseObserver)
	{
		unimplemented("DeleteNexusOperationExecution", responseObserver);
	}

	@Override
	public void pollWorkflowExecutionTimeSkipping(PollWorkflowExecutionTimeSkippingRequest request, StreamObserver<PollWorkflowExecutionTimeSkippingResponse> responseObserver)
	{
		unimplemented("PollWorkflowExecutionTimeSkipping", responseObserver);
	}
}
