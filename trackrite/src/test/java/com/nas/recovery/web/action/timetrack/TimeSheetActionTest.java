package com.nas.recovery.web.action.timetrack;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.security.Identity;
import org.testng.annotations.Test;
import org.witchcraft.base.entity.*;
import org.hibernate.annotations.Filter;

import org.testng.annotations.BeforeClass;
import org.witchcraft.seam.action.BaseAction;
import org.wc.trackrite.timetrack.TimeSheet;

public class TimeSheetActionTest extends TimeSheetActionTestBase {
	
	@Test
	public void testQry(){
		TimeSheet ts = getEntityManager().find(TimeSheet.class, 1L);
		//if(ts != null)
		//	System.out.println(ts.getTotal());
		
	}

}
