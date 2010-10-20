package com.nas.recovery.web.action.schedule;

import org.wc.trackrite.schedule.Schedule;

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

import org.wc.trackrite.schedule.ScheduleItem;

public abstract class ScheduleActionBase extends BaseAction<Schedule>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private Schedule schedule;

	@In(create = true, value = "projectAction")
	com.nas.recovery.web.action.issues.ProjectAction projectAction;

	@DataModel
	private List<Schedule> scheduleRecordList;

	public void setScheduleId(Long id) {
		setId(id);
		if (!isPostBack())
			loadAssociations();
	}

	public void setProjectId(Long id) {
		if (id != null && id > 0)
			getInstance().setProject(projectAction.loadFromId(id));
	}

	public Long getProjectId() {
		if (getInstance().getProject() != null)
			return getInstance().getProject().getId();
		return 0L;
	}

	public Long getScheduleId() {
		return (Long) getId();
	}

	//@Factory("scheduleRecordList")
	//@Observer("archivedSchedule")
	public void findRecords() {
		//search();
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
		org.wc.trackrite.issues.Project project = projectAction
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
	@Override
	public void addAssociations(Criteria criteria) {

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
			projectAction.setInstance(getInstance().getProject());
		}

		initListScheduleItems();

	}

	public void updateAssociations() {

	}

	protected List<org.wc.trackrite.schedule.ScheduleItem> listScheduleItems;

	void initListScheduleItems() {
		listScheduleItems = new ArrayList<org.wc.trackrite.schedule.ScheduleItem>();

		if (getInstance().getScheduleItems().isEmpty()) {

		} else
			listScheduleItems.addAll(getInstance().getScheduleItems());

	}

	public List<org.wc.trackrite.schedule.ScheduleItem> getListScheduleItems() {
		if (listScheduleItems == null)
			initListScheduleItems();
		return listScheduleItems;
	}

	public void setListScheduleItems(
			List<org.wc.trackrite.schedule.ScheduleItem> listScheduleItems) {
		this.listScheduleItems = listScheduleItems;
	}

	public void deleteScheduleItems(int index) {
		listScheduleItems.remove(index);
	}

	@Begin(join = true)
	public void addScheduleItems() {
		ScheduleItem scheduleItems = new ScheduleItem();

		getListScheduleItems().add(scheduleItems);
	}

	public void updateComposedAssociations() {

		if (listScheduleItems != null) {
			getInstance().getScheduleItems().clear();
			getInstance().getScheduleItems().addAll(listScheduleItems);
		}
	}

	public List<Schedule> getEntityList() {
		if (scheduleRecordList == null) {
			findRecords();
		}
		return scheduleRecordList;
	}

}