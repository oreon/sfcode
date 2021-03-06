package com.nas.recovery.web.action.workflowmgt;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.bpm.StartTask;
import org.jbpm.JbpmContext;
import org.witchcraft.jbpm.BaseJbpmProcessAction;

public class LeaveRequestProcessActionBase extends BaseJbpmProcessAction
		implements
			java.io.Serializable {

	@CreateProcess(definition = "leaveRequest")
	public void startProcess() {

	}

	@StartTask
	public void startProvideDetails() {

	}

	@EndTask(transition = "retract")
	public void retractProvideDetails() {

	}
	@EndTask(transition = "submit")
	public void submitProvideDetails() {

	}

	@StartTask
	public void startReviewRequest() {

	}

	@EndTask(transition = "moreDetails")
	public void moreDetailsReviewRequest() {

	}
	@EndTask(transition = "reject")
	public void rejectReviewRequest() {

	}
	@EndTask(transition = "accept")
	public void acceptReviewRequest() {

	}

}
