package com.nas.recoveryportal.appraisal.action;

import com.nas.recoveryportal.appraisal.Schedule;

import org.witchcraft.seam.action.BaseAction;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import org.apache.commons.lang.StringUtils;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.Component;

import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.annotations.Observer;

import com.nas.recoveryportal.appraisal.ScheduleItem;

public class ScheduleActionBase extends BaseAction<Schedule>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private Schedule schedule;

	@In(create = true, value = "projectAction")
	com.nas.recoveryportal.appraisal.action.ProjectAction projectAction;

	@DataModel
	private List<Schedule> scheduleRecordList;

	public void setScheduleId(Long id) {

		listScheduleItem = new ArrayList<ScheduleItem>();

		setId(id);
		loadAssociations();
	}

	public Long getScheduleId() {
		return (Long) getId();
	}

	@Factory("scheduleRecordList")
	@Observer("archivedSchedule")
	public void findRecords() {
		search();
	}

	public Schedule getEntity() {
		return schedule;
	}

	@Override
	public void setEntity(Schedule t) {
		this.schedule = t;
		loadAssociations();
	}

	public Schedule getSchedule() {
		return getInstance();
	}

	@Override
	protected Schedule createInstance() {
		return new Schedule();
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		com.nas.recoveryportal.appraisal.Project project = projectAction
				.getDefinedInstance();
		if (project != null) {
			getInstance().setProject(project);
		}

	}

	public boolean isWired() {
		return true;
	}

	public Schedule getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public void setSchedule(Schedule t) {
		this.schedule = t;
		loadAssociations();
	}

	@Override
	public Class<Schedule> getEntityClass() {
		return Schedule.class;
	}

	@Override
	public void setEntityList(List<Schedule> list) {
		this.scheduleRecordList = list;
	}

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseAction#createExampleCriteria(java.lang.Object)
	 */
	public void addAssoications(Criteria criteria) {

		if (schedule.getProject() != null) {
			criteria = criteria.add(Restrictions.eq("project.id", schedule
					.getProject().getId()));
		}

	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		if (schedule.getProject() != null) {
			projectAction.setEntity(getEntity().getProject());
		}

	}

	public void updateAssociations() {

	}

	private List<ScheduleItem> listScheduleItem;

	void initListScheduleItem() {
		listScheduleItem = new ArrayList<ScheduleItem>();
		if (getInstance().getScheduleItem().isEmpty()) {

		} else
			listScheduleItem.addAll(getInstance().getScheduleItem());
	}

	public List<ScheduleItem> getListScheduleItem() {
		if (listScheduleItem == null) {
			initListScheduleItem();
		}
		return listScheduleItem;
	}

	public void setListScheduleItem(List<ScheduleItem> listScheduleItem) {
		this.listScheduleItem = listScheduleItem;
	}

	public void deleteScheduleItem(ScheduleItem scheduleItem) {
		listScheduleItem.remove(scheduleItem);
	}

	@Begin(join = true)
	public void addScheduleItem() {
		ScheduleItem scheduleItem = new ScheduleItem();

		scheduleItem.setSchedule(getInstance());

		listScheduleItem.add(scheduleItem);
	}

	public void updateComposedAssociations() {

		getInstance().getScheduleItem().clear();
		getInstance().getScheduleItem().addAll(listScheduleItem);

	}

	public List<Schedule> getEntityList() {
		if (scheduleRecordList == null) {
			findRecords();
		}
		return scheduleRecordList;
	}

}
