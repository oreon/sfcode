package com.nas.recovery.web.action.issues;

import java.awt.Component;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.bpm.PooledTaskInstanceList;
import org.jboss.seam.bpm.ProcessInstance;

import org.wc.trackrite.domain.Employee;
import org.wc.trackrite.issues.Issue;
import org.wc.trackrite.issues.Status;
import org.witchcraft.exceptions.ContractViolationException;

import com.nas.recovery.web.action.domain.EmployeeAction;
import com.nas.recovery.web.action.workflowmgt.BugManagement;
import com.nas.recovery.web.action.workflowmgt.BugManagementProcessAction;

import org.jbpm.taskmgmt.exe.TaskInstance;

//// CMTD @Scope(ScopeType.CONVERSATION)
@Name("issueAction")
public class IssueAction extends IssueActionBase implements
		java.io.Serializable {

	@In(scope = ScopeType.BUSINESS_PROCESS, required = false)
	Issue token;

	@In(create = true, value = "bugManagementProcessAction")
	BugManagementProcessAction bugManagement;

	@In(create = true)
	EmployeeAction employeeAction;

	// @In(create=true)
	// PooledTaskInstanceList pooledTaskInstanceList;

	// @In
	// protected JbpmContext jbpmContext;

	@Override
	public String save() {
		Employee prevDeveloper = null;
		if (!isNew())
			prevDeveloper = getEntityManager().find(Issue.class, getId())
					.getDeveloper();

		boolean isNew = isNew();
		String ret = super.save();

		if (isNew) {
			launchProcess();
			getInstance().setProcessId(ProcessInstance.instance().getId());
		} else {
			if (getInstance().getDeveloper() != null)
				bugManagement.updateUserForPooledTask(getInstance()
						.getDeveloper().getUser().getUserName(), getInstance()
						.getProcessId(), getInstance());
		}

		super.save();
		return ret;
	}

	@Override
	public String saveWithoutConversation() {
		// TODO Auto-generated method stub
		return save();
	}

	private void launchProcess() {
		bugManagement.setToken(getInstance());
		bugManagement.startProcess();
	}

	public Issue getToken() {
		if (token == null || token.getId() == null) {
			token = (Issue) bugManagement.getTask().getVariable("token");
		}
		return token;
	}

	public Issue refreshToken(Issue toke) {
		return loadFromId(toke.getId());
	}

	public void updateDeveloper() {

		load(getToken().getId());
		getInstance().setDeveloper(employeeAction.getCurrentLoggedInEmployee());
		token.setDeveloper(employeeAction.getCurrentLoggedInEmployee());
		bugManagement.getTask().setVariable("token", token);
		save();
	}

	public void updateStatus(String status) {
		if (StringUtils.isEmpty(status))
			throw new ContractViolationException(
					"Recieved empty string for updating status");

		load(getToken().getId());

		try {
			getInstance().setStatus(Status.valueOf(status));
		} catch (NullPointerException npe) {
			throw new ContractViolationException("Issue is null");
		} catch (Exception e) {
			throw new ContractViolationException(status

			+ " couldnt be cast to an enum literal of type 'Status'");
		}

		save();

	}

	public String createMessage() {
		// ManagedJbpmContext.instance().getTaskMgmtSession().findTaskInstancesByToken(tokenId)
		System.out.println("creating message from seam");
		try {
			sendMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "true";
	}

	public static String brokerURL = "tcp://localhost:61616";

	public void sendMessage() throws Exception {
		// setup the connection to ActiveMQ
		ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
		Issue issue = loadFromId(1L);
		Producer producer = new Producer(factory, "test", issue);
		producer.run();
		producer.close();

	}

	public static void main(String[] args) {
		Status status = Status.valueOf("Started");
		System.out.println(status);
	}

}
