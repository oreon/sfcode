package com.nas.recovery.web.action.unusualoccurences;

import com.oreon.callosum.unusualoccurences.UnusualOccurence;

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

public abstract class UnusualOccurenceActionBase
		extends
			BaseAction<UnusualOccurence> implements java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private UnusualOccurence unusualOccurence;

	@In(create = true, value = "occurenceTypeAction")
	com.nas.recovery.web.action.unusualoccurences.OccurenceTypeAction occurenceTypeAction;

	@In(create = true, value = "patientAction")
	com.nas.recovery.web.action.patient.PatientAction patientAction;

	@DataModel
	private List<UnusualOccurence> unusualOccurenceRecordList;

	public void setUnusualOccurenceId(Long id) {
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
	public void setUnusualOccurenceIdForModalDlg(Long id) {
		setId(id);
		loadAssociations();
	}

	public void setOccurenceTypeId(Long id) {

		if (id != null && id > 0)
			getInstance().setOccurenceType(occurenceTypeAction.loadFromId(id));

	}

	public Long getOccurenceTypeId() {
		if (getInstance().getOccurenceType() != null)
			return getInstance().getOccurenceType().getId();
		return 0L;
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

	public void setCreatedById(Long id) {

	}

	public Long getCreatedById() {
		if (getInstance().getCreatedBy() != null)
			return getInstance().getCreatedBy().getId();
		return 0L;
	}

	public Long getUnusualOccurenceId() {
		return (Long) getId();
	}

	public UnusualOccurence getEntity() {
		return unusualOccurence;
	}

	//@Override
	public void setEntity(UnusualOccurence t) {
		this.unusualOccurence = t;
		loadAssociations();
	}

	public UnusualOccurence getUnusualOccurence() {
		return (UnusualOccurence) getInstance();
	}

	@Override
	protected UnusualOccurence createInstance() {
		return new UnusualOccurence();
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();

		com.oreon.callosum.unusualoccurences.OccurenceType occurenceType = occurenceTypeAction
				.getDefinedInstance();
		if (occurenceType != null) {
			getInstance().setOccurenceType(occurenceType);
		}

		com.oreon.callosum.patient.Patient patient = patientAction
				.getDefinedInstance();
		if (patient != null) {
			getInstance().setPatient(patient);
		}

	}

	public boolean isWired() {
		return true;
	}

	public UnusualOccurence getDefinedInstance() {
		return (UnusualOccurence) (isIdDefined() ? getInstance() : null);
	}

	public void setUnusualOccurence(UnusualOccurence t) {
		this.unusualOccurence = t;
		loadAssociations();
	}

	@Override
	public Class<UnusualOccurence> getEntityClass() {
		return UnusualOccurence.class;
	}

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseAction#createExampleCriteria(java.lang.Object)
	 */
	@Override
	public void addAssociations(Criteria criteria) {

		if (unusualOccurence.getOccurenceType() != null) {
			criteria = criteria.add(Restrictions.eq("occurenceType.id",
					unusualOccurence.getOccurenceType().getId()));
		}

		if (unusualOccurence.getPatient() != null) {
			criteria = criteria.add(Restrictions.eq("patient.id",
					unusualOccurence.getPatient().getId()));
		}

		if (unusualOccurence.getCreatedBy() != null) {
			criteria = criteria.add(Restrictions.eq("createdBy.id",
					unusualOccurence.getCreatedBy().getId()));
		}

	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		if (unusualOccurence.getOccurenceType() != null) {
			occurenceTypeAction.setInstance(getInstance().getOccurenceType());
		}

		if (unusualOccurence.getPatient() != null) {
			patientAction.setInstance(getInstance().getPatient());
		}

	}

	public void updateAssociations() {

	}

	public void updateComposedAssociations() {
	}

}
