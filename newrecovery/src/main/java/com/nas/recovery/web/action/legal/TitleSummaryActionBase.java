package com.nas.recovery.web.action.legal;

import com.nas.recovery.domain.legal.TitleSummary;

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

public class TitleSummaryActionBase extends BaseAction<TitleSummary>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private TitleSummary titleSummary;

	@DataModel
	private List<TitleSummary> titleSummaryRecordList;

	public void setTitleSummaryId(Long id) {

		setId(id);
		loadAssociations();
	}

	public Long getTitleSummaryId() {
		return (Long) getId();
	}

	//@Factory("titleSummaryRecordList")
	//@Observer("archivedTitleSummary")
	public void findRecords() {
		//search();
	}

	public TitleSummary getEntity() {
		return titleSummary;
	}

	@Override
	public void setEntity(TitleSummary t) {
		this.titleSummary = t;
		loadAssociations();
	}

	public TitleSummary getTitleSummary() {
		return getInstance();
	}

	@Override
	protected TitleSummary createInstance() {
		return new TitleSummary();
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

	public TitleSummary getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public void setTitleSummary(TitleSummary t) {
		this.titleSummary = t;
		loadAssociations();
	}

	@Override
	public Class<TitleSummary> getEntityClass() {
		return TitleSummary.class;
	}

	@Override
	public void setEntityList(List<TitleSummary> list) {
		this.titleSummaryRecordList = list;
	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

	}

	public void updateAssociations() {

	}

	public List<TitleSummary> getEntityList() {
		if (titleSummaryRecordList == null) {
			findRecords();
		}
		return titleSummaryRecordList;
	}

}
