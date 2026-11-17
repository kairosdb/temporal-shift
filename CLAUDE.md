# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
mvn clean package

# Run (dev mode with hot reload)
mvn clean jooby:run

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=WorkflowTest

# Run a single test method
mvn test -Dtest=WorkflowTest#greetingWorkflowStartsOnServer

# Docker
docker build . -t temporal-shift
docker run -p 7233:7233 -it temporal-shift
```

## Architecture

This project is a **Temporal server replacement** — it implements the Temporal gRPC API (`temporal.api.workflowservice.v1.WorkflowService`) so that Temporal SDK clients can connect to it instead of the official Temporal server.

### gRPC transport

`jooby-grpc` runs an **in-process** gRPC server (via `InProcessServerBuilder`) and routes incoming HTTP/2 requests through Jetty via a `DefaultGrpcProcessor` handler. There is no separate gRPC port — gRPC and HTTP share the same Jetty listener. The server listens on port **7233** (Temporal's default) over TLS/h2.

### Configuration

- `conf/application.conf` — production config (HOCON). The `conf/` directory is on the classpath as a resource root (see pom.xml `<resources>`).
- `src/test/resources/application.conf` — overrides the production config during tests: plain HTTP/2, random port, no TLS. This file wins because Maven places test resources before main resources on the test classpath.
- TLS: a self-signed cert lives in `certs/server.crt` (committed) and `conf/keystore.p12` (gitignored). The private key `certs/server.key` is also gitignored. Keystore password is `changeit`.

### Key classes

- **`App`** — Jooby entry point. Installs `GuiceModule` and `GrpcModule`. Adding a new gRPC service requires passing its class to `GrpcModule` here and binding it as a singleton in `MainGuiceModule`.
- **`FrontendService`** — implements `WorkflowServiceGrpc.WorkflowServiceImplBase`. All methods default to `UNIMPLEMENTED` via the shared `unimplemented()` helper. Only `getSystemInfo` is currently implemented.
- **`HealthService`** — implements `HealthGrpc.HealthImplBase` (`grpc.health.v1.Health`). `Check` always returns `SERVING`.
- **`MainGuiceModule`** — Guice bindings. All gRPC services registered in `GrpcModule` must also be bound as `Singleton` here.

### Adding a new RPC implementation

1. Find the method stub in `FrontendService` that calls `unimplemented(...)`.
2. Replace it with a real implementation using the protobuf builder pattern:
   ```java
   responseObserver.onNext(SomeResponse.newBuilder()...build());
   responseObserver.onCompleted();
   ```
3. Use `responseObserver.onError(Status.INTERNAL.withDescription("...").asRuntimeException())` for error paths.

### Protobuf types

All Temporal request/response types come from `io.temporal:temporal-serviceclient` (not the full SDK). The generated classes live under `io.temporal.api.*`. The gRPC service base class is `io.temporal.api.workflowservice.v1.WorkflowServiceGrpc.WorkflowServiceImplBase`.

The gRPC health check types (`HealthCheckRequest`, `HealthCheckResponse`) come from `io.grpc:grpc-services`.

### Deployment packaging

`mvn package` uses jooby-stork (via `tiles-maven-plugin`) to produce a `target/stork/` directory with a `bin/temporal-shift` launcher. The Dockerfile copies this into the final image and runs it with `--run`.
