package com.nas.recovery.web.action.users;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.security.Identity;
import org.testng.annotations.Test;
import org.witchcraft.base.entity.*;
import org.hibernate.annotations.Filter;

import org.testng.annotations.BeforeClass;
import org.witchcraft.seam.action.BaseAction;
import com.nas.recovery.domain.users.Role;

public class RoleActionTestBase
		extends
			org.witchcraft.action.test.BaseTest<Role> {

	RoleAction roleAction = new RoleAction();

	@BeforeClass
	public void init() {
		super.init();
	}

	@Override
	public BaseAction<Role> getAction() {
		return roleAction;
	}

	@Test
	public void testfindByName() {
		//roleAction.findByName(name);
	}

}
