package com.plugtree.ihealth;

import org.drools.event.process.DefaultProcessEventListener;
import org.drools.event.process.ProcessStartedEvent;
import org.drools.runtime.process.WorkflowProcessInstance;

public class SharedVariableListenerV5 extends DefaultProcessEventListener {

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
		long processInstanceId = event.getProcessInstance().getId();
		WorkflowProcessInstance workFlow = (WorkflowProcessInstance) event.getKnowledgeRuntime().getProcessInstance(processInstanceId);
		workFlow.setVariable(getVariableName(), getGlobalVariable());
	}
}
