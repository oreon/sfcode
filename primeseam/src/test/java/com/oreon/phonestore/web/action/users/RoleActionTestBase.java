package com.oreon.phonestore.web.action.users;

import org.junit.BeforeClass;
import org.witchcraft.seam.action.BaseAction;

import com.oreon.phonestore.users.Role;

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

}
