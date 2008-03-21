
/**
 * This is generated code - to edit code or override methods use - Exam class
 * @author witchCraft Code Generator
 * WARNING  - DO NOT EDIT - CHANGES WILL BE OVERWRITTEN
 */

package com.oreon.kgauge.dao.impl;

import com.oreon.kgauge.domain.Exam;
import com.oreon.kgauge.dao.ExamDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.witchcraft.model.support.dao.BaseDao;

@Repository
public class ExamDaoImplBase extends BaseDao<Exam> implements ExamDao {

	//// FINDERS ///// 

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseDao#createExampleCriteria(java.lang.Object)
	 */
	public void addAssoications(Criteria criteria, Exam exampleInstance) {

		if (exampleInstance.getCategory() != null) {
			criteria = criteria.add(Restrictions.eq("category.id",
					exampleInstance.getCategory().getId()));
		}

		if (exampleInstance.getExamCreator() != null) {
			criteria = criteria.add(Restrictions.eq("examCreator.id",
					exampleInstance.getExamCreator().getId()));
		}

	}

}