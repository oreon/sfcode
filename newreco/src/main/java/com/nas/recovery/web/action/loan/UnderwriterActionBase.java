package com.nas.recovery.web.action.loan;

import com.nas.recovery.domain.loan.Underwriter;

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

public abstract class UnderwriterActionBase
		extends
			com.nas.recovery.web.action.loan.PersonAction<Underwriter>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private Underwriter underwriter;

	@DataModel
	private List<Underwriter> underwriterRecordList;

	public void setUnderwriterId(Long id) {

		setId(id);
		loadAssociations();
	}

	public Long getUnderwriterId() {
		return (Long) getId();
	}

	//@Factory("underwriterRecordList")
	//@Observer("archivedUnderwriter")
	public void findRecords() {
		//search();
	}

	public Underwriter getEntity() {
		return underwriter;
	}

	@Override
	public void setEntity(Underwriter t) {
		this.underwriter = t;
		loadAssociations();
	}

	public Underwriter getUnderwriter() {
		return getInstance();
	}

	@Override
	protected Underwriter createInstance() {
		return new Underwriter();
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

	public Underwriter getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public void setUnderwriter(Underwriter t) {
		this.underwriter = t;
		loadAssociations();
	}

	@Override
	public Class<Underwriter> getEntityClass() {
		return Underwriter.class;
	}

	@Override
	public void setEntityList(List<Underwriter> list) {
		this.underwriterRecordList = list;
	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

	}

	public void updateAssociations() {

	}

	public void updateComposedAssociations() {
	}

	public List<Underwriter> getEntityList() {
		if (underwriterRecordList == null) {
			findRecords();
		}
		return underwriterRecordList;
	}

}
