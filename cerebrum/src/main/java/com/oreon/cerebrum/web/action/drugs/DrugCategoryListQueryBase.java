package com.oreon.cerebrum.web.action.drugs;

import com.oreon.cerebrum.drugs.DrugCategory;

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

import com.oreon.cerebrum.drugs.DrugCategory;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class DrugCategoryListQueryBase
		extends
			BaseQuery<DrugCategory, Long> {

	private static final String EJBQL = "select drugCategory from DrugCategory drugCategory";

	protected DrugCategory drugCategory = new DrugCategory();

	@In(create = true)
	DrugCategoryAction drugCategoryAction;

	public DrugCategoryListQueryBase() {
		super();
		setOrderColumn("id");
		setOrderDirection("desc");
	}

	public DrugCategory getDrugCategory() {
		return drugCategory;
	}

	@Override
	public DrugCategory getInstance() {
		return getDrugCategory();
	}

	private com.oreon.cerebrum.drugs.Drug drugsToSearch;

	public void setDrugsToSearch(com.oreon.cerebrum.drugs.Drug drugToSearch) {
		this.drugsToSearch = drugToSearch;
	}

	public com.oreon.cerebrum.drugs.Drug getDrugsToSearch() {
		return drugsToSearch;
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	//@Restrict("#{s:hasPermission('drugCategory', 'view')}")
	public List<DrugCategory> getResultList() {
		return super.getResultList();
	}

	@Override
	public Class<DrugCategory> getEntityClass() {
		return DrugCategory.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"drugCategory.id = #{drugCategoryList.drugCategory.id}",

			"drugCategory.archived = #{drugCategoryList.drugCategory.archived}",

			"lower(drugCategory.name) like concat(lower(#{drugCategoryList.drugCategory.name}),'%')",

			"#{drugCategoryList.drugsToSearch} in elements(drugCategory.drugs)",

			"drugCategory.dateCreated <= #{drugCategoryList.dateCreatedRange.end}",
			"drugCategory.dateCreated >= #{drugCategoryList.dateCreatedRange.begin}",};

	@Observer("archivedDrugCategory")
	public void onArchive() {
		refresh();
	}

	//@Restrict("#{s:hasPermission('drugCategory', 'delete')}")
	public void archiveById(Long id) {
		drugCategoryAction.archiveById(id);
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, DrugCategory e) {

		builder.append("\""
				+ (e.getName() != null ? e.getName().replace(",", "") : "")
				+ "\",");

		builder.append("\"" + (e.getDrugs() != null ? e.getDrugs() : "")
				+ "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Name" + ",");

		builder.append("Drugs" + ",");

		builder.append("\r\n");
	}
}
