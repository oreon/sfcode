package com.nas.recovery.web.action.patient;

import com.oreon.callosum.patient.Immunization;

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

public abstract class ImmunizationActionBase extends BaseAction<Immunization>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private Immunization immunization;

	@In(create = true, value = "patientAction")
	com.nas.recovery.web.action.patient.PatientAction patientAction;

	@In(create = true, value = "vaccineAction")
	com.nas.recovery.web.action.patient.VaccineAction vaccineAction;

	@DataModel
	private List<Immunization> immunizationRecordList;

	public void setImmunizationId(Long id) {
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
	public void setImmunizationIdForModalDlg(Long id) {
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

	public void setVaccineId(Long id) {

		if (id != null && id > 0)
			getInstance().setVaccine(vaccineAction.loadFromId(id));

	}

	public Long getVaccineId() {
		if (getInstance().getVaccine() != null)
			return getInstance().getVaccine().getId();
		return 0L;
	}

	public Long getImmunizationId() {
		return (Long) getId();
	}

	public Immunization getEntity() {
		return immunization;
	}

	//@Override
	public void setEntity(Immunization t) {
		this.immunization = t;
		loadAssociations();
	}

	public Immunization getImmunization() {
		return (Immunization) getInstance();
	}

	@Override
	protected Immunization createInstance() {
		return new Immunization();
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

		com.oreon.callosum.patient.Vaccine vaccine = vaccineAction
				.getDefinedInstance();
		if (vaccine != null) {
			getInstance().setVaccine(vaccine);
		}

	}

	public boolean isWired() {
		return true;
	}

	public Immunization getDefinedInstance() {
		return (Immunization) (isIdDefined() ? getInstance() : null);
	}

	public void setImmunization(Immunization t) {
		this.immunization = t;
		loadAssociations();
	}

	@Override
	public Class<Immunization> getEntityClass() {
		return Immunization.class;
	}

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseAction#createExampleCriteria(java.lang.Object)
	 */
	@Override
	public void addAssociations(Criteria criteria) {

		if (immunization.getPatient() != null) {
			criteria = criteria.add(Restrictions.eq("patient.id", immunization
					.getPatient().getId()));
		}

		if (immunization.getVaccine() != null) {
			criteria = criteria.add(Restrictions.eq("vaccine.id", immunization
					.getVaccine().getId()));
		}

	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		if (immunization.getPatient() != null) {
			patientAction.setInstance(getInstance().getPatient());
		}

		if (immunization.getVaccine() != null) {
			vaccineAction.setInstance(getInstance().getVaccine());
		}

	}

	public void updateAssociations() {

	}

	public void updateComposedAssociations() {
	}

}
