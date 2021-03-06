
/**
 * This is generated code - to edit code or override methods use - ExamInstance class
 * @author witchCraft Code Generator
 * WARNING  - DO NOT EDIT - CHANGES WILL BE OVERWRITTEN
 */

package com.oreon.kgauge.service.impl;

import com.oreon.kgauge.domain.ExamInstance;
import com.oreon.kgauge.service.ExamInstanceService;
import com.oreon.kgauge.dao.ExamInstanceDao;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import org.apache.log4j.Logger;

import org.witchcraft.model.support.dao.GenericDAO;
import org.witchcraft.model.support.errorhandling.BusinessException;
import org.witchcraft.model.support.service.BaseServiceImpl;

import javax.jws.WebService;

import org.witchcraft.model.support.Range;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ExamInstanceServiceImplBase extends BaseServiceImpl<ExamInstance>
		implements
			ExamInstanceService {

	private static final Logger log = Logger
			.getLogger(ExamInstanceServiceImplBase.class);

	private ExamInstanceDao examInstanceDao;

	public void setExamInstanceDao(ExamInstanceDao examInstanceDao) {
		this.examInstanceDao = examInstanceDao;
	}

	@Override
	public GenericDAO<ExamInstance> getDao() {
		return examInstanceDao;
	}

	//// Delegate all crud operations to the Dao ////

	public ExamInstance save(ExamInstance examInstance) {
		Long id = examInstance.getId();

		examInstanceDao.save(examInstance);

		return examInstance;
	}

	public void delete(ExamInstance examInstance) {
		examInstanceDao.delete(examInstance);
	}

	public ExamInstance load(Long id) {
		return examInstanceDao.load(id);
	}

	public List<ExamInstance> loadAll() {
		return examInstanceDao.loadAll();
	}

	public List<ExamInstance> searchByExample(ExamInstance examInstance) {
		return examInstanceDao.searchByExample(examInstance);
	}

	public List<ExamInstance> searchByExample(ExamInstance examInstance,
			List<Range> rangeObjects) {
		return examInstanceDao.searchByExample(examInstance, rangeObjects);
	}

	/** This method should be overridden by classes that want to filter the load all behavior e.g.
	 * showing 
	 * @return
	 */
	public ExamInstance getFilterRecord() {
		return null;
	}

	public Integer findCandidateScore(ExamInstance examInstance) {
		return examInstanceDao.findCandidateScore(examInstance);
	}

	public Integer findMaxScore(ExamInstance examInstance) {
		return examInstanceDao.findMaxScore(examInstance);
	}

}
