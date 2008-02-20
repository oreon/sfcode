package com.oreon.kgauge.domain;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import org.witchcraft.model.support.springbeanhelpers.BeanHelper;
import org.witchcraft.model.support.testing.AbstractTestDataFactory;

import org.witchcraft.model.support.testing.TestDataFactory;

import org.witchcraft.model.randomgen.RandomValueGeneratorFactory;

import org.springframework.transaction.annotation.Transactional;

import com.oreon.kgauge.service.AuthorityService;

@Transactional
public class AuthorityTestDataFactory
		extends
			AbstractTestDataFactory<Authority> {

	private List<Authority> authoritys = new ArrayList<Authority>();

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

			authority.setName("epsilon62013");

			register(authority);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return authority;
	}

	public Authority createAuthorityTwo() {
		Authority authority = new Authority();

		try {

			authority.setName("pi58796");

			register(authority);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return authority;
	}

	public Authority createAuthorityThree() {
		Authority authority = new Authority();

		try {

			authority.setName("beta45706");

			register(authority);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return authority;
	}

	public Authority createAuthorityFour() {
		Authority authority = new Authority();

		try {

			authority.setName("epsilon58801");

			register(authority);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return authority;
	}

	public Authority createAuthorityFive() {
		Authority authority = new Authority();

		try {

			authority.setName("alpha45584");

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
			authorityService.save(authority);
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

		return authority;
	}

}
