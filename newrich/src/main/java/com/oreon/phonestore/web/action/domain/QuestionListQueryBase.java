package com.oreon.phonestore.web.action.domain;

import java.util.List;

import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.security.Restrict;
import org.witchcraft.base.entity.BaseQuery;

import com.oreon.phonestore.domain.Question;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class QuestionListQueryBase extends BaseQuery<Question, Long> {

	private static final String EJBQL = "select question from Question question";

	protected Question question = new Question();

	public Question getQuestion() {
		return question;
	}

	@Override
	public Question getInstance() {
		return getQuestion();
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	@Restrict("#{s:hasPermission('question', 'view')}")
	public List<Question> getResultList() {
		return super.getResultList();
	}

	@Override
	public Class<Question> getEntityClass() {
		return Question.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"question.id = #{questionList.question.id}",

			"question.archived = #{questionList.question.archived}",

			"lower(question.text) like concat(lower(#{questionList.question.text}),'%')",

			"question.exam.id = #{questionList.question.exam.id}",

			"question.dateCreated <= #{questionList.dateCreatedRange.end}",
			"question.dateCreated >= #{questionList.dateCreatedRange.begin}",};

	public List<Question> getQuestionsByExam(
			com.oreon.phonestore.domain.Exam exam) {
		//setMaxResults(10000);
		question.setExam(exam);
		return getResultList();
	}

	@Observer("archivedQuestion")
	public void onArchive() {
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, Question e) {

		builder.append("\""
				+ (e.getText() != null ? e.getText().replace(",", "") : "")
				+ "\",");

		builder.append("\""
				+ (e.getExam() != null ? e.getExam().getDisplayName().replace(
						",", "") : "") + "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Text" + ",");

		builder.append("Exam" + ",");

		builder.append("\r\n");
	}
}