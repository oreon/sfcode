package com.nas.recovery.web.action.appointment;

import com.oreon.callosum.appointment.PrescribedTest;

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

import com.oreon.callosum.appointment.PrescribedTest;

/**
 * D
 * @author WitchcraftMDA Seam Cartridge
 *
 */
public abstract class PrescribedTestListQueryBase
		extends
			BaseQuery<PrescribedTest, Long> {

	private static final String EJBQL = "select prescribedTest from PrescribedTest prescribedTest";

	protected PrescribedTest prescribedTest = new PrescribedTest();

	public PrescribedTest getPrescribedTest() {
		return prescribedTest;
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	public Class<PrescribedTest> getEntityClass() {
		return PrescribedTest.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"prescribedTest.id = #{prescribedTestList.prescribedTest.id}",

			"lower(prescribedTest.remarks) like concat(lower(#{prescribedTestList.prescribedTest.remarks}),'%')",

			"prescribedTest.dxTest.id = #{prescribedTestList.prescribedTest.dxTest.id}",

			"prescribedTest.encounter.id = #{prescribedTestList.prescribedTest.encounter.id}",

			"prescribedTest.dateCreated <= #{prescribedTestList.dateCreatedRange.end}",
			"prescribedTest.dateCreated >= #{prescribedTestList.dateCreatedRange.begin}",};

	public List<PrescribedTest> getPrescribedTestsByEncounter(
			com.oreon.callosum.appointment.Encounter encounter) {
		//setMaxResults(10000);
		prescribedTest.setEncounter(encounter);
		return getResultList();
	}

	@Observer("archivedPrescribedTest")
	public void onArchive() {
		refresh();
	}

}
