package com.oreon.kgauge.web.jsf;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import org.witchcraft.model.jsf.BaseBackingBean;
import org.witchcraft.model.support.service.BaseService;
import org.apache.commons.lang.StringUtils;

import com.oreon.kgauge.domain.ExamCreator;
import com.oreon.kgauge.service.ExamCreatorService;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.witchcraft.model.support.Range;

/**
 * This is generated code - to edit code or override methods use - ExamCreator class
 * @author witchCraft Code Generator
 * WARNING  - DO NOT EDIT - CHANGES WILL BE OVERWRITTEN
 */

public class ExamCreatorBackingBeanBase extends BaseBackingBean<ExamCreator> {

	protected ExamCreator examCreator = new ExamCreator();

	protected ExamCreatorService examCreatorService;

	private String repeatPassword;

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatpassword) {
		this.repeatPassword = repeatpassword;
	}

	private Range<Date> rangeCreationDate = new Range<Date>("dateCreated");

	public Range<Date> getRangeCreationDate() {
		return rangeCreationDate;
	}

	public void setRangeCreationDate(Range<Date> rangeCreationDate) {
		this.rangeCreationDate = rangeCreationDate;
	}

	private Range<Date> rangeDateOfBirth = new Range<Date>("dateOfBirth");

	public Range<Date> getRangeDateOfBirth() {
		return rangeDateOfBirth;
	}

	public void setRangeDateOfBirth(Range<Date> rangeDateOfBirth) {
		this.rangeDateOfBirth = rangeDateOfBirth;
	}

	public void setExamCreatorService(ExamCreatorService examCreatorService) {
		this.examCreatorService = examCreatorService;
	}

	public ExamCreator getExamCreator() {
		return examCreator;
	}

	public void set(ExamCreator examCreator) {
		this.examCreator = examCreator;
	}

	@SuppressWarnings("unchecked")
	public BaseService<ExamCreator> getBaseService() {
		return examCreatorService;
	}

	public ExamCreator getEntity() {
		return getExamCreator();
	}

	public void reset() {
		examCreator = new ExamCreator();

	}

	@Override
	protected List<Range> getRangeList() {

		List<Range> listRanges = super.getRangeList();

		listRanges.add(rangeDateOfBirth);

		listRanges.add(rangeCreationDate);
		return listRanges;
	}

	protected void reloadFromId(long id) {
		examCreator = examCreatorService.load(id);
	}

}
