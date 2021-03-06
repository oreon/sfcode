package com.oreon.cerebrum.web.action.prescription;

import com.oreon.cerebrum.prescription.PrescriptionTemplate;

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

import com.oreon.cerebrum.prescription.PrescriptionTemplate;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class PrescriptionTemplateListQueryBase
		extends
			BaseQuery<PrescriptionTemplate, Long> {

	private static final String EJBQL = "select prescriptionTemplate from PrescriptionTemplate prescriptionTemplate";

	protected PrescriptionTemplate prescriptionTemplate = new PrescriptionTemplate();

	@In(create = true)
	PrescriptionTemplateAction prescriptionTemplateAction;

	public PrescriptionTemplateListQueryBase() {
		super();
		setOrderColumn("id");
		setOrderDirection("desc");
	}

	public PrescriptionTemplate getPrescriptionTemplate() {
		return prescriptionTemplate;
	}

	@Override
	public PrescriptionTemplate getInstance() {
		return getPrescriptionTemplate();
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	//@Restrict("#{s:hasPermission('prescriptionTemplate', 'view')}")
	public List<PrescriptionTemplate> getResultList() {
		return super.getResultList();
	}

	@Override
	public Class<PrescriptionTemplate> getEntityClass() {
		return PrescriptionTemplate.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"prescriptionTemplate.id = #{prescriptionTemplateList.prescriptionTemplate.id}",

			"prescriptionTemplate.archived = #{prescriptionTemplateList.prescriptionTemplate.archived}",

			"lower(prescriptionTemplate.name) like concat(lower(#{prescriptionTemplateList.prescriptionTemplate.name}),'%')",

			"lower(prescriptionTemplate.directivesForPatient) like concat(lower(#{prescriptionTemplateList.prescriptionTemplate.directivesForPatient}),'%')",

			"prescriptionTemplate.dateCreated <= #{prescriptionTemplateList.dateCreatedRange.end}",
			"prescriptionTemplate.dateCreated >= #{prescriptionTemplateList.dateCreatedRange.begin}",};

	@Observer("archivedPrescriptionTemplate")
	public void onArchive() {
		refresh();
	}

	//@Restrict("#{s:hasPermission('prescriptionTemplate', 'delete')}")
	public void archiveById(Long id) {
		prescriptionTemplateAction.archiveById(id);
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, PrescriptionTemplate e) {

		builder.append("\""
				+ (e.getName() != null ? e.getName().replace(",", "") : "")
				+ "\",");

		builder.append("\""
				+ (e.getDirectivesForPatient() != null ? e
						.getDirectivesForPatient().replace(",", "") : "")
				+ "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Name" + ",");

		builder.append("DirectivesForPatient" + ",");

		builder.append("\r\n");
	}
}
