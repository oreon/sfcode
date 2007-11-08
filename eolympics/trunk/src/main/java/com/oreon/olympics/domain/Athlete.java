package com.oreon.olympics.domain;

import javax.persistence.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;

public class Athlete extends AthleteBase {

	private static final Logger log = Logger.getLogger(Athlete.class);

	public Athlete athleteInstance() {
		return this;
	}
}
