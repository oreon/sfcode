package com.nas.recovery.web.action.workflowmgt.bugManagement.assign;

import org.jboss.seam.Component;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;

public class QaAssignment implements AssignmentHandler {

	public void assign(Assignable assignable, ExecutionContext executionContext)
			throws Exception {
		//assignable.setPooledActors(new String[]{"qa"});
	}

}
