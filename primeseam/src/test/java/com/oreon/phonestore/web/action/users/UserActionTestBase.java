package com.oreon.phonestore.web.action.users;

import org.junit.BeforeClass;
import org.witchcraft.seam.action.BaseAction;

import com.oreon.phonestore.users.User;

public class UserActionTestBase
		extends
			org.witchcraft.action.test.BaseTest<User> {

	UserAction userAction = new UserAction();

	@BeforeClass
	public void init() {
		super.init();
	}

	@Override
	public BaseAction<User> getAction() {
		return userAction;
	}

}
