package com.oreon.kgauge.domain;

import javax.persistence.Entity;

import org.apache.log4j.Logger;

@Entity
public class Exam extends ExamBase {

	private static final Logger log = Logger.getLogger(Exam.class);

	/* Default Constructor */
	public Exam() {
	}

	/* Constructor with all attributes */
	public Exam(String description, String name, Integer questions,
			Integer duration, Double price, ScoringType scoringStrategy,
			ExamStatus examStatus) {
		super(description, name, questions, duration, price, scoringStrategy,
				examStatus);
	}

	public Exam examInstance() {
		return this;
	}
}