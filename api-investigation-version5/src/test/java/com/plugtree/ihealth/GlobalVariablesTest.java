package com.plugtree.ihealth;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Assert;
import org.junit.Test;

import com.plugtree.ihealth.workitems.TestAsyncWorkItemHandlerV5;

public class GlobalVariablesTest {

	@Test
	public void testVersion5Programmatic() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("kbaseexample/testProcess.bpmn2"), ResourceType.BPMN2);
		if (kbuilder.hasErrors()) {
			for (KnowledgeBuilderError error : kbuilder.getErrors()) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Problem loading knowledge base");
		}
		StatefulKnowledgeSession ksession = kbuilder.newKnowledgeBase().newStatefulKnowledgeSession();
		ksession.addEventListener(new SharedVariableListenerV5());
		TestAsyncWorkItemHandlerV5 handler1 = new TestAsyncWorkItemHandlerV5();
		ksession.getWorkItemManager().registerWorkItemHandler("task1", handler1);
		TestAsyncWorkItemHandlerV5 handler2 = new TestAsyncWorkItemHandlerV5();
		ksession.getWorkItemManager().registerWorkItemHandler("task2", handler2);
		
		org.drools.runtime.process.ProcessInstance instance1 = ksession.startProcess("com.plugtree.ihealth.testProcess");
		Assert.assertNotNull(instance1);
		Assert.assertEquals(org.drools.runtime.process.ProcessInstance.STATE_ACTIVE, instance1.getState());
		Assert.assertTrue(instance1 instanceof org.drools.runtime.process.WorkflowProcessInstance);
		org.drools.runtime.process.WorkflowProcessInstance wf1 = (org.drools.runtime.process.WorkflowProcessInstance) instance1;
		Object obj1 = wf1.getVariable(SharedVariableListenerV5.getVariableName());
		Assert.assertNotNull(obj1);
		Assert.assertEquals(obj1, SharedVariableListenerV5.getGlobalVariable());
		
		org.drools.runtime.process.ProcessInstance instance2 = ksession.startProcess("com.plugtree.ihealth.testProcess");
		Assert.assertNotNull(instance2);
		Assert.assertEquals(org.drools.runtime.process.ProcessInstance.STATE_ACTIVE, instance2.getState());
		Assert.assertTrue(instance2 instanceof org.drools.runtime.process.WorkflowProcessInstance);
		org.drools.runtime.process.WorkflowProcessInstance wf2 = (org.drools.runtime.process.WorkflowProcessInstance) instance2;
		Object obj2 = wf2.getVariable(SharedVariableListenerV5.getVariableName());
		Assert.assertNotNull(obj2);
		Assert.assertEquals(obj2, SharedVariableListenerV5.getGlobalVariable());
	}
}
