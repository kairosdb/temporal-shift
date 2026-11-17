package org.kairosdb.temporal;

import io.jooby.Jooby;
import io.jooby.jetty.JettyServer;
import io.jooby.ServerOptions;
import io.jooby.grpc.GrpcModule;
import io.jooby.guice.GuiceModule;

public class App extends Jooby {

  {
    install(new GuiceModule(new MainGuiceModule()));

    install(new GrpcModule(FrontendService.class, HealthService.class));

    get("/", ctx -> "Welcome to Jooby!");
  }

  public static void main(final String[] args) {
    runApp(args, new JettyServer(), App::new);
  }
}
