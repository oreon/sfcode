package com.wc.jshopper.domain.action;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.security.Identity;
import org.testng.annotations.Test;
import org.witchcraft.base.entity.*;
import org.hibernate.annotations.Filter;

import org.testng.annotations.BeforeClass;
import org.witchcraft.seam.action.BaseAction;
import com.wc.jshopper.domain.OrderItem;

public class OrderItemTestBase
		extends
			org.witchcraft.action.test.BaseTest<OrderItem> {

	OrderItemAction orderItemAction = new OrderItemAction();

	@BeforeClass
	public void init() {
		super.init();
	}

	@Override
	public BaseAction<OrderItem> getAction() {
		return orderItemAction;
	}

}