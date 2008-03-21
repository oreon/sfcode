
/**
 * This is generated code - to edit code or override methods use - AnswerChoice class
 * @author witchCraft Code Generator
 * WARNING  - DO NOT EDIT - CHANGES WILL BE OVERWRITTEN
 */

package com.oreon.kgauge.dao.impl;

import com.oreon.kgauge.domain.AnswerChoice;
import com.oreon.kgauge.dao.AnswerChoiceDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.witchcraft.model.support.dao.BaseDao;

@Repository
public class AnswerChoiceDaoImplBase extends BaseDao<AnswerChoice>
		implements
			AnswerChoiceDao {

	//// FINDERS ///// 

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseDao#createExampleCriteria(java.lang.Object)
	 */
	public void addAssoications(Criteria criteria, AnswerChoice exampleInstance) {

		if (exampleInstance.getQuestion() != null) {
			criteria = criteria.add(Restrictions.eq("question.id",
					exampleInstance.getQuestion().getId()));
		}

	}

}