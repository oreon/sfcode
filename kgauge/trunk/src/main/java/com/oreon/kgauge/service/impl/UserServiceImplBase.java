
/**
 * This is generated code - to edit code or override methods use - User class
 * @author witchCraft Code Generator
 * WARNING  - DO NOT EDIT - CHANGES WILL BE OVERWRITTEN
 */

package com.oreon.kgauge.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.witchcraft.model.support.Range;
import org.witchcraft.model.support.dao.GenericDAO;
import org.witchcraft.model.support.service.BaseServiceImpl;

import com.oreon.kgauge.dao.UserDao;
import com.oreon.kgauge.domain.User;
import com.oreon.kgauge.service.UserService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UserServiceImplBase extends BaseServiceImpl<User>
		implements
			UserService {

	private static final Logger log = Logger
			.getLogger(UserServiceImplBase.class);

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public GenericDAO<User> getDao() {
		return userDao;
	}

	//// Delegate all crud operations to the Dao ////

	public User save(User user) {
		Long id = user.getId();
		checkUniqueConstraints(user);
		userDao.save(user);

		return user;
	}

	/** Before saving a record we need to ensure that no unique constraints
	 * will be violated. 
	 * @param customer
	 */
	private void checkUniqueConstraints(User user) {
		User existingUser = null;

		existingUser = userDao.findByUserName(user.getUserName());
		ensureUnique(user, existingUser, "Entity.exists.withUserName");

	}

	public void delete(User user) {
		userDao.delete(user);
	}

	public User load(Long id) {
		return userDao.load(id);
	}

	public List<User> loadAll() {
		return userDao.loadAll();
	}

	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	public List<User> searchByExample(User user) {
		return userDao.searchByExample(user);
	}

	public List<User> searchByExample(User user, List<Range> rangeObjects) {
		return userDao.searchByExample(user, rangeObjects);
	}

	/*
	public List query(String queryString, Object... params) {
		return basicDAO.query(queryString, params);
	}*/

}