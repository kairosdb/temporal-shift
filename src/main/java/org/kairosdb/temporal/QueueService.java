package org.kairosdb.temporal;

import io.grpc.stub.StreamObserver;
import io.temporal.api.workflowservice.v1.*;

public class QueueService implements RoutingService
{
	//Provides queue service for submitted tasks.

	@Override
	public boolean routeStartWorkflowExecution(StartWorkflowExecutionRequest request, StreamObserver<StartWorkflowExecutionResponse> responseObserver) { return false; }

	@Override
	public boolean routePollWorkflowTaskQueue(PollWorkflowTaskQueueRequest request, StreamObserver<PollWorkflowTaskQueueResponse> responseObserver) { return false; }

	@Override
	public boolean routeRespondWorkflowTaskCompleted(RespondWorkflowTaskCompletedRequest request, StreamObserver<RespondWorkflowTaskCompletedResponse> responseObserver) { return false; }

	@Override
	public boolean routeRespondWorkflowTaskFailed(RespondWorkflowTaskFailedRequest request, StreamObserver<RespondWorkflowTaskFailedResponse> responseObserver) { return false; }

	@Override
	public boolean routePollActivityTaskQueue(PollActivityTaskQueueRequest request, StreamObserver<PollActivityTaskQueueResponse> responseObserver) { return false; }

	@Override
	public boolean routeRecordActivityTaskHeartbeat(RecordActivityTaskHeartbeatRequest request, StreamObserver<RecordActivityTaskHeartbeatResponse> responseObserver) { return false; }

	@Override
	public boolean routeRecordActivityTaskHeartbeatById(RecordActivityTaskHeartbeatByIdRequest request, StreamObserver<RecordActivityTaskHeartbeatByIdResponse> responseObserver) { return false; }

	@Override
	public boolean routeRespondActivityTaskCompleted(RespondActivityTaskCompletedRequest request, StreamObserver<RespondActivityTaskCompletedResponse> responseObserver) { return false; }

	@Override
	public boolean routeRespondActivityTaskCompletedById(RespondActivityTaskCompletedByIdRequest request, StreamObserver<RespondActivityTaskCompletedByIdResponse> responseObserver) { return false; }

	@Override
	public boolean routeRespondActivityTaskFailed(RespondActivityTaskFailedRequest request, StreamObserver<RespondActivityTaskFailedResponse> responseObserver) { return false; }

	@Override
	public boolean routeRespondActivityTaskFailedById(RespondActivityTaskFailedByIdRequest request, StreamObserver<RespondActivityTaskFailedByIdResponse> responseObserver) { return false; }

	@Override
	public boolean routeRespondActivityTaskCanceled(RespondActivityTaskCanceledRequest request, StreamObserver<RespondActivityTaskCanceledResponse> responseObserver) { return false; }

	@Override
	public boolean routeRespondActivityTaskCanceledById(RespondActivityTaskCanceledByIdRequest request, StreamObserver<RespondActivityTaskCanceledByIdResponse> responseObserver) { return false; }

	@Override
	public boolean routeRequestCancelWorkflowExecution(RequestCancelWorkflowExecutionRequest request, StreamObserver<RequestCancelWorkflowExecutionResponse> responseObserver) { return false; }

	@Override
	public boolean routeSignalWorkflowExecution(SignalWorkflowExecutionRequest request, StreamObserver<SignalWorkflowExecutionResponse> responseObserver) { return false; }

	@Override
	public boolean routeSignalWithStartWorkflowExecution(SignalWithStartWorkflowExecutionRequest request, StreamObserver<SignalWithStartWorkflowExecutionResponse> responseObserver) { return false; }

	@Override
	public boolean routeResetWorkflowExecution(ResetWorkflowExecutionRequest request, StreamObserver<ResetWorkflowExecutionResponse> responseObserver) { return false; }

	@Override
	public boolean routeTerminateWorkflowExecution(TerminateWorkflowExecutionRequest request, StreamObserver<TerminateWorkflowExecutionResponse> responseObserver) { return false; }

	@Override
	public boolean routeDeleteWorkflowExecution(DeleteWorkflowExecutionRequest request, StreamObserver<DeleteWorkflowExecutionResponse> responseObserver) { return false; }
}
