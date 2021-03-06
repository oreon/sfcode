package com.oreon.cerebrum.web.action.ddx;

import com.oreon.cerebrum.ddx.Disease;

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

import com.oreon.cerebrum.ddx.Disease;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class DiseaseListQueryBase extends BaseQuery<Disease, Long> {

	private static final String EJBQL = "select disease from Disease disease";

	protected Disease disease = new Disease();

	@In(create = true)
	DiseaseAction diseaseAction;

	public DiseaseListQueryBase() {
		super();
		setOrderColumn("id");
		setOrderDirection("desc");
	}

	public Disease getDisease() {
		return disease;
	}

	@Override
	public Disease getInstance() {
		return getDisease();
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	//@Restrict("#{s:hasPermission('disease', 'view')}")
	public List<Disease> getResultList() {
		return super.getResultList();
	}

	@Override
	public Class<Disease> getEntityClass() {
		return Disease.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"disease.id = #{diseaseList.disease.id}",

			"disease.archived = #{diseaseList.disease.archived}",

			"disease.gender = #{diseaseList.disease.gender}",

			"lower(disease.name) like concat(lower(#{diseaseList.disease.name}),'%')",

			"disease.relatedDisease.id = #{diseaseList.disease.relatedDisease.id}",

			"disease.conditionCategory.id = #{diseaseList.disease.conditionCategory.id}",

			"disease.dateCreated <= #{diseaseList.dateCreatedRange.end}",
			"disease.dateCreated >= #{diseaseList.dateCreatedRange.begin}",};

	public List<Disease> getDifferentialDiagnosesByRelatedDisease(
			com.oreon.cerebrum.ddx.Disease disease) {
		disease.setRelatedDisease(disease);
		return getResultList();
	}

	@Observer("archivedDisease")
	public void onArchive() {
		refresh();
	}

	public void setRelatedDiseaseId(Long id) {
		if (disease.getRelatedDisease() == null) {
			disease.setRelatedDisease(new com.oreon.cerebrum.ddx.Disease());
		}
		disease.getRelatedDisease().setId(id);
	}

	public Long getRelatedDiseaseId() {
		return disease.getRelatedDisease() == null ? null : disease
				.getRelatedDisease().getId();
	}

	public void setConditionCategoryId(Long id) {
		if (disease.getConditionCategory() == null) {
			disease
					.setConditionCategory(new com.oreon.cerebrum.ddx.ConditionCategory());
		}
		disease.getConditionCategory().setId(id);
	}

	public Long getConditionCategoryId() {
		return disease.getConditionCategory() == null ? null : disease
				.getConditionCategory().getId();
	}

	//@Restrict("#{s:hasPermission('disease', 'delete')}")
	public void archiveById(Long id) {
		diseaseAction.archiveById(id);
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, Disease e) {

		builder.append("\"" + (e.getGender() != null ? e.getGender() : "")
				+ "\",");

		builder.append("\""
				+ (e.getName() != null ? e.getName().replace(",", "") : "")
				+ "\",");

		builder.append("\""
				+ (e.getRelatedDisease() != null ? e.getRelatedDisease()
						.getDisplayName().replace(",", "") : "") + "\",");

		builder.append("\""
				+ (e.getConditionCategory() != null ? e.getConditionCategory()
						.getDisplayName().replace(",", "") : "") + "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Gender" + ",");

		builder.append("Name" + ",");

		builder.append("RelatedDisease" + ",");

		builder.append("ConditionCategory" + ",");

		builder.append("\r\n");
	}
}
