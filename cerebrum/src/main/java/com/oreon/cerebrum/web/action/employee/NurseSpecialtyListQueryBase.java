package com.oreon.cerebrum.web.action.employee;

import com.oreon.cerebrum.employee.NurseSpecialty;

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

import com.oreon.cerebrum.employee.NurseSpecialty;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class NurseSpecialtyListQueryBase
		extends
			BaseQuery<NurseSpecialty, Long> {

	private static final String EJBQL = "select nurseSpecialty from NurseSpecialty nurseSpecialty";

	protected NurseSpecialty nurseSpecialty = new NurseSpecialty();

	@In(create = true)
	NurseSpecialtyAction nurseSpecialtyAction;

	public NurseSpecialtyListQueryBase() {
		super();
		setOrderColumn("id");
		setOrderDirection("desc");
	}

	public NurseSpecialty getNurseSpecialty() {
		return nurseSpecialty;
	}

	@Override
	public NurseSpecialty getInstance() {
		return getNurseSpecialty();
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	//@Restrict("#{s:hasPermission('nurseSpecialty', 'view')}")
	public List<NurseSpecialty> getResultList() {
		return super.getResultList();
	}

	@Override
	public Class<NurseSpecialty> getEntityClass() {
		return NurseSpecialty.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"nurseSpecialty.id = #{nurseSpecialtyList.nurseSpecialty.id}",

			"nurseSpecialty.archived = #{nurseSpecialtyList.nurseSpecialty.archived}",

			"lower(nurseSpecialty.name) like concat(lower(#{nurseSpecialtyList.nurseSpecialty.name}),'%')",

			"nurseSpecialty.dateCreated <= #{nurseSpecialtyList.dateCreatedRange.end}",
			"nurseSpecialty.dateCreated >= #{nurseSpecialtyList.dateCreatedRange.begin}",};

	@Observer("archivedNurseSpecialty")
	public void onArchive() {
		refresh();
	}

	//@Restrict("#{s:hasPermission('nurseSpecialty', 'delete')}")
	public void archiveById(Long id) {
		nurseSpecialtyAction.archiveById(id);
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, NurseSpecialty e) {

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
