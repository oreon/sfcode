package com.oreon.kgauge.web.jsf;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import org.witchcraft.model.jsf.BaseBackingBean;
import org.witchcraft.model.support.service.BaseService;
import org.apache.commons.lang.StringUtils;

import java.util.Set;
import org.apache.commons.collections.ListUtils;

import com.oreon.kgauge.domain.AnsweredQuestion;
import com.oreon.kgauge.service.AnsweredQuestionService;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import org.witchcraft.model.support.Range;

/**
 * This is generated code - to edit code or override methods use - AnsweredQuestion class
 * @author witchCraft Code Generator
 * WARNING  - DO NOT EDIT - CHANGES WILL BE OVERWRITTEN
 */

public class AnsweredQuestionBackingBeanBase
		extends
			BaseBackingBean<AnsweredQuestion> {

	protected AnsweredQuestion answeredQuestion = new AnsweredQuestion();

	protected AnsweredQuestionService answeredQuestionService;

	private Range<Date> rangeCreationDate = new Range<Date>("dateCreated");

	public Range<Date> getRangeCreationDate() {
		return rangeCreationDate;
	}

	public void setRangeCreationDate(Range<Date> rangeCreationDate) {
		this.rangeCreationDate = rangeCreationDate;
	}

	public AnsweredQuestion getAnsweredQuestion() {
		return answeredQuestion;
	}

	public void setAnsweredQuestion(AnsweredQuestion answeredQuestion) {
		this.answeredQuestion = answeredQuestion;
	}

	public void setAnsweredQuestionService(
			AnsweredQuestionService answeredQuestionService) {
		this.answeredQuestionService = answeredQuestionService;
	}

	public AnsweredQuestionService getAnsweredQuestionService() {
		return this.answeredQuestionService;
	}

	@SuppressWarnings("unchecked")
	public BaseService<AnsweredQuestion> getBaseService() {
		return answeredQuestionService;
	}

	public AnsweredQuestion getEntity() {
		return getAnsweredQuestion();
	}

	/**
	 * Any initializations of the member entity should be done in this method - 
	 * It will be called before add new action
	 */
	public void initForAddNew() {

	}

	public void reset() {
		answeredQuestion = new AnsweredQuestion();
		resetRanges();

	}

	@Override
	protected List<Range> getRangeList() {

		List<Range> listRanges = super.getRangeList();

		listRanges.add(rangeCreationDate);
		return listRanges;
	}

	protected void reloadFromId(long id) {
		if (id != 0)
			answeredQuestion = answeredQuestionService.load(id);

	}

	@Override
	public String update() {

		return super.update();
	}

}
