package com.nas.recovery.web.action.patient;

import com.oreon.callosum.patient.Allergy;

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

import com.oreon.callosum.patient.Allergy;

/**
 * D
 * @author WitchcraftMDA Seam Cartridge
 *
 */
public abstract class AllergyListQueryBase extends BaseQuery<Allergy, Long> {

	private static final String EJBQL = "select allergy from Allergy allergy";

	protected Allergy allergy = new Allergy();

	public Allergy getAllergy() {
		return allergy;
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	public Class<Allergy> getEntityClass() {
		return Allergy.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"allergy.id = #{allergyList.allergy.id}",

			"allergy.patient.id = #{allergyList.allergy.patient.id}",

			"allergy.allergen.id = #{allergyList.allergy.allergen.id}",

			"allergy.severity = #{allergyList.allergy.severity}",

			"allergy.dateCreated <= #{allergyList.dateCreatedRange.end}",
			"allergy.dateCreated >= #{allergyList.dateCreatedRange.begin}",};

	public List<Allergy> getAllergysByPatient(
			com.oreon.callosum.patient.Patient patient) {
		//setMaxResults(10000);
		allergy.setPatient(patient);
		return getResultList();
	}

	@Observer("archivedAllergy")
	public void onArchive() {
		refresh();
	}

}
