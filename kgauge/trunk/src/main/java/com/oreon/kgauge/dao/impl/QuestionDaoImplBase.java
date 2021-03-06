
/**
 * This is generated code - to edit code or override methods use - Question class
 * @author witchCraft Code Generator
 * WARNING  - DO NOT EDIT - CHANGES WILL BE OVERWRITTEN
 */

package com.oreon.kgauge.dao.impl;

import com.oreon.kgauge.domain.Question;
import com.oreon.kgauge.dao.QuestionDao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Indexed;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.witchcraft.model.support.dao.BaseDao;

@Repository
public class QuestionDaoImplBase extends BaseDao<Question>
		implements
			QuestionDao {

	//// FINDERS ///// 
	private static final Logger logger = Logger
			.getLogger(QuestionDaoImplBase.class);

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseDao#createExampleCriteria(java.lang.Object)
	 */
	public void addAssoications(Criteria criteria, Question exampleInstance) {

		if (exampleInstance.getSection() != null) {
			criteria = criteria.add(Restrictions.eq("section.id",
					exampleInstance.getSection().getId()));
		}

	}

	public com.oreon.kgauge.domain.AnswerChoice findCorrectChoice(
			Question question) {

		return executeSingleResultNamedQuery("", new Object[]{question});

	}

}
