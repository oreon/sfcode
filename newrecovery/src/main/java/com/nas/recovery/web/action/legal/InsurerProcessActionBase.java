package com.nas.recovery.web.action.legal;

import com.nas.recovery.domain.legal.InsurerProcess;

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

public class InsurerProcessActionBase extends BaseAction<InsurerProcess>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private InsurerProcess insurerProcess;

	@DataModel
	private List<InsurerProcess> insurerProcessRecordList;

	public void setInsurerProcessId(Long id) {

		setId(id);
		loadAssociations();
	}

	public Long getInsurerProcessId() {
		return (Long) getId();
	}

	//@Factory("insurerProcessRecordList")
	//@Observer("archivedInsurerProcess")
	public void findRecords() {
		//search();
	}

	public InsurerProcess getEntity() {
		return insurerProcess;
	}

	@Override
	public void setEntity(InsurerProcess t) {
		this.insurerProcess = t;
		loadAssociations();
	}

	public InsurerProcess getInsurerProcess() {
		return getInstance();
	}

	@Override
	protected InsurerProcess createInstance() {
		return new InsurerProcess();
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();

	}

	public boolean isWired() {
		return true;
	}

	public InsurerProcess getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public void setInsurerProcess(InsurerProcess t) {
		this.insurerProcess = t;
		loadAssociations();
	}

	@Override
	public Class<InsurerProcess> getEntityClass() {
		return InsurerProcess.class;
	}

	@Override
	public void setEntityList(List<InsurerProcess> list) {
		this.insurerProcessRecordList = list;
	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

	}

	public void updateAssociations() {

	}

	public List<InsurerProcess> getEntityList() {
		if (insurerProcessRecordList == null) {
			findRecords();
		}
		return insurerProcessRecordList;
	}

}
