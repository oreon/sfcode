package com.oreon.kgauge.domain;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import org.witchcraft.model.support.springbeanhelpers.BeanHelper;
import org.witchcraft.model.support.testing.AbstractTestDataFactory;

import org.witchcraft.model.support.testing.TestDataFactory;
import org.witchcraft.model.support.errorhandling.BusinessException;
import org.witchcraft.model.randomgen.RandomValueGeneratorFactory;

import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import com.oreon.kgauge.service.QuestionService;

@Transactional
public class QuestionTestDataFactory extends AbstractTestDataFactory<Question> {

	private List<Question> questions = new ArrayList<Question>();

	private static final Logger logger = Logger
			.getLogger(QuestionTestDataFactory.class);

	private static int RECORDS_TO_CREATE = 30;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy.MM.dd HH:mm:ss z");

	QuestionService questionService;

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public void register(Question question) {
		questions.add(question);
	}

	public Question createQuestionOne() {
		Question question = new Question();

		try {

			question.setQuestionText("alpha");
			question.setDifficultyLevel(36);

			TestDataFactory examTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("examTestDataFactory");

			question.setExam((com.oreon.kgauge.domain.Exam) examTestDataFactory
					.loadOneRecord());

			TestDataFactory sectionTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("sectionTestDataFactory");

			question
					.setSection((com.oreon.kgauge.domain.Section) sectionTestDataFactory
							.loadOneRecord());

			register(question);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return question;
	}

	public Question createQuestionTwo() {
		Question question = new Question();

		try {

			question.setQuestionText("zeta");
			question.setDifficultyLevel(882);

			TestDataFactory examTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("examTestDataFactory");

			question.setExam((com.oreon.kgauge.domain.Exam) examTestDataFactory
					.loadOneRecord());

			TestDataFactory sectionTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("sectionTestDataFactory");

			question
					.setSection((com.oreon.kgauge.domain.Section) sectionTestDataFactory
							.loadOneRecord());

			register(question);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return question;
	}

	public Question createQuestionThree() {
		Question question = new Question();

		try {

			question.setQuestionText("pi");
			question.setDifficultyLevel(6482);

			TestDataFactory examTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("examTestDataFactory");

			question.setExam((com.oreon.kgauge.domain.Exam) examTestDataFactory
					.loadOneRecord());

			TestDataFactory sectionTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("sectionTestDataFactory");

			question
					.setSection((com.oreon.kgauge.domain.Section) sectionTestDataFactory
							.loadOneRecord());

			register(question);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return question;
	}

	public Question createQuestionFour() {
		Question question = new Question();

		try {

			question.setQuestionText("epsilon");
			question.setDifficultyLevel(1196);

			TestDataFactory examTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("examTestDataFactory");

			question.setExam((com.oreon.kgauge.domain.Exam) examTestDataFactory
					.loadOneRecord());

			TestDataFactory sectionTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("sectionTestDataFactory");

			question
					.setSection((com.oreon.kgauge.domain.Section) sectionTestDataFactory
							.loadOneRecord());

			register(question);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return question;
	}

	public Question createQuestionFive() {
		Question question = new Question();

		try {

			question.setQuestionText("delta");
			question.setDifficultyLevel(2632);

			TestDataFactory examTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("examTestDataFactory");

			question.setExam((com.oreon.kgauge.domain.Exam) examTestDataFactory
					.loadOneRecord());

			TestDataFactory sectionTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("sectionTestDataFactory");

			question
					.setSection((com.oreon.kgauge.domain.Section) sectionTestDataFactory
							.loadOneRecord());

			register(question);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return question;
	}

	public Question loadOneRecord() {
		List<Question> questions = questionService.loadAll();

		if (questions.isEmpty()) {
			persistAll();
			questions = questionService.loadAll();
		}

		return questions.get(new Random().nextInt(questions.size()));
	}

	public List<Question> getAllAsList() {

		if (questions.isEmpty()) {

			createQuestionOne();
			createQuestionTwo();
			createQuestionThree();
			createQuestionFour();
			createQuestionFive();

		}

		return questions;
	}

	public void persistAll() {
		if (!isPersistable() || alreadyPersisted)
			return;

		getAllAsList();

		for (Question question : questions) {
			try {
				questionService.save(question);
			} catch (BusinessException be) {
				logger.warn(" Question " + question.getDisplayName()
						+ "couldn't be saved " + be.getMessage());
			}
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
			Question question = createRandomQuestion();
			questionService.save(question);
		}
	}

	public Question createRandomQuestion() {
		Question question = new Question();

		question.setQuestionText((String) RandomValueGeneratorFactory
				.createInstance("String"));
		question.setDifficultyLevel((Integer) RandomValueGeneratorFactory
				.createInstance("Integer"));

		TestDataFactory examTestDataFactory = (TestDataFactory) BeanHelper
				.getBean("examTestDataFactory");

		question.setExam((com.oreon.kgauge.domain.Exam) examTestDataFactory
				.loadOneRecord());

		TestDataFactory sectionTestDataFactory = (TestDataFactory) BeanHelper
				.getBean("sectionTestDataFactory");

		question
				.setSection((com.oreon.kgauge.domain.Section) sectionTestDataFactory
						.loadOneRecord());

		return question;
	}

}
