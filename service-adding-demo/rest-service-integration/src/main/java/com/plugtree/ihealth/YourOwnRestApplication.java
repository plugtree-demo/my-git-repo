package com.plugtree.ihealth;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class YourOwnRestApplication extends Application {

	private static final Set<Class<?>> classSet = new HashSet<Class<?>>();
	
	static {
		classSet.add(MyExtendedRestResource.class);
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		return classSet;
	}
}
