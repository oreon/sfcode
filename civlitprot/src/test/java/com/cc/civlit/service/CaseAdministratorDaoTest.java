package com.cc.civlit.service;

import com.cc.civlit.domain.CaseAdministrator;
import org.springframework.test.jpa.AbstractJpaTests;
import java.util.List;

import org.witchcraft.model.support.testing.TestDataFactory;
import org.witchcraft.model.support.springbeanhelpers.BeanHelper;

import java.text.SimpleDateFormat;

import javax.persistence.PersistenceException;
import org.hibernate.PropertyValueException;
import java.util.Date;

public class CaseAdministratorDaoTest extends AbstractJpaTests {

	protected CaseAdministrator caseAdministratorInstance = new CaseAdministrator();

	protected CaseAdministratorService caseAdministratorService;

	protected boolean bTest = true;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy.MM.dd HH:mm:ss z");

	public void setCaseAdministratorService(
			CaseAdministratorService caseAdministratorService) {
		this.caseAdministratorService = caseAdministratorService;
	}

	protected TestDataFactory caseAdministratorTestDataFactory = (TestDataFactory) BeanHelper
			.getBean("caseAdministratorTestDataFactory");

	@Override
	protected String[] getConfigLocations() {
		return new String[]{"classpath:/applicationContext.xml",
				"classpath:/testDataFactories.xml"};
	}

	@Override
	protected void runTest() throws Throwable {
		if (!bTest)
			return;
		super.runTest();
	}

	/**
	 * Do the setup before the test in this method
	 **/
	protected void onSetUpInTransaction() throws Exception {
		try {

			caseAdministratorInstance.setFirstName("beta");
			caseAdministratorInstance.setLastName("Eric");
			caseAdministratorInstance.setDateOfBirth(dateFormat
					.parse("2008.08.06 16:26:23 EDT"));
			caseAdministratorInstance.setEmail("epsilon67278");

			caseAdministratorService.save(caseAdministratorInstance);
		} catch (PersistenceException pe) {
			//if this instance can't be created due to back references e.g an orderItem needs an Order - 
			// - we will simply skip generated tests.
			if (pe.getCause() instanceof PropertyValueException
					&& pe.getMessage().contains("Backref")) {
				bTest = false;
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	//test saving a new record and updating an existing record;
	public void testSave() {

		try {
			CaseAdministrator caseAdministrator = new CaseAdministrator();

			try {

				caseAdministrator.setFirstName("Wilson");
				caseAdministrator.setLastName("Lavendar");
				caseAdministrator.setDateOfBirth(dateFormat
						.parse("2008.08.02 16:18:37 EDT"));
				caseAdministrator.setEmail("gamma1354");

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			caseAdministratorService.save(caseAdministrator);
			assertNotNull(caseAdministrator.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testEdit() {

		try {
			//test saving a new record and updating an existing record;
			CaseAdministrator caseAdministrator = (CaseAdministrator) caseAdministratorTestDataFactory
					.loadOneRecord();

			caseAdministrator.setFirstName("Eric");
			caseAdministrator.setLastName("Mark");
			caseAdministrator.setDateOfBirth(dateFormat
					.parse("2008.07.11 17:43:03 EDT"));
			caseAdministrator.setEmail("Lavendar48873");

			caseAdministratorService.save(caseAdministrator);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testCount() {
		assertTrue(caseAdministratorService.getCount() > 0);
	}

	//count the number of records - add one delete it - check count is same after delete
	public void testDelete() {

		try {
			long count, newCount, diff = 0;
			count = caseAdministratorService.getCount();
			CaseAdministrator caseAdministrator = (CaseAdministrator) caseAdministratorTestDataFactory
					.loadOneRecord();
			caseAdministratorService.delete(caseAdministrator);
			newCount = caseAdministratorService.getCount();
			diff = newCount - count;
			assertEquals(diff, 1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testLoad() {

		try {
			CaseAdministrator caseAdministrator = caseAdministratorService
					.load(caseAdministratorInstance.getId());
			assertNotNull(caseAdministrator.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testFindByEmail() {
		if (!bTest)
			return;

		assertNotNull("Couldn't find a CaseAdministrator with email ",
				caseAdministratorService.findByEmail(caseAdministratorInstance
						.getEmail()));
		//assertNull("Found a CaseAdministrator with email YYY", caseAdministratorService.findByEmail("YYY"));			

	}

	public void testSearchByExample() {
		try {
			List<CaseAdministrator> caseAdministrators = caseAdministratorService
					.searchByExample(caseAdministratorInstance);
			assertTrue(!caseAdministrators.isEmpty());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/////////////////// Queries //////////////////////////////////

}
