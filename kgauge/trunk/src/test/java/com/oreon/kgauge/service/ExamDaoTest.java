package com.oreon.kgauge.service;

import com.oreon.kgauge.domain.Exam;
import org.springframework.test.jpa.AbstractJpaTests;
import java.util.List;

import org.witchcraft.model.support.testing.TestDataFactory;
import org.witchcraft.model.support.springbeanhelpers.BeanHelper;

import java.text.SimpleDateFormat;

import javax.persistence.PersistenceException;
import org.hibernate.PropertyValueException;
import java.util.Date;

public class ExamDaoTest extends AbstractJpaTests {

	protected Exam examInstance = new Exam();

	protected ExamService examService;

	protected boolean bTest = true;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy.MM.dd HH:mm:ss z");

	public void setExamService(ExamService examService) {
		this.examService = examService;
	}

	protected TestDataFactory examTestDataFactory = (TestDataFactory) BeanHelper
			.getBean("examTestDataFactory");

	@Override
	protected String[] getConfigLocations() {
		return new String[]{"classpath:/applicationContext.xml",
				"classpath:/testDataFactories.xml"};
	}

	@Override
	protected void runTest() throws Throwable {
		if (!bTest)
			return;
		super.runTest();
	}

	/**
	 * Do the setup before the test in this method
	 **/
	protected void onSetUpInTransaction() throws Exception {
		try {

			examInstance.setExamNumber("pi");
			examInstance.setName("pi");
			examInstance.setDescription("zeta");
			examInstance.setQuestions(5028);
			examInstance.setDuration(8704);
			examInstance.setPrice(24.57);
			examInstance
					.setScoringStrategy(com.oreon.kgauge.domain.ScoringType.ScoreForAllAnswers);
			examInstance
					.setExamStatus(com.oreon.kgauge.domain.ExamStatus.INACTIVE);

			TestDataFactory categoryTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("categoryTestDataFactory");

			examInstance
					.setCategory((com.oreon.kgauge.domain.Category) categoryTestDataFactory
							.loadOneRecord());

			TestDataFactory examCreatorTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("examCreatorTestDataFactory");

			examInstance
					.setExamCreator((com.oreon.kgauge.domain.ExamCreator) examCreatorTestDataFactory
							.loadOneRecord());

			examService.save(examInstance);
		} catch (PersistenceException pe) {
			//if this instance can't be created due to back references e.g an orderItem needs an Order - 
			// - we will simply skip generated tests.
			if (pe.getCause() instanceof PropertyValueException
					&& pe.getMessage().contains("Backref")) {
				bTest = false;
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	//test saving a new record and updating an existing record;
	public void testSave() {

		try {
			Exam exam = new Exam();

			try {

				exam.setExamNumber("pi");
				exam.setName("alpha");
				exam.setDescription("zeta");
				exam.setQuestions(2615);
				exam.setDuration(3663);
				exam.setPrice(74.95);
				exam
						.setScoringStrategy(com.oreon.kgauge.domain.ScoringType.ScoreOnlyForCorrectAnswers);
				exam.setExamStatus(com.oreon.kgauge.domain.ExamStatus.INACTIVE);

				TestDataFactory categoryTestDataFactory = (TestDataFactory) BeanHelper
						.getBean("categoryTestDataFactory");

				exam
						.setCategory((com.oreon.kgauge.domain.Category) categoryTestDataFactory
								.loadOneRecord());

				TestDataFactory examCreatorTestDataFactory = (TestDataFactory) BeanHelper
						.getBean("examCreatorTestDataFactory");

				exam
						.setExamCreator((com.oreon.kgauge.domain.ExamCreator) examCreatorTestDataFactory
								.loadOneRecord());

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			examService.save(exam);
			assertNotNull(exam.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testEdit() {

		try {
			//test saving a new record and updating an existing record;
			Exam exam = (Exam) examTestDataFactory.loadOneRecord();

			exam.setExamNumber("delta");
			exam.setName("pi");
			exam.setDescription("epsilon");
			exam.setQuestions(5984);
			exam.setDuration(1945);
			exam.setPrice(43.12);
			exam
					.setScoringStrategy(com.oreon.kgauge.domain.ScoringType.ScoreForAllAnswers);
			exam.setExamStatus(com.oreon.kgauge.domain.ExamStatus.ACTIVE);

			examService.save(exam);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testCount() {
		assertTrue(examService.getCount() > 0);
	}

	//count the number of records - add one delete it - check count is same after delete
	public void testDelete() {

		try {
			long count, newCount, diff = 0;
			count = examService.getCount();
			Exam exam = (Exam) examTestDataFactory.loadOneRecord();
			examService.delete(exam);
			newCount = examService.getCount();
			diff = newCount - count;
			assertEquals(diff, 1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testLoad() {

		try {
			Exam exam = examService.load(examInstance.getId());
			assertNotNull(exam.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testSearchByExample() {
		try {
			List<Exam> exams = examService.searchByExample(examInstance);
			assertTrue(!exams.isEmpty());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
