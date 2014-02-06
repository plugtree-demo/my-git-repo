package com.plugtree.ihealth;

import org.kie.api.event.process.DefaultProcessEventListener;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.runtime.process.WorkflowProcessInstance;

public class SharedVariableListenerV6 extends DefaultProcessEventListener {

	//variable service
	
	private static final GlobalVariable GLOBAL_VARIABLE = new GlobalVariable();
	private static final String VARIABLE_NAME = "common_variable";
	
	public static GlobalVariable getGlobalVariable() {
		return GLOBAL_VARIABLE;
	}
	
	public static String getVariableName() {
		return VARIABLE_NAME;
	}
	
	//global variable setters
	
	@Override
	public void beforeProcessStarted(ProcessStartedEvent event) {
		WorkflowProcessInstance workFlow = (WorkflowProcessInstance) event.getProcessInstance();
		workFlow.setVariable(getVariableName(), getGlobalVariable());
	}
}
