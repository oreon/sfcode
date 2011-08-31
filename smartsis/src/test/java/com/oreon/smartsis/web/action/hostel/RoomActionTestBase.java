package com.oreon.smartsis.web.action.hostel;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.security.Identity;
import org.testng.annotations.Test;
import org.witchcraft.base.entity.*;
import org.hibernate.annotations.Filter;

import org.testng.annotations.BeforeClass;
import org.witchcraft.seam.action.BaseAction;
import com.oreon.smartsis.hostel.Room;

public class RoomActionTestBase
		extends
			org.witchcraft.action.test.BaseTest<Room> {

	RoomAction roomAction = new RoomAction();

	@BeforeClass
	public void init() {
		super.init();
	}

	@Override
	public BaseAction<Room> getAction() {
		return roomAction;
	}

}