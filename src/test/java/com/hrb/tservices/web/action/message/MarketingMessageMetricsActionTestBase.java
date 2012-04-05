package com.hrb.tservices.web.action.message;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.security.Identity;
import org.testng.annotations.Test;
import org.witchcraft.base.entity.*;
import org.hibernate.annotations.Filter;

import org.testng.annotations.BeforeClass;
import org.witchcraft.seam.action.BaseAction;
import com.hrb.tservices.domain.message.MarketingMessageMetrics;

public class MarketingMessageMetricsActionTestBase
		extends
			org.witchcraft.action.test.BaseTest<MarketingMessageMetrics> {

	MarketingMessageMetricsAction marketingMessageMetricsAction = new MarketingMessageMetricsAction();

	@BeforeClass
	public void init() {
		super.init();
	}

	@Override
	public BaseAction<MarketingMessageMetrics> getAction() {
		return marketingMessageMetricsAction;
	}

}