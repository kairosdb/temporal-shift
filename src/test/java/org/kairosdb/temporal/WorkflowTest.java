package org.kairosdb.temporal;

import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import io.grpc.ManagedChannel;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowServiceException;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 These unit tests create an inprocess grpc server and connects directly without
 going over the network.
 */
public class WorkflowTest {

	private static final String TASK_QUEUE = "test-queue";

	@WorkflowInterface
	public interface GreetingWorkflow {
		@WorkflowMethod
		String greet(String name);
	}

	public static class GreetingWorkflowImpl implements GreetingWorkflow {
		@Override
		public String greet(String name) {
			return "Hello, " + name;
		}
	}

	@Test
	public void greetingWorkflowStartsOnServer() throws Exception {

		GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();
		// 1. Unique name for the in-process server
		String serverName = InProcessServerBuilder.generateName();

		// 2. Register your service implementation with the in-process server
		grpcCleanup.register(InProcessServerBuilder.forName(serverName)
				.directExecutor()
				.addService(new FrontendService())
				.build()
				.start());

		// 3. Create a client channel connected to the in-process server
		ManagedChannel channel = grpcCleanup.register(
				InProcessChannelBuilder.forName(serverName).directExecutor().build()
		);

		try {
			WorkflowServiceStubs stubs = WorkflowServiceStubs.newServiceStubs(
					WorkflowServiceStubsOptions.newBuilder()
							.setChannel(channel)
							.build());
			try
			{
				WorkflowClient client = WorkflowClient.newInstance(stubs);

				GreetingWorkflow workflow = client.newWorkflowStub(GreetingWorkflow.class,
						WorkflowOptions.newBuilder()
								.setWorkflowId("greeting-" + UUID.randomUUID())
								.setTaskQueue(TASK_QUEUE)
								.build());

				// TODO: once startWorkflowExecution is implemented, change to:
				//   WorkflowRun run = WorkflowClient.start(workflow::greet, "World");
				//   assertEquals("Hello, World", workflow.greet("World"));
				WorkflowServiceException ex = assertThrows(WorkflowServiceException.class,
						() -> WorkflowClient.start(workflow::greet, "World"));
				assertEquals(io.grpc.Status.Code.UNIMPLEMENTED,
						io.grpc.Status.fromThrowable(ex.getCause()).getCode());
			}
			finally
			{
				stubs.shutdown();
			}
		} finally {
			channel.shutdownNow();
		}
	}
}
