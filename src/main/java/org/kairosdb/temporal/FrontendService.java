package org.kairosdb.temporal;

import com.google.inject.Inject;
import com.google.protobuf.Duration;
import io.grpc.ServerServiceDefinition;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.temporal.api.enums.v1.NamespaceState;
import io.temporal.api.namespace.v1.NamespaceConfig;
import io.temporal.api.namespace.v1.NamespaceInfo;
import io.temporal.api.replication.v1.ClusterReplicationConfig;
import io.temporal.api.replication.v1.NamespaceReplicationConfig;
import io.temporal.api.workflowservice.v1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.temporal.api.workflowservice.v1.WorkflowServiceGrpc.getRegisterNamespaceMethod;
import static io.temporal.api.workflowservice.v1.WorkflowServiceGrpc.getServiceDescriptor;

/**
 Implementation of the Temporal frontend gRPC service
 (temporal.api.workflowservice.v1.WorkflowService).

 Every RPC is currently stubbed out and responds with UNIMPLEMENTED.
 */
public class FrontendService extends WorkflowServiceGrpc.WorkflowServiceImplBase
{
	private static final Logger logger = LoggerFactory.getLogger(FrontendService.class);

	private final DataStore m_dataStore;
	private final Orchestrator m_orchestrator;
	private final RoutingService m_routingService;


	@Inject
	public FrontendService(Orchestrator orchestrator, DataStore dataStore, RoutingService routingService)
	{
		m_orchestrator = orchestrator;
		m_dataStore = dataStore;
		m_routingService = routingService;
	}

	static final String DEFAULT_NAMESPACE      = "default";
	static final String DEFAULT_NAMESPACE_ID   = "32049b68-8872-4a4d-b0e2-9f3a5a4e7c11";
	static final String DEFAULT_CLUSTER_NAME   = "default";
	private static final long RETENTION_SECONDS = 72 * 3600L; // 3 days

	private static final DescribeNamespaceResponse DEFAULT_NAMESPACE_RESPONSE =
			DescribeNamespaceResponse.newBuilder()
					.setNamespaceInfo(NamespaceInfo.newBuilder()
							.setName(DEFAULT_NAMESPACE)
							.setId(DEFAULT_NAMESPACE_ID)
							.setState(NamespaceState.NAMESPACE_STATE_REGISTERED)
							.setDescription("Default namespace")
							.build())
					.setConfig(NamespaceConfig.newBuilder()
							.setWorkflowExecutionRetentionTtl(Duration.newBuilder()
									.setSeconds(RETENTION_SECONDS)
									.build())
							.build())
					.setReplicationConfig(NamespaceReplicationConfig.newBuilder()
							.setActiveClusterName(DEFAULT_CLUSTER_NAME)
							.addClusters(ClusterReplicationConfig.newBuilder()
									.setClusterName(DEFAULT_CLUSTER_NAME)
									.build())
							.build())
					.setIsGlobalNamespace(false)
					.build();

	private static void unimplemented(String method, StreamObserver<?> responseObserver)
	{
		logger.warn("Unimplemented method called: {}", method);
		responseObserver.onError(Status.UNIMPLEMENTED
				.withDescription("Method temporal.api.workflowservice.v1.WorkflowService/" + method + " is not implemented")
				.asRuntimeException());
	}

	@Override
	public void registerNamespace(RegisterNamespaceRequest request, StreamObserver<RegisterNamespaceResponse> responseObserver)
	{
		logger.info("Called: RegisterNamespace");
		unimplemented("RegisterNamespace", responseObserver);
	}

	@Override
	public void describeNamespace(DescribeNamespaceRequest request, StreamObserver<DescribeNamespaceResponse> responseObserver)
	{
		logger.info("Called: DescribeNamespace");
		String name = request.getNamespace();
		String id   = request.getId();

		boolean isDefault = (name.isEmpty() || DEFAULT_NAMESPACE.equals(name))
				&& (id.isEmpty() || DEFAULT_NAMESPACE_ID.equals(id));

		if (!isDefault)
		{
			responseObserver.onError(Status.NOT_FOUND
					.withDescription("Namespace not found: " + (name.isEmpty() ? id : name))
					.asRuntimeException());
			return;
		}

		responseObserver.onNext(DEFAULT_NAMESPACE_RESPONSE);
		responseObserver.onCompleted();
	}

	@Override
	public void listNamespaces(ListNamespacesRequest request, StreamObserver<ListNamespacesResponse> responseObserver)
	{
		logger.info("Called: ListNamespaces");
		responseObserver.onNext(ListNamespacesResponse.newBuilder()
				.addNamespaces(DEFAULT_NAMESPACE_RESPONSE)
				.build());
		responseObserver.onCompleted();
	}

	@Override
	public void updateNamespace(UpdateNamespaceRequest request, StreamObserver<UpdateNamespaceResponse> responseObserver)
	{
		logger.info("Called: UpdateNamespace");
		unimplemented("UpdateNamespace", responseObserver);
	}

	// Deprecated in Temporal API.
	@Override
	public void deprecateNamespace(DeprecateNamespaceRequest request, StreamObserver<DeprecateNamespaceResponse> responseObserver)
	{
		logger.info("Called: DeprecateNamespace");
		unimplemented("DeprecateNamespace", responseObserver);
	}

	@Override
	public void getClusterInfo(GetClusterInfoRequest request, StreamObserver<GetClusterInfoResponse> responseObserver)
	{
		logger.info("Called: GetClusterInfo");
		unimplemented("GetClusterInfo", responseObserver);
	}

	@Override
	public void getSystemInfo(GetSystemInfoRequest request, StreamObserver<GetSystemInfoResponse> responseObserver)
	{
		logger.info("Called: GetSystemInfo");
		GetSystemInfoResponse response = GetSystemInfoResponse.newBuilder()
				.setServerVersion("1.26.0")
				.setCapabilities(GetSystemInfoResponse.Capabilities.newBuilder()
						.setSignalAndQueryHeader(true)
						.setInternalErrorDifferentiation(true)
						.setActivityFailureIncludeHeartbeat(true)
						.setSupportsSchedules(true)
						.setEncodedFailureAttributes(true)
						.setUpsertMemo(true)
						.setSdkMetadata(true)
						.build())
				.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}



	//Calls that work off of the visibility data

	@Override
	public void getWorkflowExecutionHistory(GetWorkflowExecutionHistoryRequest request, StreamObserver<GetWorkflowExecutionHistoryResponse> responseObserver)
	{
		logger.info("Called: GetWorkflowExecutionHistory");
		unimplemented("GetWorkflowExecutionHistory", responseObserver);
	}

	@Override
	public void getWorkflowExecutionHistoryReverse(GetWorkflowExecutionHistoryReverseRequest request, StreamObserver<GetWorkflowExecutionHistoryReverseResponse> responseObserver)
	{
		logger.info("Called: GetWorkflowExecutionHistoryReverse");
		unimplemented("GetWorkflowExecutionHistoryReverse", responseObserver);
	}

	@Override
	public void listOpenWorkflowExecutions(ListOpenWorkflowExecutionsRequest request, StreamObserver<ListOpenWorkflowExecutionsResponse> responseObserver)
	{
		logger.info("Called: ListOpenWorkflowExecutions");
		unimplemented("ListOpenWorkflowExecutions", responseObserver);
	}

	@Override
	public void listClosedWorkflowExecutions(ListClosedWorkflowExecutionsRequest request, StreamObserver<ListClosedWorkflowExecutionsResponse> responseObserver)
	{
		logger.info("Called: ListClosedWorkflowExecutions");
		unimplemented("ListClosedWorkflowExecutions", responseObserver);
	}

	@Override
	public void listWorkflowExecutions(ListWorkflowExecutionsRequest request, StreamObserver<ListWorkflowExecutionsResponse> responseObserver)
	{
		logger.info("Called: ListWorkflowExecutions");
		unimplemented("ListWorkflowExecutions", responseObserver);
	}

	@Override
	public void listArchivedWorkflowExecutions(ListArchivedWorkflowExecutionsRequest request, StreamObserver<ListArchivedWorkflowExecutionsResponse> responseObserver)
	{
		logger.info("Called: ListArchivedWorkflowExecutions");
		unimplemented("ListArchivedWorkflowExecutions", responseObserver);
	}

	// Deprecated: Replaced with listWorkflowExecutions.
	@Override
	public void scanWorkflowExecutions(ScanWorkflowExecutionsRequest request, StreamObserver<ScanWorkflowExecutionsResponse> responseObserver)
	{
		logger.info("Called: ScanWorkflowExecutions");
		unimplemented("ScanWorkflowExecutions", responseObserver);
	}

	@Override
	public void countWorkflowExecutions(CountWorkflowExecutionsRequest request, StreamObserver<CountWorkflowExecutionsResponse> responseObserver)
	{
		logger.info("Called: CountWorkflowExecutions");
		unimplemented("CountWorkflowExecutions", responseObserver);
	}

	@Override
	public void getSearchAttributes(GetSearchAttributesRequest request, StreamObserver<GetSearchAttributesResponse> responseObserver)
	{
		logger.info("Called: GetSearchAttributes");
		unimplemented("GetSearchAttributes", responseObserver);
	}





	//Calls below here may need to be routed to the correct service
	@Override
	public void startWorkflowExecution(StartWorkflowExecutionRequest request, StreamObserver<StartWorkflowExecutionResponse> responseObserver)
	{
		if (m_routingService.routeStartWorkflowExecution(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}

		logger.info("Called: StartWorkflowExecution");
		try
		{
			DataStore.WorkflowStartResult result = m_dataStore.startWorkflowExecution(request);
			responseObserver.onNext(StartWorkflowExecutionResponse.newBuilder()
					.setRunId(result.runId())
					.setStarted(result.started())
					.build());
			responseObserver.onCompleted();
		}
		catch (DataStore.WorkflowAlreadyExistsException e)
		{
			responseObserver.onError(Status.ALREADY_EXISTS
					.withDescription(e.getMessage())
					.asRuntimeException());
		}
		catch (Exception e)
		{
			logger.error("Error starting workflow execution", e);
			responseObserver.onError(Status.INTERNAL
					.withDescription(e.getMessage())
					.asRuntimeException());
		}
	}

	@Override
	public void executeMultiOperation(ExecuteMultiOperationRequest request, StreamObserver<ExecuteMultiOperationResponse> responseObserver)
	{
		logger.info("Called: ExecuteMultiOperation");
		unimplemented("ExecuteMultiOperation", responseObserver);
	}


	@Override
	public void pollWorkflowTaskQueue(PollWorkflowTaskQueueRequest request, StreamObserver<PollWorkflowTaskQueueResponse> responseObserver)
	{
		if (m_routingService.routePollWorkflowTaskQueue(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}

		logger.info("Called: PollWorkflowTaskQueue");
		unimplemented("PollWorkflowTaskQueue", responseObserver);
	}

	@Override
	public void respondWorkflowTaskCompleted(RespondWorkflowTaskCompletedRequest request, StreamObserver<RespondWorkflowTaskCompletedResponse> responseObserver)
	{
		if (m_routingService.routeRespondWorkflowTaskCompleted(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}

		logger.info("Called: RespondWorkflowTaskCompleted");
		unimplemented("RespondWorkflowTaskCompleted", responseObserver);
	}

	@Override
	public void respondWorkflowTaskFailed(RespondWorkflowTaskFailedRequest request, StreamObserver<RespondWorkflowTaskFailedResponse> responseObserver)
	{
		if (m_routingService.routeRespondWorkflowTaskFailed(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}

		logger.info("Called: RespondWorkflowTaskFailed");
		unimplemented("RespondWorkflowTaskFailed", responseObserver);
	}

	@Override
	public void pollActivityTaskQueue(PollActivityTaskQueueRequest request, StreamObserver<PollActivityTaskQueueResponse> responseObserver)
	{
		if (m_routingService.routePollActivityTaskQueue(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: PollActivityTaskQueue");
		unimplemented("PollActivityTaskQueue", responseObserver);
	}

	@Override
	public void recordActivityTaskHeartbeat(RecordActivityTaskHeartbeatRequest request, StreamObserver<RecordActivityTaskHeartbeatResponse> responseObserver)
	{
		if (m_routingService.routeRecordActivityTaskHeartbeat(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}

		logger.info("Called: RecordActivityTaskHeartbeat");
		unimplemented("RecordActivityTaskHeartbeat", responseObserver);
	}

	@Override
	public void recordActivityTaskHeartbeatById(RecordActivityTaskHeartbeatByIdRequest request, StreamObserver<RecordActivityTaskHeartbeatByIdResponse> responseObserver)
	{
		if (m_routingService.routeRecordActivityTaskHeartbeatById(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: RecordActivityTaskHeartbeatById");
		unimplemented("RecordActivityTaskHeartbeatById", responseObserver);
	}

	@Override
	public void respondActivityTaskCompleted(RespondActivityTaskCompletedRequest request, StreamObserver<RespondActivityTaskCompletedResponse> responseObserver)
	{
		if (m_routingService.routeRespondActivityTaskCompleted(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: RespondActivityTaskCompleted");
		unimplemented("RespondActivityTaskCompleted", responseObserver);
	}

	@Override
	public void respondActivityTaskCompletedById(RespondActivityTaskCompletedByIdRequest request, StreamObserver<RespondActivityTaskCompletedByIdResponse> responseObserver)
	{
		if (m_routingService.routeRespondActivityTaskCompletedById(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: RespondActivityTaskCompletedById");
		unimplemented("RespondActivityTaskCompletedById", responseObserver);
	}

	@Override
	public void respondActivityTaskFailed(RespondActivityTaskFailedRequest request, StreamObserver<RespondActivityTaskFailedResponse> responseObserver)
	{
		if (m_routingService.routeRespondActivityTaskFailed(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: RespondActivityTaskFailed");
		unimplemented("RespondActivityTaskFailed", responseObserver);
	}

	@Override
	public void respondActivityTaskFailedById(RespondActivityTaskFailedByIdRequest request, StreamObserver<RespondActivityTaskFailedByIdResponse> responseObserver)
	{
		if (m_routingService.routeRespondActivityTaskFailedById(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: RespondActivityTaskFailedById");
		unimplemented("RespondActivityTaskFailedById", responseObserver);
	}

	@Override
	public void respondActivityTaskCanceled(RespondActivityTaskCanceledRequest request, StreamObserver<RespondActivityTaskCanceledResponse> responseObserver)
	{
		if (m_routingService.routeRespondActivityTaskCanceled(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: RespondActivityTaskCanceled");
		unimplemented("RespondActivityTaskCanceled", responseObserver);
	}

	@Override
	public void respondActivityTaskCanceledById(RespondActivityTaskCanceledByIdRequest request, StreamObserver<RespondActivityTaskCanceledByIdResponse> responseObserver)
	{
		if (m_routingService.routeRespondActivityTaskCanceledById(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: RespondActivityTaskCanceledById");
		unimplemented("RespondActivityTaskCanceledById", responseObserver);
	}

	@Override
	public void requestCancelWorkflowExecution(RequestCancelWorkflowExecutionRequest request, StreamObserver<RequestCancelWorkflowExecutionResponse> responseObserver)
	{
		if (m_routingService.routeRequestCancelWorkflowExecution(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: RequestCancelWorkflowExecution");
		unimplemented("RequestCancelWorkflowExecution", responseObserver);
	}

	@Override
	public void signalWorkflowExecution(SignalWorkflowExecutionRequest request, StreamObserver<SignalWorkflowExecutionResponse> responseObserver)
	{
		if (m_routingService.routeSignalWorkflowExecution(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: SignalWorkflowExecution");
		unimplemented("SignalWorkflowExecution", responseObserver);
	}

	@Override
	public void signalWithStartWorkflowExecution(SignalWithStartWorkflowExecutionRequest request, StreamObserver<SignalWithStartWorkflowExecutionResponse> responseObserver)
	{
		if (m_routingService.routeSignalWithStartWorkflowExecution(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: SignalWithStartWorkflowExecution");
		unimplemented("SignalWithStartWorkflowExecution", responseObserver);
	}

	@Override
	public void resetWorkflowExecution(ResetWorkflowExecutionRequest request, StreamObserver<ResetWorkflowExecutionResponse> responseObserver)
	{
		if (m_routingService.routeResetWorkflowExecution(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: ResetWorkflowExecution");
		unimplemented("ResetWorkflowExecution", responseObserver);
	}

	@Override
	public void terminateWorkflowExecution(TerminateWorkflowExecutionRequest request, StreamObserver<TerminateWorkflowExecutionResponse> responseObserver)
	{
		if (m_routingService.routeTerminateWorkflowExecution(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: TerminateWorkflowExecution");
		unimplemented("TerminateWorkflowExecution", responseObserver);
	}

	@Override
	public void deleteWorkflowExecution(DeleteWorkflowExecutionRequest request, StreamObserver<DeleteWorkflowExecutionResponse> responseObserver)
	{
		if (m_routingService.routeDeleteWorkflowExecution(request, responseObserver))
		{
			//Request was routed to another frontend node
			return;
		}
		logger.info("Called: DeleteWorkflowExecution");
		unimplemented("DeleteWorkflowExecution", responseObserver);
	}

	@Override
	public void respondQueryTaskCompleted(RespondQueryTaskCompletedRequest request, StreamObserver<RespondQueryTaskCompletedResponse> responseObserver)
	{
		logger.info("Called: RespondQueryTaskCompleted");
		unimplemented("RespondQueryTaskCompleted", responseObserver);
	}

	@Override
	public void resetStickyTaskQueue(ResetStickyTaskQueueRequest request, StreamObserver<ResetStickyTaskQueueResponse> responseObserver)
	{
		logger.info("Called: ResetStickyTaskQueue");
		unimplemented("ResetStickyTaskQueue", responseObserver);
	}

	@Override
	public void shutdownWorker(ShutdownWorkerRequest request, StreamObserver<ShutdownWorkerResponse> responseObserver)
	{
		logger.info("Called: ShutdownWorker");
		unimplemented("ShutdownWorker", responseObserver);
	}

	@Override
	public void queryWorkflow(QueryWorkflowRequest request, StreamObserver<QueryWorkflowResponse> responseObserver)
	{
		logger.info("Called: QueryWorkflow");
		unimplemented("QueryWorkflow", responseObserver);
	}

	@Override
	public void describeWorkflowExecution(DescribeWorkflowExecutionRequest request, StreamObserver<DescribeWorkflowExecutionResponse> responseObserver)
	{
		logger.info("Called: DescribeWorkflowExecution");
		unimplemented("DescribeWorkflowExecution", responseObserver);
	}

	@Override
	public void describeTaskQueue(DescribeTaskQueueRequest request, StreamObserver<DescribeTaskQueueResponse> responseObserver)
	{
		logger.info("Called: DescribeTaskQueue");
		unimplemented("DescribeTaskQueue", responseObserver);
	}

	@Override
	public void listTaskQueuePartitions(ListTaskQueuePartitionsRequest request, StreamObserver<ListTaskQueuePartitionsResponse> responseObserver)
	{
		logger.info("Called: ListTaskQueuePartitions");
		unimplemented("ListTaskQueuePartitions", responseObserver);
	}

	@Override
	public void createSchedule(CreateScheduleRequest request, StreamObserver<CreateScheduleResponse> responseObserver)
	{
		logger.info("Called: CreateSchedule");
		unimplemented("CreateSchedule", responseObserver);
	}

	@Override
	public void describeSchedule(DescribeScheduleRequest request, StreamObserver<DescribeScheduleResponse> responseObserver)
	{
		logger.info("Called: DescribeSchedule");
		unimplemented("DescribeSchedule", responseObserver);
	}

	@Override
	public void updateSchedule(UpdateScheduleRequest request, StreamObserver<UpdateScheduleResponse> responseObserver)
	{
		logger.info("Called: UpdateSchedule");
		unimplemented("UpdateSchedule", responseObserver);
	}

	@Override
	public void patchSchedule(PatchScheduleRequest request, StreamObserver<PatchScheduleResponse> responseObserver)
	{
		logger.info("Called: PatchSchedule");
		unimplemented("PatchSchedule", responseObserver);
	}

	@Override
	public void listScheduleMatchingTimes(ListScheduleMatchingTimesRequest request, StreamObserver<ListScheduleMatchingTimesResponse> responseObserver)
	{
		logger.info("Called: ListScheduleMatchingTimes");
		unimplemented("ListScheduleMatchingTimes", responseObserver);
	}

	@Override
	public void deleteSchedule(DeleteScheduleRequest request, StreamObserver<DeleteScheduleResponse> responseObserver)
	{
		logger.info("Called: DeleteSchedule");
		unimplemented("DeleteSchedule", responseObserver);
	}

	@Override
	public void listSchedules(ListSchedulesRequest request, StreamObserver<ListSchedulesResponse> responseObserver)
	{
		logger.info("Called: ListSchedules");
		unimplemented("ListSchedules", responseObserver);
	}

	@Override
	public void countSchedules(CountSchedulesRequest request, StreamObserver<CountSchedulesResponse> responseObserver)
	{
		logger.info("Called: CountSchedules");
		unimplemented("CountSchedules", responseObserver);
	}

	// Deprecated: Use updateWorkerVersioningRules.
	@Override
	public void updateWorkerBuildIdCompatibility(UpdateWorkerBuildIdCompatibilityRequest request, StreamObserver<UpdateWorkerBuildIdCompatibilityResponse> responseObserver)
	{
		logger.info("Called: UpdateWorkerBuildIdCompatibility");
		unimplemented("UpdateWorkerBuildIdCompatibility", responseObserver);
	}

	// Deprecated: Use getWorkerVersioningRules.
	@Override
	public void getWorkerBuildIdCompatibility(GetWorkerBuildIdCompatibilityRequest request, StreamObserver<GetWorkerBuildIdCompatibilityResponse> responseObserver)
	{
		logger.info("Called: GetWorkerBuildIdCompatibility");
		unimplemented("GetWorkerBuildIdCompatibility", responseObserver);
	}

	@Override
	public void updateWorkerVersioningRules(UpdateWorkerVersioningRulesRequest request, StreamObserver<UpdateWorkerVersioningRulesResponse> responseObserver)
	{
		logger.info("Called: UpdateWorkerVersioningRules");
		unimplemented("UpdateWorkerVersioningRules", responseObserver);
	}

	@Override
	public void getWorkerVersioningRules(GetWorkerVersioningRulesRequest request, StreamObserver<GetWorkerVersioningRulesResponse> responseObserver)
	{
		logger.info("Called: GetWorkerVersioningRules");
		unimplemented("GetWorkerVersioningRules", responseObserver);
	}

	// Deprecated: Use describeTaskQueue.
	@Override
	public void getWorkerTaskReachability(GetWorkerTaskReachabilityRequest request, StreamObserver<GetWorkerTaskReachabilityResponse> responseObserver)
	{
		logger.info("Called: GetWorkerTaskReachability");
		unimplemented("GetWorkerTaskReachability", responseObserver);
	}

	// Deprecated: Replaced with describeWorkerDeploymentVersion.
	@Override
	public void describeDeployment(DescribeDeploymentRequest request, StreamObserver<DescribeDeploymentResponse> responseObserver)
	{
		logger.info("Called: DescribeDeployment");
		unimplemented("DescribeDeployment", responseObserver);
	}

	@Override
	public void describeWorkerDeploymentVersion(DescribeWorkerDeploymentVersionRequest request, StreamObserver<DescribeWorkerDeploymentVersionResponse> responseObserver)
	{
		logger.info("Called: DescribeWorkerDeploymentVersion");
		unimplemented("DescribeWorkerDeploymentVersion", responseObserver);
	}

	// Deprecated: Replaced with listWorkerDeployments.
	@Override
	public void listDeployments(ListDeploymentsRequest request, StreamObserver<ListDeploymentsResponse> responseObserver)
	{
		logger.info("Called: ListDeployments");
		unimplemented("ListDeployments", responseObserver);
	}

	// Deprecated: Replaced with DrainageInfo returned by describeWorkerDeploymentVersion.
	@Override
	public void getDeploymentReachability(GetDeploymentReachabilityRequest request, StreamObserver<GetDeploymentReachabilityResponse> responseObserver)
	{
		logger.info("Called: GetDeploymentReachability");
		unimplemented("GetDeploymentReachability", responseObserver);
	}

	// Deprecated: Replaced by current_version returned by describeWorkerDeployment.
	@Override
	public void getCurrentDeployment(GetCurrentDeploymentRequest request, StreamObserver<GetCurrentDeploymentResponse> responseObserver)
	{
		logger.info("Called: GetCurrentDeployment");
		unimplemented("GetCurrentDeployment", responseObserver);
	}

	// Deprecated: Replaced by setWorkerDeploymentCurrentVersion.
	@Override
	public void setCurrentDeployment(SetCurrentDeploymentRequest request, StreamObserver<SetCurrentDeploymentResponse> responseObserver)
	{
		logger.info("Called: SetCurrentDeployment");
		unimplemented("SetCurrentDeployment", responseObserver);
	}

	@Override
	public void setWorkerDeploymentCurrentVersion(SetWorkerDeploymentCurrentVersionRequest request, StreamObserver<SetWorkerDeploymentCurrentVersionResponse> responseObserver)
	{
		logger.info("Called: SetWorkerDeploymentCurrentVersion");
		unimplemented("SetWorkerDeploymentCurrentVersion", responseObserver);
	}

	@Override
	public void describeWorkerDeployment(DescribeWorkerDeploymentRequest request, StreamObserver<DescribeWorkerDeploymentResponse> responseObserver)
	{
		logger.info("Called: DescribeWorkerDeployment");
		unimplemented("DescribeWorkerDeployment", responseObserver);
	}

	@Override
	public void deleteWorkerDeployment(DeleteWorkerDeploymentRequest request, StreamObserver<DeleteWorkerDeploymentResponse> responseObserver)
	{
		logger.info("Called: DeleteWorkerDeployment");
		unimplemented("DeleteWorkerDeployment", responseObserver);
	}

	@Override
	public void deleteWorkerDeploymentVersion(DeleteWorkerDeploymentVersionRequest request, StreamObserver<DeleteWorkerDeploymentVersionResponse> responseObserver)
	{
		logger.info("Called: DeleteWorkerDeploymentVersion");
		unimplemented("DeleteWorkerDeploymentVersion", responseObserver);
	}

	@Override
	public void setWorkerDeploymentRampingVersion(SetWorkerDeploymentRampingVersionRequest request, StreamObserver<SetWorkerDeploymentRampingVersionResponse> responseObserver)
	{
		logger.info("Called: SetWorkerDeploymentRampingVersion");
		unimplemented("SetWorkerDeploymentRampingVersion", responseObserver);
	}

	@Override
	public void listWorkerDeployments(ListWorkerDeploymentsRequest request, StreamObserver<ListWorkerDeploymentsResponse> responseObserver)
	{
		logger.info("Called: ListWorkerDeployments");
		unimplemented("ListWorkerDeployments", responseObserver);
	}

	@Override
	public void createWorkerDeployment(CreateWorkerDeploymentRequest request, StreamObserver<CreateWorkerDeploymentResponse> responseObserver)
	{
		logger.info("Called: CreateWorkerDeployment");
		unimplemented("CreateWorkerDeployment", responseObserver);
	}

	@Override
	public void createWorkerDeploymentVersion(CreateWorkerDeploymentVersionRequest request, StreamObserver<CreateWorkerDeploymentVersionResponse> responseObserver)
	{
		logger.info("Called: CreateWorkerDeploymentVersion");
		unimplemented("CreateWorkerDeploymentVersion", responseObserver);
	}

	@Override
	public void updateWorkerDeploymentVersionComputeConfig(UpdateWorkerDeploymentVersionComputeConfigRequest request, StreamObserver<UpdateWorkerDeploymentVersionComputeConfigResponse> responseObserver)
	{
		logger.info("Called: UpdateWorkerDeploymentVersionComputeConfig");
		unimplemented("UpdateWorkerDeploymentVersionComputeConfig", responseObserver);
	}

	@Override
	public void validateWorkerDeploymentVersionComputeConfig(ValidateWorkerDeploymentVersionComputeConfigRequest request, StreamObserver<ValidateWorkerDeploymentVersionComputeConfigResponse> responseObserver)
	{
		logger.info("Called: ValidateWorkerDeploymentVersionComputeConfig");
		unimplemented("ValidateWorkerDeploymentVersionComputeConfig", responseObserver);
	}

	@Override
	public void updateWorkerDeploymentVersionMetadata(UpdateWorkerDeploymentVersionMetadataRequest request, StreamObserver<UpdateWorkerDeploymentVersionMetadataResponse> responseObserver)
	{
		logger.info("Called: UpdateWorkerDeploymentVersionMetadata");
		unimplemented("UpdateWorkerDeploymentVersionMetadata", responseObserver);
	}

	@Override
	public void setWorkerDeploymentManager(SetWorkerDeploymentManagerRequest request, StreamObserver<SetWorkerDeploymentManagerResponse> responseObserver)
	{
		logger.info("Called: SetWorkerDeploymentManager");
		unimplemented("SetWorkerDeploymentManager", responseObserver);
	}

	@Override
	public void updateWorkflowExecution(UpdateWorkflowExecutionRequest request, StreamObserver<UpdateWorkflowExecutionResponse> responseObserver)
	{
		logger.info("Called: UpdateWorkflowExecution");
		unimplemented("UpdateWorkflowExecution", responseObserver);
	}

	@Override
	public void pollWorkflowExecutionUpdate(PollWorkflowExecutionUpdateRequest request, StreamObserver<PollWorkflowExecutionUpdateResponse> responseObserver)
	{
		logger.info("Called: PollWorkflowExecutionUpdate");
		unimplemented("PollWorkflowExecutionUpdate", responseObserver);
	}

	@Override
	public void startBatchOperation(StartBatchOperationRequest request, StreamObserver<StartBatchOperationResponse> responseObserver)
	{
		logger.info("Called: StartBatchOperation");
		unimplemented("StartBatchOperation", responseObserver);
	}

	@Override
	public void stopBatchOperation(StopBatchOperationRequest request, StreamObserver<StopBatchOperationResponse> responseObserver)
	{
		logger.info("Called: StopBatchOperation");
		unimplemented("StopBatchOperation", responseObserver);
	}

	@Override
	public void describeBatchOperation(DescribeBatchOperationRequest request, StreamObserver<DescribeBatchOperationResponse> responseObserver)
	{
		logger.info("Called: DescribeBatchOperation");
		unimplemented("DescribeBatchOperation", responseObserver);
	}

	@Override
	public void listBatchOperations(ListBatchOperationsRequest request, StreamObserver<ListBatchOperationsResponse> responseObserver)
	{
		logger.info("Called: ListBatchOperations");
		unimplemented("ListBatchOperations", responseObserver);
	}

	@Override
	public void pollNexusTaskQueue(PollNexusTaskQueueRequest request, StreamObserver<PollNexusTaskQueueResponse> responseObserver)
	{
		logger.info("Called: PollNexusTaskQueue");
		unimplemented("PollNexusTaskQueue", responseObserver);
	}

	@Override
	public void respondNexusTaskCompleted(RespondNexusTaskCompletedRequest request, StreamObserver<RespondNexusTaskCompletedResponse> responseObserver)
	{
		logger.info("Called: RespondNexusTaskCompleted");
		unimplemented("RespondNexusTaskCompleted", responseObserver);
	}

	@Override
	public void respondNexusTaskFailed(RespondNexusTaskFailedRequest request, StreamObserver<RespondNexusTaskFailedResponse> responseObserver)
	{
		logger.info("Called: RespondNexusTaskFailed");
		unimplemented("RespondNexusTaskFailed", responseObserver);
	}

	@Override
	public void updateActivityOptions(UpdateActivityOptionsRequest request, StreamObserver<UpdateActivityOptionsResponse> responseObserver)
	{
		logger.info("Called: UpdateActivityOptions");
		unimplemented("UpdateActivityOptions", responseObserver);
	}

	@Override
	public void updateWorkflowExecutionOptions(UpdateWorkflowExecutionOptionsRequest request, StreamObserver<UpdateWorkflowExecutionOptionsResponse> responseObserver)
	{
		logger.info("Called: UpdateWorkflowExecutionOptions");
		unimplemented("UpdateWorkflowExecutionOptions", responseObserver);
	}

	@Override
	public void pauseActivity(PauseActivityRequest request, StreamObserver<PauseActivityResponse> responseObserver)
	{
		logger.info("Called: PauseActivity");
		unimplemented("PauseActivity", responseObserver);
	}

	@Override
	public void unpauseActivity(UnpauseActivityRequest request, StreamObserver<UnpauseActivityResponse> responseObserver)
	{
		logger.info("Called: UnpauseActivity");
		unimplemented("UnpauseActivity", responseObserver);
	}

	@Override
	public void resetActivity(ResetActivityRequest request, StreamObserver<ResetActivityResponse> responseObserver)
	{
		logger.info("Called: ResetActivity");
		unimplemented("ResetActivity", responseObserver);
	}

	@Override
	public void createWorkflowRule(CreateWorkflowRuleRequest request, StreamObserver<CreateWorkflowRuleResponse> responseObserver)
	{
		logger.info("Called: CreateWorkflowRule");
		unimplemented("CreateWorkflowRule", responseObserver);
	}

	@Override
	public void describeWorkflowRule(DescribeWorkflowRuleRequest request, StreamObserver<DescribeWorkflowRuleResponse> responseObserver)
	{
		logger.info("Called: DescribeWorkflowRule");
		unimplemented("DescribeWorkflowRule", responseObserver);
	}

	@Override
	public void deleteWorkflowRule(DeleteWorkflowRuleRequest request, StreamObserver<DeleteWorkflowRuleResponse> responseObserver)
	{
		logger.info("Called: DeleteWorkflowRule");
		unimplemented("DeleteWorkflowRule", responseObserver);
	}

	@Override
	public void listWorkflowRules(ListWorkflowRulesRequest request, StreamObserver<ListWorkflowRulesResponse> responseObserver)
	{
		logger.info("Called: ListWorkflowRules");
		unimplemented("ListWorkflowRules", responseObserver);
	}

	@Override
	public void triggerWorkflowRule(TriggerWorkflowRuleRequest request, StreamObserver<TriggerWorkflowRuleResponse> responseObserver)
	{
		logger.info("Called: TriggerWorkflowRule");
		unimplemented("TriggerWorkflowRule", responseObserver);
	}

	@Override
	public void recordWorkerHeartbeat(RecordWorkerHeartbeatRequest request, StreamObserver<RecordWorkerHeartbeatResponse> responseObserver)
	{
		logger.info("Called: RecordWorkerHeartbeat");
		unimplemented("RecordWorkerHeartbeat", responseObserver);
	}

	@Override
	public void listWorkers(ListWorkersRequest request, StreamObserver<ListWorkersResponse> responseObserver)
	{
		logger.info("Called: ListWorkers");
		unimplemented("ListWorkers", responseObserver);
	}

	@Override
	public void countWorkers(CountWorkersRequest request, StreamObserver<CountWorkersResponse> responseObserver)
	{
		logger.info("Called: CountWorkers");
		unimplemented("CountWorkers", responseObserver);
	}

	@Override
	public void updateTaskQueueConfig(UpdateTaskQueueConfigRequest request, StreamObserver<UpdateTaskQueueConfigResponse> responseObserver)
	{
		logger.info("Called: UpdateTaskQueueConfig");
		unimplemented("UpdateTaskQueueConfig", responseObserver);
	}

	@Override
	public void fetchWorkerConfig(FetchWorkerConfigRequest request, StreamObserver<FetchWorkerConfigResponse> responseObserver)
	{
		logger.info("Called: FetchWorkerConfig");
		unimplemented("FetchWorkerConfig", responseObserver);
	}

	@Override
	public void updateWorkerConfig(UpdateWorkerConfigRequest request, StreamObserver<UpdateWorkerConfigResponse> responseObserver)
	{
		logger.info("Called: UpdateWorkerConfig");
		unimplemented("UpdateWorkerConfig", responseObserver);
	}

	@Override
	public void describeWorker(DescribeWorkerRequest request, StreamObserver<DescribeWorkerResponse> responseObserver)
	{
		logger.info("Called: DescribeWorker");
		unimplemented("DescribeWorker", responseObserver);
	}

	@Override
	public void pauseWorkflowExecution(PauseWorkflowExecutionRequest request, StreamObserver<PauseWorkflowExecutionResponse> responseObserver)
	{
		logger.info("Called: PauseWorkflowExecution");
		unimplemented("PauseWorkflowExecution", responseObserver);
	}

	@Override
	public void unpauseWorkflowExecution(UnpauseWorkflowExecutionRequest request, StreamObserver<UnpauseWorkflowExecutionResponse> responseObserver)
	{
		logger.info("Called: UnpauseWorkflowExecution");
		unimplemented("UnpauseWorkflowExecution", responseObserver);
	}

	@Override
	public void startActivityExecution(StartActivityExecutionRequest request, StreamObserver<StartActivityExecutionResponse> responseObserver)
	{
		logger.info("Called: StartActivityExecution");
		unimplemented("StartActivityExecution", responseObserver);
	}

	@Override
	public void startNexusOperationExecution(StartNexusOperationExecutionRequest request, StreamObserver<StartNexusOperationExecutionResponse> responseObserver)
	{
		logger.info("Called: StartNexusOperationExecution");
		unimplemented("StartNexusOperationExecution", responseObserver);
	}

	@Override
	public void describeActivityExecution(DescribeActivityExecutionRequest request, StreamObserver<DescribeActivityExecutionResponse> responseObserver)
	{
		logger.info("Called: DescribeActivityExecution");
		unimplemented("DescribeActivityExecution", responseObserver);
	}

	@Override
	public void describeNexusOperationExecution(DescribeNexusOperationExecutionRequest request, StreamObserver<DescribeNexusOperationExecutionResponse> responseObserver)
	{
		logger.info("Called: DescribeNexusOperationExecution");
		unimplemented("DescribeNexusOperationExecution", responseObserver);
	}

	@Override
	public void pollActivityExecution(PollActivityExecutionRequest request, StreamObserver<PollActivityExecutionResponse> responseObserver)
	{
		logger.info("Called: PollActivityExecution");
		unimplemented("PollActivityExecution", responseObserver);
	}

	@Override
	public void pollNexusOperationExecution(PollNexusOperationExecutionRequest request, StreamObserver<PollNexusOperationExecutionResponse> responseObserver)
	{
		logger.info("Called: PollNexusOperationExecution");
		unimplemented("PollNexusOperationExecution", responseObserver);
	}

	@Override
	public void listActivityExecutions(ListActivityExecutionsRequest request, StreamObserver<ListActivityExecutionsResponse> responseObserver)
	{
		logger.info("Called: ListActivityExecutions");
		unimplemented("ListActivityExecutions", responseObserver);
	}

	@Override
	public void listNexusOperationExecutions(ListNexusOperationExecutionsRequest request, StreamObserver<ListNexusOperationExecutionsResponse> responseObserver)
	{
		logger.info("Called: ListNexusOperationExecutions");
		unimplemented("ListNexusOperationExecutions", responseObserver);
	}

	@Override
	public void countActivityExecutions(CountActivityExecutionsRequest request, StreamObserver<CountActivityExecutionsResponse> responseObserver)
	{
		logger.info("Called: CountActivityExecutions");
		unimplemented("CountActivityExecutions", responseObserver);
	}

	@Override
	public void countNexusOperationExecutions(CountNexusOperationExecutionsRequest request, StreamObserver<CountNexusOperationExecutionsResponse> responseObserver)
	{
		logger.info("Called: CountNexusOperationExecutions");
		unimplemented("CountNexusOperationExecutions", responseObserver);
	}

	@Override
	public void requestCancelActivityExecution(RequestCancelActivityExecutionRequest request, StreamObserver<RequestCancelActivityExecutionResponse> responseObserver)
	{
		logger.info("Called: RequestCancelActivityExecution");
		unimplemented("RequestCancelActivityExecution", responseObserver);
	}

	@Override
	public void requestCancelNexusOperationExecution(RequestCancelNexusOperationExecutionRequest request, StreamObserver<RequestCancelNexusOperationExecutionResponse> responseObserver)
	{
		logger.info("Called: RequestCancelNexusOperationExecution");
		unimplemented("RequestCancelNexusOperationExecution", responseObserver);
	}

	@Override
	public void terminateActivityExecution(TerminateActivityExecutionRequest request, StreamObserver<TerminateActivityExecutionResponse> responseObserver)
	{
		logger.info("Called: TerminateActivityExecution");
		unimplemented("TerminateActivityExecution", responseObserver);
	}

	@Override
	public void deleteActivityExecution(DeleteActivityExecutionRequest request, StreamObserver<DeleteActivityExecutionResponse> responseObserver)
	{
		logger.info("Called: DeleteActivityExecution");
		unimplemented("DeleteActivityExecution", responseObserver);
	}

	@Override
	public void pauseActivityExecution(PauseActivityExecutionRequest request, StreamObserver<PauseActivityExecutionResponse> responseObserver)
	{
		logger.info("Called: PauseActivityExecution");
		unimplemented("PauseActivityExecution", responseObserver);
	}

	@Override
	public void resetActivityExecution(ResetActivityExecutionRequest request, StreamObserver<ResetActivityExecutionResponse> responseObserver)
	{
		logger.info("Called: ResetActivityExecution");
		unimplemented("ResetActivityExecution", responseObserver);
	}

	@Override
	public void unpauseActivityExecution(UnpauseActivityExecutionRequest request, StreamObserver<UnpauseActivityExecutionResponse> responseObserver)
	{
		logger.info("Called: UnpauseActivityExecution");
		unimplemented("UnpauseActivityExecution", responseObserver);
	}

	@Override
	public void updateActivityExecutionOptions(UpdateActivityExecutionOptionsRequest request, StreamObserver<UpdateActivityExecutionOptionsResponse> responseObserver)
	{
		logger.info("Called: UpdateActivityExecutionOptions");
		unimplemented("UpdateActivityExecutionOptions", responseObserver);
	}

	@Override
	public void terminateNexusOperationExecution(TerminateNexusOperationExecutionRequest request, StreamObserver<TerminateNexusOperationExecutionResponse> responseObserver)
	{
		logger.info("Called: TerminateNexusOperationExecution");
		unimplemented("TerminateNexusOperationExecution", responseObserver);
	}

	@Override
	public void deleteNexusOperationExecution(DeleteNexusOperationExecutionRequest request, StreamObserver<DeleteNexusOperationExecutionResponse> responseObserver)
	{
		logger.info("Called: DeleteNexusOperationExecution");
		unimplemented("DeleteNexusOperationExecution", responseObserver);
	}

	@Override
	public void pollWorkflowExecutionTimeSkipping(PollWorkflowExecutionTimeSkippingRequest request, StreamObserver<PollWorkflowExecutionTimeSkippingResponse> responseObserver)
	{
		logger.info("Called: PollWorkflowExecutionTimeSkipping");
		unimplemented("PollWorkflowExecutionTimeSkipping", responseObserver);
	}
}
