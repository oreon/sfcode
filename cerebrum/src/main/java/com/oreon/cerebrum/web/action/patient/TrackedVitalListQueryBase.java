package com.oreon.cerebrum.web.action.patient;

import com.oreon.cerebrum.patient.TrackedVital;

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

import com.oreon.cerebrum.patient.TrackedVital;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class TrackedVitalListQueryBase
		extends
			BaseQuery<TrackedVital, Long> {

	private static final String EJBQL = "select trackedVital from TrackedVital trackedVital";

	protected TrackedVital trackedVital = new TrackedVital();

	@In(create = true)
	TrackedVitalAction trackedVitalAction;

	public TrackedVitalListQueryBase() {
		super();
		setOrderColumn("id");
		setOrderDirection("desc");
	}

	public TrackedVital getTrackedVital() {
		return trackedVital;
	}

	@Override
	public TrackedVital getInstance() {
		return getTrackedVital();
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	//@Restrict("#{s:hasPermission('trackedVital', 'view')}")
	public List<TrackedVital> getResultList() {
		return super.getResultList();
	}

	@Override
	public Class<TrackedVital> getEntityClass() {
		return TrackedVital.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private Range<Double> minValRange = new Range<Double>();

	public Range<Double> getMinValRange() {
		return minValRange;
	}
	public void setMinVal(Range<Double> minValRange) {
		this.minValRange = minValRange;
	}

	private Range<Double> maxValRange = new Range<Double>();

	public Range<Double> getMaxValRange() {
		return maxValRange;
	}
	public void setMaxVal(Range<Double> maxValRange) {
		this.maxValRange = maxValRange;
	}

	private static final String[] RESTRICTIONS = {
			"trackedVital.id = #{trackedVitalList.trackedVital.id}",

			"trackedVital.archived = #{trackedVitalList.trackedVital.archived}",

			"lower(trackedVital.name) like concat(lower(#{trackedVitalList.trackedVital.name}),'%')",

			"trackedVital.minVal >= #{trackedVitalList.minValRange.begin}",
			"trackedVital.minVal <= #{trackedVitalList.minValRange.end}",

			"trackedVital.maxVal >= #{trackedVitalList.maxValRange.begin}",
			"trackedVital.maxVal <= #{trackedVitalList.maxValRange.end}",

			"trackedVital.dateCreated <= #{trackedVitalList.dateCreatedRange.end}",
			"trackedVital.dateCreated >= #{trackedVitalList.dateCreatedRange.begin}",};

	@Observer("archivedTrackedVital")
	public void onArchive() {
		refresh();
	}

	//@Restrict("#{s:hasPermission('trackedVital', 'delete')}")
	public void archiveById(Long id) {
		trackedVitalAction.archiveById(id);
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, TrackedVital e) {

		builder.append("\""
				+ (e.getName() != null ? e.getName().replace(",", "") : "")
				+ "\",");

		builder.append("\"" + (e.getMinVal() != null ? e.getMinVal() : "")
				+ "\",");

		builder.append("\"" + (e.getMaxVal() != null ? e.getMaxVal() : "")
				+ "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Name" + ",");

		builder.append("MinVal" + ",");

		builder.append("MaxVal" + ",");

		builder.append("\r\n");
	}
}
