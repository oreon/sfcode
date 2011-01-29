package com.nas.recovery.web.action.incidents;

import com.oreon.trkincidents.incidents.IncidentType;

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

import com.oreon.trkincidents.incidents.FormField;
import com.oreon.trkincidents.incidents.ReferenceField;

public abstract class IncidentTypeActionBase extends BaseAction<IncidentType>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private IncidentType incidentType;

	@DataModel
	private List<IncidentType> incidentTypeRecordList;

	public void setIncidentTypeId(Long id) {
		if (id == 0) {
			clearInstance();
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
	public void setIncidentTypeIdForModalDlg(Long id) {
		setId(id);
		loadAssociations();
	}

	public Long getIncidentTypeId() {
		return (Long) getId();
	}

	public IncidentType getEntity() {
		return incidentType;
	}

	//@Override
	public void setEntity(IncidentType t) {
		this.incidentType = t;
		loadAssociations();
	}

	public IncidentType getIncidentType() {
		return (IncidentType) getInstance();
	}

	@Override
	protected IncidentType createInstance() {
		return new IncidentType();
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

	public IncidentType getDefinedInstance() {
		return (IncidentType) (isIdDefined() ? getInstance() : null);
	}

	public void setIncidentType(IncidentType t) {
		this.incidentType = t;
		loadAssociations();
	}

	@Override
	public Class<IncidentType> getEntityClass() {
		return IncidentType.class;
	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		initListFormFields();

		initListReferenceFields();

	}

	public void updateAssociations() {

	}

	protected List<com.oreon.trkincidents.incidents.FormField> listFormFields = new ArrayList<com.oreon.trkincidents.incidents.FormField>();

	void initListFormFields() {

		if (listFormFields.isEmpty())
			listFormFields.addAll(getInstance().getFormFields());

	}

	public List<com.oreon.trkincidents.incidents.FormField> getListFormFields() {

		prePopulateListFormFields();
		return listFormFields;
	}

	public void prePopulateListFormFields() {
	}

	public void setListFormFields(
			List<com.oreon.trkincidents.incidents.FormField> listFormFields) {
		this.listFormFields = listFormFields;
	}

	public void deleteFormFields(int index) {
		listFormFields.remove(index);
	}

	@Begin(join = true)
	public void addFormFields() {
		initListFormFields();
		FormField formFields = new FormField();

		getListFormFields().add(formFields);
	}

	protected List<com.oreon.trkincidents.incidents.ReferenceField> listReferenceFields = new ArrayList<com.oreon.trkincidents.incidents.ReferenceField>();

	void initListReferenceFields() {

		if (listReferenceFields.isEmpty())
			listReferenceFields.addAll(getInstance().getReferenceFields());

	}

	public List<com.oreon.trkincidents.incidents.ReferenceField> getListReferenceFields() {

		prePopulateListReferenceFields();
		return listReferenceFields;
	}

	public void prePopulateListReferenceFields() {
	}

	public void setListReferenceFields(
			List<com.oreon.trkincidents.incidents.ReferenceField> listReferenceFields) {
		this.listReferenceFields = listReferenceFields;
	}

	public void deleteReferenceFields(int index) {
		listReferenceFields.remove(index);
	}

	@Begin(join = true)
	public void addReferenceFields() {
		initListReferenceFields();
		ReferenceField referenceFields = new ReferenceField();

		referenceFields.setIncidentType(getInstance());

		getListReferenceFields().add(referenceFields);
	}

	public void updateComposedAssociations() {

		if (listFormFields != null) {
			getInstance().getFormFields().clear();
			getInstance().getFormFields().addAll(listFormFields);
		}

		if (listReferenceFields != null) {
			getInstance().getReferenceFields().clear();
			getInstance().getReferenceFields().addAll(listReferenceFields);
		}
	}

	public void clearLists() {
		listFormFields.clear();
		listReferenceFields.clear();

	}

}