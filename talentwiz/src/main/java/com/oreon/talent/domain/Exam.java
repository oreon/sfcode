package com.oreon.talent.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Date;
import javax.ws.rs.core.Response;

import javax.persistence.*;
import org.hibernate.validator.*;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Cascade;

import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.IndexedEmbedded;

import org.hibernate.annotations.Filter;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.seam.annotations.Name;

import org.witchcraft.base.entity.BusinessEntity;
import org.witchcraft.model.support.audit.Auditable;
import org.witchcraft.base.entity.FileAttachment;

import org.witchcraft.utils.*;

import com.oreon.talent.ProjectUtils;

@Entity
@Table(name = "exam")
@Filter(name = "archiveFilterDef")
@Name("exam")
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Analyzer(definition = "entityAnalyzer")
@XmlRootElement
public class Exam extends BusinessEntity implements java.io.Serializable {
	private static final long serialVersionUID = 172727827L;

	@NotNull
	@Length(min = 1, max = 250)
	@Column(unique = true)
	@Field(index = Index.TOKENIZED)
	@Analyzer(definition = "entityAnalyzer")
	protected String title

	;

	@Lob
	@Column(unique = false)
	@Field(index = Index.TOKENIZED)
	@Analyzer(definition = "entityAnalyzer")
	protected String description

	;

	@OneToMany(mappedBy = "exam", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinColumn(name = "exam_ID", nullable = false)
	@OrderBy("dateCreated DESC")
	@IndexedEmbedded
	private Set<Question> questions = new HashSet<Question>();

	public void addQuestion(Question question) {
		question.setExam(this);
		this.questions.add(question);
	}

	@Transient
	public List<com.oreon.talent.domain.Question> getListQuestions() {
		return new ArrayList<com.oreon.talent.domain.Question>(questions);
	}

	//JSF Friendly function to get count of collections
	public int getQuestionsCount() {
		return questions.size();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {

		return title;

	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {

		return description;

	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	@Transient
	public String getDisplayName() {
		try {
			return title;
		} catch (Exception e) {
			return "Exception - " + e.getMessage();
		}
	}

	@Transient
	public String getDescriptionAbbreviated() {
		try {
			return org.apache.commons.lang.WordUtils.abbreviate(description
					.trim(), 100, 200, "...");
		} catch (Exception e) {
			return description != null ? description : "";
		}
	}

	//Empty setter , needed for richfaces autocomplete to work 
	public void setDisplayName(String name) {
	}

	/** This method is used by hibernate full text search - override to add additional fields
	 * @see org.witchcraft.model.support.BusinessEntity#retrieveSearchableFieldsArray()
	 */
	@Override
	public List<String> listSearchableFields() {
		List<String> listSearchableFields = new ArrayList<String>();
		listSearchableFields.addAll(super.listSearchableFields());

		listSearchableFields.add("title");

		listSearchableFields.add("description");

		listSearchableFields.add("questions.text");

		return listSearchableFields;
	}

	@Field(index = Index.TOKENIZED, name = "searchData")
	@Analyzer(definition = "entityAnalyzer")
	public String getSearchData() {
		StringBuilder builder = new StringBuilder();

		builder.append(getTitle() + " ");

		builder.append(getDescription() + " ");

		for (BusinessEntity e : questions) {
			builder.append(e.getDisplayName() + " ");
		}

		return builder.toString();
	}

}
