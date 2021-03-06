package com.oreon.cerebrum.web.action.prescription;

import com.oreon.cerebrum.prescription.Prescription;

import org.witchcraft.seam.action.BaseAction;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import org.witchcraft.base.entity.BaseQuery;
import org.witchcraft.base.entity.Range;

import org.jboss.seam.annotations.Observer;

import java.math.BigDecimal;

import org.jboss.seam.annotations.security.Restrict;

import org.jboss.seam.annotations.In;

import com.oreon.cerebrum.prescription.Prescription;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class PrescriptionListQueryBase
		extends
			BaseQuery<Prescription, Long> {

	private static final String EJBQL = "select prescription from Prescription prescription";

	protected Prescription prescription = new Prescription();

	@In(create = true)
	PrescriptionAction prescriptionAction;

	public PrescriptionListQueryBase() {
		super();
		setOrderColumn("id");
		setOrderDirection("desc");
	}

	public Prescription getPrescription() {
		return prescription;
	}

	@Override
	public Prescription getInstance() {
		return getPrescription();
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	//@Restrict("#{s:hasPermission('prescription', 'view')}")
	public List<Prescription> getResultList() {
		return super.getResultList();
	}

	@Override
	public Class<Prescription> getEntityClass() {
		return Prescription.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"prescription.id = #{prescriptionList.prescription.id}",

			"prescription.archived = #{prescriptionList.prescription.archived}",

			"prescription.patient.id = #{prescriptionList.prescription.patient.id}",

			"lower(prescription.directivesForPatient) like concat(lower(#{prescriptionList.prescription.directivesForPatient}),'%')",

			"prescription.active = #{prescriptionList.prescription.active}",

			"prescription.dateCreated <= #{prescriptionList.dateCreatedRange.end}",
			"prescription.dateCreated >= #{prescriptionList.dateCreatedRange.begin}",};

	public List<Prescription> getPrescriptionsByPatient(
			com.oreon.cerebrum.patient.Patient patient) {
		prescription.setPatient(patient);
		return getResultList();
	}

	@Observer("archivedPrescription")
	public void onArchive() {
		refresh();
	}

	public void setPatientId(Long id) {
		if (prescription.getPatient() == null) {
			prescription.setPatient(new com.oreon.cerebrum.patient.Patient());
		}
		prescription.getPatient().setId(id);
	}

	public Long getPatientId() {
		return prescription.getPatient() == null ? null : prescription
				.getPatient().getId();
	}

	//@Restrict("#{s:hasPermission('prescription', 'delete')}")
	public void archiveById(Long id) {
		prescriptionAction.archiveById(id);
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, Prescription e) {

		builder.append("\""
				+ (e.getPatient() != null ? e.getPatient().getDisplayName()
						.replace(",", "") : "") + "\",");

		builder.append("\""
				+ (e.getDirectivesForPatient() != null ? e
						.getDirectivesForPatient().replace(",", "") : "")
				+ "\",");

		builder.append("\"" + (e.getActive() != null ? e.getActive() : "")
				+ "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Patient" + ",");

		builder.append("DirectivesForPatient" + ",");

		builder.append("Active" + ",");

		builder.append("\r\n");
	}
}
