package com.oreon.cerebrum.web.action.patient;

import com.oreon.cerebrum.patient.Admission;

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

import com.oreon.cerebrum.patient.Admission;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class AdmissionListQueryBase extends BaseQuery<Admission, Long> {

	private static final String EJBQL = "select admission from Admission admission";

	protected Admission admission = new Admission();

	@In(create = true)
	AdmissionAction admissionAction;

	public AdmissionListQueryBase() {
		super();
		setOrderColumn("id");
		setOrderDirection("desc");
	}

	public Admission getAdmission() {
		return admission;
	}

	@Override
	public Admission getInstance() {
		return getAdmission();
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	//@Restrict("#{s:hasPermission('admission', 'view')}")
	public List<Admission> getResultList() {
		return super.getResultList();
	}

	@Override
	public Class<Admission> getEntityClass() {
		return Admission.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private Range<Date> dischargeDateRange = new Range<Date>();

	public Range<Date> getDischargeDateRange() {
		return dischargeDateRange;
	}
	public void setDischargeDate(Range<Date> dischargeDateRange) {
		this.dischargeDateRange = dischargeDateRange;
	}

	private static final String[] RESTRICTIONS = {
			"admission.id = #{admissionList.admission.id}",

			"admission.archived = #{admissionList.admission.archived}",

			"admission.patient.id = #{admissionList.admission.patient.id}",

			"lower(admission.admissionNote) like concat(lower(#{admissionList.admission.admissionNote}),'%')",

			"admission.dischargeDate >= #{admissionList.dischargeDateRange.begin}",
			"admission.dischargeDate <= #{admissionList.dischargeDateRange.end}",

			"admission.bed.id = #{admissionList.admission.bed.id}",

			"lower(admission.dischargeNote) like concat(lower(#{admissionList.admission.dischargeNote}),'%')",

			"admission.dischargeCode = #{admissionList.admission.dischargeCode}",

			"admission.dateCreated <= #{admissionList.dateCreatedRange.end}",
			"admission.dateCreated >= #{admissionList.dateCreatedRange.begin}",};

	public List<Admission> getAdmissionsByPatient(
			com.oreon.cerebrum.patient.Patient patient) {
		admission.setPatient(patient);
		return getResultList();
	}

	@Observer("archivedAdmission")
	public void onArchive() {
		refresh();
	}

	public void setPatientId(Long id) {
		if (admission.getPatient() == null) {
			admission.setPatient(new com.oreon.cerebrum.patient.Patient());
		}
		admission.getPatient().setId(id);
	}

	public Long getPatientId() {
		return admission.getPatient() == null ? null : admission.getPatient()
				.getId();
	}

	public void setBedId(Long id) {
		if (admission.getBed() == null) {
			admission.setBed(new com.oreon.cerebrum.facility.Bed());
		}
		admission.getBed().setId(id);
	}

	public Long getBedId() {
		return admission.getBed() == null ? null : admission.getBed().getId();
	}

	//@Restrict("#{s:hasPermission('admission', 'delete')}")
	public void archiveById(Long id) {
		admissionAction.archiveById(id);
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, Admission e) {

		builder.append("\""
				+ (e.getPatient() != null ? e.getPatient().getDisplayName()
						.replace(",", "") : "") + "\",");

		builder.append("\""
				+ (e.getAdmissionNote() != null ? e.getAdmissionNote().replace(
						",", "") : "") + "\",");

		builder.append("\""
				+ (e.getDischargeDate() != null ? e.getDischargeDate() : "")
				+ "\",");

		builder.append("\""
				+ (e.getBed() != null ? e.getBed().getDisplayName().replace(
						",", "") : "") + "\",");

		builder.append("\""
				+ (e.getDischargeNote() != null ? e.getDischargeNote().replace(
						",", "") : "") + "\",");

		builder.append("\""
				+ (e.getDischargeCode() != null ? e.getDischargeCode() : "")
				+ "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Patient" + ",");

		builder.append("AdmissionNote" + ",");

		builder.append("DischargeDate" + ",");

		builder.append("Bed" + ",");

		builder.append("DischargeNote" + ",");

		builder.append("DischargeCode" + ",");

		builder.append("\r\n");
	}
}
