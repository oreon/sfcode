
/**
 * This is generated code - to edit code or override methods use - ExamInstance class
 * @author witchCraft Code Generator
 * WARNING  - DO NOT EDIT - CHANGES WILL BE OVERWRITTEN
 */

package com.oreon.kgauge.domain;

import javax.persistence.*;
import org.hibernate.annotations.Cascade;

import org.witchcraft.model.jsf.Image;
import java.util.Date;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;

@MappedSuperclass
/*This is the result of an exam actually being written by a candidate.*/
public abstract class ExamInstanceBase
		extends
			org.witchcraft.model.support.BusinessEntity
		implements
			java.io.Serializable,
			org.witchcraft.model.support.audit.Auditable {

	private static final long serialVersionUID = 1L;

	/* Default Constructor */
	public ExamInstanceBase() {
	}

	private com.oreon.kgauge.domain.Candidate candidate;

	private com.oreon.kgauge.domain.Exam exam;

	private java.util.Set<com.oreon.kgauge.domain.AnsweredQuestion> answeredQuestion = new java.util.HashSet<com.oreon.kgauge.domain.AnsweredQuestion>();

	@ManyToOne
	@JoinColumn(name = "candidate_ID", nullable = false)
	@XmlTransient
	public com.oreon.kgauge.domain.Candidate getCandidate() {
		return this.candidate;
	}

	public void setCandidate(com.oreon.kgauge.domain.Candidate candidate) {
		this.candidate = candidate;
	}

	@ManyToOne
	@JoinColumn(name = "exam_ID", nullable = false)
	@XmlTransient
	public com.oreon.kgauge.domain.Exam getExam() {
		return this.exam;
	}

	public void setExam(com.oreon.kgauge.domain.Exam exam) {
		this.exam = exam;
	}

	public void add(com.oreon.kgauge.domain.AnsweredQuestion answeredQuestion) {
		this.answeredQuestion.add(answeredQuestion);
	}

	public void remove(com.oreon.kgauge.domain.AnsweredQuestion answeredQuestion) {
		this.answeredQuestion.remove(answeredQuestion);
	}

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "examInstance_ID", nullable = true)
	public java.util.Set<com.oreon.kgauge.domain.AnsweredQuestion> getAnsweredQuestion() {
		return this.answeredQuestion;
	}

	public void setAnsweredQuestion(
			java.util.Set<com.oreon.kgauge.domain.AnsweredQuestion> answeredQuestion) {
		this.answeredQuestion = answeredQuestion;
	}

	@Transient
	public java.util.Iterator<com.oreon.kgauge.domain.AnsweredQuestion> getAnsweredQuestionIterator() {
		return this.answeredQuestion.iterator();
	}

	/** Method size on the set doesn't work with technologies requiring 
	 *  java beans get/set  interface so we provide a getter method 
	 * @return
	 */
	@Transient
	public int getAnsweredQuestionCount() {
		return this.answeredQuestion.size();
	}

	public Integer calculateScore() {
		return null;
		//should return Integer
	}

	public abstract ExamInstance examInstanceInstance();

}
