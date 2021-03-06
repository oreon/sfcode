package com.nas.recovery.web.action.domain;

import com.oreon.tapovan.domain.Exam;

import org.witchcraft.seam.action.BaseAction;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import org.witchcraft.base.entity.BaseQuery;
import org.witchcraft.base.entity.Range;

import org.jboss.seam.annotations.Observer;

import com.oreon.tapovan.domain.Exam;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class ExamListQueryBase extends BaseQuery<Exam, Long> {

	private static final String EJBQL = "select exam from Exam exam";

	protected Exam exam = new Exam();

	public Exam getExam() {
		return exam;
	}

	private com.oreon.tapovan.domain.Grade gradeToSearch;

	public void setGradeToSearch(com.oreon.tapovan.domain.Grade gradeToSearch) {
		this.gradeToSearch = gradeToSearch;
	}

	public com.oreon.tapovan.domain.Grade getGradeToSearch() {
		return gradeToSearch;
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	public Class<Exam> getEntityClass() {
		return Exam.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private Range<Integer> maxMarksRange = new Range<Integer>();
	public Range<Integer> getMaxMarksRange() {
		return maxMarksRange;
	}
	public void setMaxMarks(Range<Integer> maxMarksRange) {
		this.maxMarksRange = maxMarksRange;
	}

	private Range<Integer> passMarksRange = new Range<Integer>();
	public Range<Integer> getPassMarksRange() {
		return passMarksRange;
	}
	public void setPassMarks(Range<Integer> passMarksRange) {
		this.passMarksRange = passMarksRange;
	}

	private static final String[] RESTRICTIONS = {
			"exam.id = #{examList.exam.id}",

			"lower(exam.name) like concat(lower(#{examList.exam.name}),'%')",

			"#{examList.gradeToSearch} in elements(exam.grades)",

			"exam.maxMarks >= #{examList.maxMarksRange.begin}",
			"exam.maxMarks <= #{examList.maxMarksRange.end}",

			"exam.passMarks >= #{examList.passMarksRange.begin}",
			"exam.passMarks <= #{examList.passMarksRange.end}",

			"exam.dateCreated <= #{examList.dateCreatedRange.end}",
			"exam.dateCreated >= #{examList.dateCreatedRange.begin}",};

	@Observer("archivedExam")
	public void onArchive() {
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, Exam e) {

		builder.append("\"" + (e.getName() != null ? e.getName() : "") + "\",");

		builder.append("\"" + (e.getGrades() != null ? e.getGrades() : "")
				+ "\",");

		builder.append("\"" + (e.getMaxMarks() != null ? e.getMaxMarks() : "")
				+ "\",");

		builder.append("\""
				+ (e.getPassMarks() != null ? e.getPassMarks() : "") + "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Name" + ",");

		builder.append("Grades" + ",");

		builder.append("MaxMarks" + ",");

		builder.append("PassMarks" + ",");

		builder.append("\r\n");
	}
}
