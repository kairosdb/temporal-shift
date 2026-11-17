package org.kairosdb.temporal;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;

public class MainGuiceModule implements Module
{
	@Override
	public void configure(Binder binder)
	{
		binder.bind(Orchestrator.class).in(Singleton.class);
		binder.bind(QueueService.class).in(Singleton.class);
		binder.bind(RoutingService.class).to(QueueService.class).in(Singleton.class);
		binder.bind(DataStore.class).to(org.kairosdb.temporal.h2.DataStoreImpl.class).in(Singleton.class);
		binder.bind(FrontendService.class).in(Singleton.class);
		binder.bind(HealthService.class).in(Singleton.class);
	}
}
