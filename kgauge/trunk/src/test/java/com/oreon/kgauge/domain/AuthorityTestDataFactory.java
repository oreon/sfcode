package com.oreon.kgauge.domain;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import org.witchcraft.model.support.springbeanhelpers.BeanHelper;
import org.witchcraft.model.support.testing.AbstractTestDataFactory;

import org.witchcraft.model.support.testing.TestDataFactory;
import org.witchcraft.model.support.errorhandling.BusinessException;
import org.witchcraft.model.randomgen.RandomValueGeneratorFactory;

import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import com.oreon.kgauge.service.AuthorityService;

@Transactional
public class AuthorityTestDataFactory
		extends
			AbstractTestDataFactory<Authority> {

	private List<Authority> authoritys = new ArrayList<Authority>();

	private static final Logger logger = Logger
			.getLogger(AuthorityTestDataFactory.class);

	private static int RECORDS_TO_CREATE = 30;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy.MM.dd HH:mm:ss z");

	AuthorityService authorityService;

	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void register(Authority authority) {
		authoritys.add(authority);
	}

	public Authority createAuthorityOne() {
		Authority authority = new Authority();

		try {

			authority.setName("alpha");

			TestDataFactory userTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("userTestDataFactory");

			authority
					.setUser((com.oreon.kgauge.domain.User) userTestDataFactory
							.loadOneRecord());

			register(authority);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return authority;
	}

	public Authority createAuthorityTwo() {
		Authority authority = new Authority();

		try {

			authority.setName("Lavendar");

			TestDataFactory userTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("userTestDataFactory");

			authority
					.setUser((com.oreon.kgauge.domain.User) userTestDataFactory
							.loadOneRecord());

			register(authority);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return authority;
	}

	public Authority createAuthorityThree() {
		Authority authority = new Authority();

		try {

			authority.setName("delta");

			TestDataFactory userTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("userTestDataFactory");

			authority
					.setUser((com.oreon.kgauge.domain.User) userTestDataFactory
							.loadOneRecord());

			register(authority);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return authority;
	}

	public Authority createAuthorityFour() {
		Authority authority = new Authority();

		try {

			authority.setName("Wilson");

			TestDataFactory userTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("userTestDataFactory");

			authority
					.setUser((com.oreon.kgauge.domain.User) userTestDataFactory
							.loadOneRecord());

			register(authority);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return authority;
	}

	public Authority createAuthorityFive() {
		Authority authority = new Authority();

		try {

			authority.setName("alpha");

			TestDataFactory userTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("userTestDataFactory");

			authority
					.setUser((com.oreon.kgauge.domain.User) userTestDataFactory
							.loadOneRecord());

			register(authority);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return authority;
	}

	public Authority loadOneRecord() {
		List<Authority> authoritys = authorityService.loadAll();

		if (authoritys.isEmpty()) {
			persistAll();
			authoritys = authorityService.loadAll();
		}

		return authoritys.get(new Random().nextInt(authoritys.size()));
	}

	public List<Authority> getAllAsList() {

		if (authoritys.isEmpty()) {

			createAuthorityOne();
			createAuthorityTwo();
			createAuthorityThree();
			createAuthorityFour();
			createAuthorityFive();

		}

		return authoritys;
	}

	public void persistAll() {
		if (!isPersistable() || alreadyPersisted)
			return;

		getAllAsList();

		for (Authority authority : authoritys) {
			try {
				authorityService.save(authority);
			} catch (BusinessException be) {
				logger.warn(" Authority " + authority.getDisplayName()
						+ "couldn't be saved " + be.getMessage());
			}
		}

		alreadyPersisted = true;
	}

	/** Execute this method to manually generate additional orders
	 * @param args
	 */
	public static void main(String args[]) {

		TestDataFactory placedOrderTestDataFactory = (TestDataFactory) BeanHelper
				.getBean("placedOrderTestDataFactory");

		placedOrderTestDataFactory.createAndSaveRecords(RECORDS_TO_CREATE);
	}

	public void createAndSaveRecords(int recordsTocreate) {
		for (int i = 0; i < recordsTocreate; i++) {
			Authority authority = createRandomAuthority();
			authorityService.save(authority);
		}
	}

	public Authority createRandomAuthority() {
		Authority authority = new Authority();

		authority.setName((String) RandomValueGeneratorFactory
				.createInstance("String"));

		TestDataFactory userTestDataFactory = (TestDataFactory) BeanHelper
				.getBean("userTestDataFactory");

		authority.setUser((com.oreon.kgauge.domain.User) userTestDataFactory
				.loadOneRecord());

		return authority;
	}

}