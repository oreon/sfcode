package com.nas.recovery.web.action.patient;

import com.oreon.callosum.patient.Prescription;

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

import com.oreon.callosum.patient.PrescriptionItem;

public abstract class PrescriptionActionBase extends BaseAction<Prescription>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private Prescription prescription;

	@In(create = true, value = "patientAction")
	com.nas.recovery.web.action.patient.PatientAction patientAction;

	@DataModel
	private List<Prescription> prescriptionRecordList;

	public void setPrescriptionId(Long id) {
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
	public void setPrescriptionIdForModalDlg(Long id) {
		setId(id);
		loadAssociations();
	}

	public void setPatientId(Long id) {

		if (id != null && id > 0)
			getInstance().setPatient(patientAction.loadFromId(id));

	}

	public Long getPatientId() {
		if (getInstance().getPatient() != null)
			return getInstance().getPatient().getId();
		return 0L;
	}

	public Long getPrescriptionId() {
		return (Long) getId();
	}

	public Prescription getEntity() {
		return prescription;
	}

	//@Override
	public void setEntity(Prescription t) {
		this.prescription = t;
		loadAssociations();
	}

	public Prescription getPrescription() {
		return (Prescription) getInstance();
	}

	@Override
	protected Prescription createInstance() {
		return new Prescription();
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();

		com.oreon.callosum.patient.Patient patient = patientAction
				.getDefinedInstance();
		if (patient != null) {
			getInstance().setPatient(patient);
		}

	}

	public boolean isWired() {
		return true;
	}

	public Prescription getDefinedInstance() {
		return (Prescription) (isIdDefined() ? getInstance() : null);
	}

	public void setPrescription(Prescription t) {
		this.prescription = t;
		loadAssociations();
	}

	@Override
	public Class<Prescription> getEntityClass() {
		return Prescription.class;
	}

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseAction#createExampleCriteria(java.lang.Object)
	 */
	@Override
	public void addAssociations(Criteria criteria) {

		if (prescription.getPatient() != null) {
			criteria = criteria.add(Restrictions.eq("patient.id", prescription
					.getPatient().getId()));
		}

	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		if (prescription.getPatient() != null) {
			patientAction.setInstance(getInstance().getPatient());
		}

		initListPrescriptionItems();

	}

	public void updateAssociations() {

	}

	protected List<com.oreon.callosum.patient.PrescriptionItem> listPrescriptionItems = new ArrayList<com.oreon.callosum.patient.PrescriptionItem>();

	void initListPrescriptionItems() {

		if (listPrescriptionItems.isEmpty())
			listPrescriptionItems.addAll(getInstance().getPrescriptionItems());

	}

	public List<com.oreon.callosum.patient.PrescriptionItem> getListPrescriptionItems() {

		return listPrescriptionItems;
	}

	public void setListPrescriptionItems(
			List<com.oreon.callosum.patient.PrescriptionItem> listPrescriptionItems) {
		this.listPrescriptionItems = listPrescriptionItems;
	}

	public void deletePrescriptionItems(int index) {
		listPrescriptionItems.remove(index);
	}

	@Begin(join = true)
	public void addPrescriptionItems() {
		PrescriptionItem prescriptionItems = new PrescriptionItem();

		prescriptionItems.setPrescription(getInstance());

		getListPrescriptionItems().add(prescriptionItems);
	}

	public void updateComposedAssociations() {

		if (listPrescriptionItems != null) {
			getInstance().getPrescriptionItems().clear();
			getInstance().getPrescriptionItems().addAll(listPrescriptionItems);
		}
	}

}
