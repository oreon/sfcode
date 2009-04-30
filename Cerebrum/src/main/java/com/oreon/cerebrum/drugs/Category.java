package com.oreon.cerebrum.drugs;

import javax.persistence.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import javax.jws.WebService;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Indexed;

import java.util.Date;

@Entity
@Indexed
@Analyzer(impl = org.witchcraft.lucene.analyzers.EnglishAnalyzer.class)
public class Category extends CategoryBase implements java.io.Serializable {

	private static final Logger log = Logger.getLogger(Category.class);
	private static final long serialVersionUID = 1L;

	/* Default Constructor */
	public Category() {
	}

	/* Constructor with all attributes */
	public Category(String name) {
		super(name, name);
	}

	public Category categoryInstance() {
		return this;
	}

}