package com.pcas.datapkg.web.action.inventory;

import com.pcas.datapkg.domain.inventory.DrugAbstract;

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
import org.jboss.seam.security.Identity;

import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.annotations.Observer;

import org.witchcraft.base.entity.FileAttachment;

import org.apache.commons.io.FileUtils;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

public abstract class DrugAbstractActionBase extends BaseAction<DrugAbstract>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private DrugAbstract drugAbstract;

	@DataModel
	private List<DrugAbstract> drugAbstractRecordList;

	public void setDrugAbstractId(Long id) {
		if (id == 0) {
			clearInstance();
			clearLists();
			loadAssociations();
			return;
		}
		setId(id);
		if (!isPostBack())
			loadAssociations();
	}

	/** for modal dlg we need to load associaitons regardless of postback
	 * @param id
	 */
	public void setDrugAbstractIdForModalDlg(Long id) {
		setId(id);
		clearLists();
		loadAssociations();
	}

	public Long getDrugAbstractId() {
		return (Long) getId();
	}

	public DrugAbstract getEntity() {
		return drugAbstract;
	}

	//@Override
	public void setEntity(DrugAbstract t) {
		this.drugAbstract = t;
		loadAssociations();
	}

	public DrugAbstract getDrugAbstract() {
		return (DrugAbstract) getInstance();
	}

	@Override
	protected DrugAbstract createInstance() {
		DrugAbstract instance = super.createInstance();

		return instance;
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

	public DrugAbstract getDefinedInstance() {
		return (DrugAbstract) (isIdDefined() ? getInstance() : null);
	}

	public void setDrugAbstract(DrugAbstract t) {
		this.drugAbstract = t;
		if (drugAbstract != null)
			setDrugAbstractId(t.getId());
		loadAssociations();
	}

	@Override
	public Class<DrugAbstract> getEntityClass() {
		return DrugAbstract.class;
	}

	public com.pcas.datapkg.domain.inventory.DrugAbstract findByUnqName(
			String name) {
		return executeSingleResultNamedQuery("drugAbstract.findByUnqName", name);
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

	public void clearLists() {

	}

	public String viewDrugAbstract() {
		load(currentEntityId);
		return "viewDrugAbstract";
	}

}
