package com.oreon.cerebrum.web.action.facility;

import com.oreon.cerebrum.facility.Facility;

import org.witchcraft.seam.action.BaseAction;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;

import org.witchcraft.seam.action.BaseQuery;

import org.witchcraft.base.entity.Range;

import org.primefaces.model.SortOrder;
import org.witchcraft.seam.action.EntityLazyDataModel;
import org.primefaces.model.LazyDataModel;
import java.util.Map;

import org.jboss.seam.annotations.Observer;

import java.math.BigDecimal;
import javax.faces.model.DataModel;

import org.jboss.seam.annotations.security.Restrict;

import org.jboss.seam.annotations.In;
import org.jboss.seam.Component;

import com.oreon.cerebrum.facility.Facility;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class FacilityListQueryBase extends BaseQuery<Facility, Long> {

	private static final String EJBQL = "select facility from Facility facility";

	protected Facility facility = new Facility();

	@In(create = true)
	FacilityAction facilityAction;

	public FacilityListQueryBase() {
		super();
		setOrderColumn("id");
		setOrderDirection("desc");
	}

	public Facility getFacility() {
		return facility;
	}

	@Override
	public Facility getInstance() {
		return getFacility();
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	//@Restrict("#{s:hasPermission('facility', 'view')}")
	public List<Facility> getResultList() {
		return super.getResultList();
	}

	@Override
	public Class<Facility> getEntityClass() {
		return Facility.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"facility.id = #{facilityList.facility.id}",

			"facility.archived = #{facilityList.facility.archived}",

			"lower(facility.name) like concat(lower(#{facilityList.facility.name}),'%')",

			"facility.dateCreated <= #{facilityList.dateCreatedRange.end}",
			"facility.dateCreated >= #{facilityList.dateCreatedRange.begin}",};

	@Observer("archivedFacility")
	public void onArchive() {
		refresh();
	}

	//@Restrict("#{s:hasPermission('facility', 'delete')}")
	public void archiveById(Long id) {
		facilityAction.archiveById(id);
		refresh();
	}

}