package com.oreon.phonestore.web.action.domain;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.security.Identity;
import org.testng.annotations.Test;
import org.witchcraft.base.entity.*;
import org.hibernate.annotations.Filter;

import org.testng.annotations.BeforeClass;
import org.witchcraft.seam.action.BaseAction;
import com.oreon.phonestore.domain.Department;

@SuppressWarnings("unused")
public class DepartmentActionTest extends DepartmentActionTestBase {
	
	@org.junit.Test
	public void testDepartment(){
		System.out.println("being tested");
	}

}
