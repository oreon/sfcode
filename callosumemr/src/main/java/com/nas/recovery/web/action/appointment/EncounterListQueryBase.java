package com.nas.recovery.web.action.appointment;

import com.oreon.callosum.appointment.Encounter;

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

import com.oreon.callosum.appointment.Encounter;

/**
 * D
 * @author WitchcraftMDA Seam Cartridge
 *
 */
public abstract class EncounterListQueryBase extends BaseQuery<Encounter, Long> {

	private static final String EJBQL = "select encounter from Encounter encounter";

	protected Encounter encounter = new Encounter();

	public Encounter getEncounter() {
		return encounter;
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	public Class<Encounter> getEntityClass() {
		return Encounter.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"encounter.id = #{encounterList.encounter.id}",

			"encounter.patient.id = #{encounterList.encounter.patient.id}",

			"encounter.physician.id = #{encounterList.encounter.physician.id}",

			"lower(encounter.notes) like concat(lower(#{encounterList.encounter.notes}),'%')",

			"encounter.dateCreated <= #{encounterList.dateCreatedRange.end}",
			"encounter.dateCreated >= #{encounterList.dateCreatedRange.begin}",};

	@Observer("archivedEncounter")
	public void onArchive() {
		refresh();
	}

}
