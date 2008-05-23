package com.oreon.kgauge.domain;

import javax.persistence.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import javax.jws.WebService;

import java.util.Date;

@Entity
public class ExamInstance extends ExamInstanceBase
		implements
			java.io.Serializable {

	private static final Logger log = Logger.getLogger(ExamInstance.class);
	private static final long serialVersionUID = 1L;

	/* Default Constructor */
	public ExamInstance() {
	}

	public ExamInstance examInstanceInstance() {
		return this;
	}

}