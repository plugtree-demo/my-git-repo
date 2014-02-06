package com.plugtree.ihealth;

import org.junit.Assert;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.internal.io.ResourceFactory;

import com.plugtree.ihealth.workitems.TestAsyncWorkItemHandlerV6;

public class GlobalVariablesTest {

	@Test
	public void testVersion6Programmatic() throws Exception {
		KieServices ks = KieServices.Factory.get();
		KieFileSystem kfs = ks.newKieFileSystem();
		KieBuilder kbuilder = ks.newKieBuilder(kfs);
		kfs.write("src/main/resources/proc.bpmn2", ResourceFactory.newClassPathResource("kbaseexample/testProcess.bpmn2"));
		kbuilder.buildAll();
		if (kbuilder.getResults().hasMessages(Message.Level.ERROR)) {
			System.err.println(kbuilder.getResults());
			throw new IllegalArgumentException("Problem loading kie base");
		}
		KieContainer kcontainer = ks.newKieContainer(kbuilder.getKieModule().getReleaseId());
		KieSession ksession = kcontainer.getKieBase().newKieSession();
		ksession.addEventListener(new SharedVariableListenerV6());
		TestAsyncWorkItemHandlerV6 handler1 = new TestAsyncWorkItemHandlerV6();
		ksession.getWorkItemManager().registerWorkItemHandler("task1", handler1);
		TestAsyncWorkItemHandlerV6 handler2 = new TestAsyncWorkItemHandlerV6();
		ksession.getWorkItemManager().registerWorkItemHandler("task2", handler2);
		
		ProcessInstance instance1 = ksession.startProcess("com.plugtree.ihealth.testProcess");
		Assert.assertNotNull(instance1);
		Assert.assertEquals(ProcessInstance.STATE_ACTIVE, instance1.getState());
		Assert.assertTrue(instance1 instanceof WorkflowProcessInstance);
		WorkflowProcessInstance wf1 = (WorkflowProcessInstance) instance1;
		Object obj1 = wf1.getVariable(SharedVariableListenerV6.getVariableName());
		Assert.assertNotNull(obj1);
		Assert.assertEquals(obj1, SharedVariableListenerV6.getGlobalVariable());
		
		ProcessInstance instance2 = ksession.startProcess("com.plugtree.ihealth.testProcess");
		Assert.assertNotNull(instance2);
		Assert.assertEquals(ProcessInstance.STATE_ACTIVE, instance2.getState());
		Assert.assertTrue(instance2 instanceof WorkflowProcessInstance);
		WorkflowProcessInstance wf2 = (WorkflowProcessInstance) instance2;
		Object obj2 = wf2.getVariable(SharedVariableListenerV6.getVariableName());
		Assert.assertNotNull(obj2);
		Assert.assertEquals(obj2, SharedVariableListenerV6.getGlobalVariable());
	}
	
	@Test
	public void testVersion6KModule() throws Exception {
		KieServices ks = KieServices.Factory.get();
		KieContainer kcontainer = ks.newKieClasspathContainer();
		KieBase kbase = kcontainer.getKieBase();
		KieSession ksession = kbase.newKieSession();
		ksession.addEventListener(new SharedVariableListenerV6());
		TestAsyncWorkItemHandlerV6 handler1 = new TestAsyncWorkItemHandlerV6();
		TestAsyncWorkItemHandlerV6 handler2 = new TestAsyncWorkItemHandlerV6();
		ksession.getWorkItemManager().registerWorkItemHandler("task1", handler1);
		ksession.getWorkItemManager().registerWorkItemHandler("task2", handler2);

		ProcessInstance instance1 = ksession.startProcess("com.plugtree.ihealth.testProcess");
		Assert.assertNotNull(instance1);
		Assert.assertEquals(ProcessInstance.STATE_ACTIVE, instance1.getState());
		Assert.assertTrue(instance1 instanceof WorkflowProcessInstance);
		WorkflowProcessInstance wf1 = (WorkflowProcessInstance) instance1;
		Object obj1 = wf1.getVariable(SharedVariableListenerV6.getVariableName());
		Assert.assertNotNull(obj1);
		Assert.assertEquals(obj1, SharedVariableListenerV6.getGlobalVariable());
		
		ProcessInstance instance2 = ksession.startProcess("com.plugtree.ihealth.testProcess");
		Assert.assertNotNull(instance2);
		Assert.assertEquals(ProcessInstance.STATE_ACTIVE, instance2.getState());
		Assert.assertTrue(instance2 instanceof WorkflowProcessInstance);
		WorkflowProcessInstance wf2 = (WorkflowProcessInstance) instance2;
		Object obj2 = wf2.getVariable(SharedVariableListenerV6.getVariableName());
		Assert.assertNotNull(obj2);
		Assert.assertEquals(obj2, SharedVariableListenerV6.getGlobalVariable());
	}
}
