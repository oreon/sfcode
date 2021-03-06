package com.oreon.cerebrum.web.action.patient;

import com.oreon.cerebrum.patient.Allergen;

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

import com.oreon.cerebrum.patient.Allergen;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class AllergenListQueryBase extends BaseQuery<Allergen, Long> {

	private static final String EJBQL = "select allergen from Allergen allergen";

	protected Allergen allergen = new Allergen();

	@In(create = true)
	AllergenAction allergenAction;

	public AllergenListQueryBase() {
		super();
		setOrderColumn("id");
		setOrderDirection("desc");
	}

	public Allergen getAllergen() {
		return allergen;
	}

	@Override
	public Allergen getInstance() {
		return getAllergen();
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	//@Restrict("#{s:hasPermission('allergen', 'view')}")
	public List<Allergen> getResultList() {
		return super.getResultList();
	}

	@Override
	public Class<Allergen> getEntityClass() {
		return Allergen.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"allergen.id = #{allergenList.allergen.id}",

			"allergen.archived = #{allergenList.allergen.archived}",

			"lower(allergen.name) like concat(lower(#{allergenList.allergen.name}),'%')",

			"allergen.dateCreated <= #{allergenList.dateCreatedRange.end}",
			"allergen.dateCreated >= #{allergenList.dateCreatedRange.begin}",};

	@Observer("archivedAllergen")
	public void onArchive() {
		refresh();
	}

	//@Restrict("#{s:hasPermission('allergen', 'delete')}")
	public void archiveById(Long id) {
		allergenAction.archiveById(id);
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, Allergen e) {

		builder.append("\""
				+ (e.getName() != null ? e.getName().replace(",", "") : "")
				+ "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Name" + ",");

		builder.append("\r\n");
	}
}
