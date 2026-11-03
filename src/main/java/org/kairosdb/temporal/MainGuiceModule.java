package org.kairosdb.temporal;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;

public class MainGuiceModule implements Module
{
	@Override
	public void configure(Binder binder)
	{
		binder.bind(FrontendService.class).in(Singleton.class);
	}
}
