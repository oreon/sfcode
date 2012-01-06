package com.oreon.cerebrum.web.action.drugs;

import com.oreon.cerebrum.drugs.Drug;

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

import com.oreon.cerebrum.drugs.DrugInteraction;

public abstract class DrugActionBase extends BaseAction<Drug>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private Drug drug;

	@DataModel
	private List<Drug> drugRecordList;

	public void setDrugId(Long id) {
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
	public void setDrugIdForModalDlg(Long id) {
		setId(id);
		clearLists();
		loadAssociations();
	}

	public Long getDrugId() {
		return (Long) getId();
	}

	public Drug getEntity() {
		return drug;
	}

	//@Override
	public void setEntity(Drug t) {
		this.drug = t;
		loadAssociations();
	}

	public Drug getDrug() {
		return (Drug) getInstance();
	}

	@Override
	protected Drug createInstance() {
		Drug instance = super.createInstance();

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

	public Drug getDefinedInstance() {
		return (Drug) (isIdDefined() ? getInstance() : null);
	}

	public void setDrug(Drug t) {
		this.drug = t;
		if (drug != null)
			setDrugId(t.getId());
		loadAssociations();
	}

	@Override
	public Class<Drug> getEntityClass() {
		return Drug.class;
	}

	public com.oreon.cerebrum.drugs.Drug findByUnqName(String name) {
		return executeSingleResultNamedQuery("drug.findByUnqName", name);
	}

	public com.oreon.cerebrum.drugs.Drug findByUnqDrugBankId(String drugBankId) {
		return executeSingleResultNamedQuery("drug.findByUnqDrugBankId",
				drugBankId);
	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		initListDrugInteractions();

	}

	public void updateAssociations() {

	}

	protected List<com.oreon.cerebrum.drugs.DrugInteraction> listDrugInteractions = new ArrayList<com.oreon.cerebrum.drugs.DrugInteraction>();

	void initListDrugInteractions() {

		if (listDrugInteractions.isEmpty())
			listDrugInteractions.addAll(getInstance().getDrugInteractions());

	}

	public List<com.oreon.cerebrum.drugs.DrugInteraction> getListDrugInteractions() {

		prePopulateListDrugInteractions();
		return listDrugInteractions;
	}

	public void prePopulateListDrugInteractions() {
	}

	public void setListDrugInteractions(
			List<com.oreon.cerebrum.drugs.DrugInteraction> listDrugInteractions) {
		this.listDrugInteractions = listDrugInteractions;
	}

	public void deleteDrugInteractions(int index) {
		listDrugInteractions.remove(index);
	}

	@Begin(join = true)
	public void addDrugInteractions() {
		initListDrugInteractions();
		DrugInteraction drugInteractions = new DrugInteraction();

		drugInteractions.setDrug(getInstance());

		getListDrugInteractions().add(drugInteractions);
	}

	public void updateComposedAssociations() {

		if (listDrugInteractions != null) {
			getInstance().getDrugInteractions().clear();
			getInstance().getDrugInteractions().addAll(listDrugInteractions);
		}
	}

	public void clearLists() {
		listDrugInteractions.clear();

	}

	public String viewDrug() {
		load(currentEntityId);
		return "viewDrug";
	}

}