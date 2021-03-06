package com.oreon.cerebrum.web.action.appointment;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.security.Identity;
import org.testng.annotations.Test;
import org.witchcraft.base.entity.*;
import org.hibernate.annotations.Filter;

import org.testng.annotations.BeforeClass;
import org.witchcraft.seam.action.BaseAction;
import com.oreon.cerebrum.appointment.Appointment;

public class AppointmentActionTestBase
		extends
			org.witchcraft.action.test.BaseTest<Appointment> {

	AppointmentAction appointmentAction = new AppointmentAction();

	@BeforeClass
	public void init() {
		super.init();
	}

	@Override
	public BaseAction<Appointment> getAction() {
		return appointmentAction;
	}

}
