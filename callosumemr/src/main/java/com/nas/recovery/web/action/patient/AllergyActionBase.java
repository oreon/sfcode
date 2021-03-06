package com.nas.recovery.web.action.patient;

import com.oreon.callosum.patient.Allergy;

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

public abstract class AllergyActionBase extends BaseAction<Allergy>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private Allergy allergy;

	@In(create = true, value = "patientAction")
	com.nas.recovery.web.action.patient.PatientAction patientAction;

	@In(create = true, value = "allergenAction")
	com.nas.recovery.web.action.patient.AllergenAction allergenAction;

	@DataModel
	private List<Allergy> allergyRecordList;

	public void setAllergyId(Long id) {
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
	public void setAllergyIdForModalDlg(Long id) {
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

	public void setAllergenId(Long id) {

		if (id != null && id > 0)
			getInstance().setAllergen(allergenAction.loadFromId(id));

	}

	public Long getAllergenId() {
		if (getInstance().getAllergen() != null)
			return getInstance().getAllergen().getId();
		return 0L;
	}

	public Long getAllergyId() {
		return (Long) getId();
	}

	public Allergy getEntity() {
		return allergy;
	}

	//@Override
	public void setEntity(Allergy t) {
		this.allergy = t;
		loadAssociations();
	}

	public Allergy getAllergy() {
		return (Allergy) getInstance();
	}

	@Override
	protected Allergy createInstance() {
		return new Allergy();
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

		com.oreon.callosum.patient.Allergen allergen = allergenAction
				.getDefinedInstance();
		if (allergen != null) {
			getInstance().setAllergen(allergen);
		}

	}

	public boolean isWired() {
		return true;
	}

	public Allergy getDefinedInstance() {
		return (Allergy) (isIdDefined() ? getInstance() : null);
	}

	public void setAllergy(Allergy t) {
		this.allergy = t;
		loadAssociations();
	}

	@Override
	public Class<Allergy> getEntityClass() {
		return Allergy.class;
	}

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseAction#createExampleCriteria(java.lang.Object)
	 */
	@Override
	public void addAssociations(Criteria criteria) {

		if (allergy.getPatient() != null) {
			criteria = criteria.add(Restrictions.eq("patient.id", allergy
					.getPatient().getId()));
		}

		if (allergy.getAllergen() != null) {
			criteria = criteria.add(Restrictions.eq("allergen.id", allergy
					.getAllergen().getId()));
		}

	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		if (allergy.getPatient() != null) {
			patientAction.setInstance(getInstance().getPatient());
		}

		if (allergy.getAllergen() != null) {
			allergenAction.setInstance(getInstance().getAllergen());
		}

	}

	public void updateAssociations() {

	}

	public void updateComposedAssociations() {
	}

}
