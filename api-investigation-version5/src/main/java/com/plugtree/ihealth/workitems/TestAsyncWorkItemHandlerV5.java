package com.plugtree.ihealth.workitems;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

public class TestAsyncWorkItemHandlerV5 implements WorkItemHandler {

	private WorkItem item = null;
	
	@Override
	public void abortWorkItem(WorkItem item, WorkItemManager manager) {
		this.item = item;
	}

	@Override
	public void executeWorkItem(WorkItem item, WorkItemManager manager) {
		this.item = null;
	}

	public WorkItem getItem() {
		WorkItem retval = item;
		item = null;
		return retval;
	}
}
