
	
package com.jonah.mentormatcher.web.action.mentorship;
	

import java.util.List;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;

import com.jonah.mentormatcher.domain.mentorship.JoinRequest;
import com.jonah.mentormatcher.domain.mentorship.MentorshipOffering;
import com.jonah.mentormatcher.web.action.domain.EmployeeAction;
import com.jonah.mentormatcher.web.action.workflowmgt.ApproveMentorshipProcessAction;

	
//@Scope(ScopeType.CONVERSATION)
@Name("mentorshipOfferingAction")
public class MentorshipOfferingAction extends MentorshipOfferingActionBase implements java.io.Serializable{
	
	@In(create=true)
	ApproveMentorshipProcessAction approveMentorshipProcessAction;
	
	@In(create=true)
	Identity identity;
	
	@Override
	protected MentorshipOffering createInstance() {
		// TODO Auto-generated method stub
		return super.createInstance();
	}
	
	
	public void apply(){
		//MentorshipOfferingAction mentorshipOfferingAction = (MentorshipOfferingAction) Component.getInstance("mentorshipOfferingAction");
		//persist();
		EmployeeAction employeeAction = (EmployeeAction) Component.getInstance("employeeAction");
		
		JoinRequest joinRequest = new JoinRequest();
		long id = getInstance().getId() != null ? getInstance().getId() : currentEntityId;
		joinRequest.setMentorshipOffering(entityManager.find(MentorshipOffering.class, id) );
		joinRequest.setProspectiveMentee(employeeAction.getCurrentLoggedInEmployee());
		
		JoinRequestAction joinRequestAction = (JoinRequestAction) Component.getInstance("joinRequestAction");
		joinRequest.setRequestText(joinRequestAction.getInstance().getRequestText());
		joinRequestAction.persist(joinRequest);
		joinRequestAction.setInstance(joinRequest);
		
		approveMentorshipProcessAction.setProcessToken(joinRequest);
		approveMentorshipProcessAction.startProcess();
	}
	
	@Override
	public String createOffering() {
		save();
		return "success";
	}
	
	public String viewMentorshipOffering(){
		return super.viewMentorshipOffering();
	}
	
	
	public String applyForMentorship(){
		apply();
		return "applicationSuccess";
	}
	
	public List<MentorshipOffering> getMyOfferings(){
		String qry = "Select e from MentorshipOffering e where e.mentor.appUser.userName = ?1";
		return executeQuery(qry, identity.getCredentials().getUsername());
	}
	
}
	