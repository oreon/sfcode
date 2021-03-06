package com.oreon.smartsis.fees;

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

import com.oreon.smartsis.ProjectUtils;

@Entity
@Table(name = "studentpaidfee")
@Filter(name = "archiveFilterDef")
@Name("studentPaidFee")
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Analyzer(definition = "entityAnalyzer")
@XmlRootElement
public class StudentPaidFee extends BusinessEntity
		implements
			java.io.Serializable {
	private static final long serialVersionUID = 866326586L;

	@Transient
	protected Double amountOwed

	;

	@NotNull
	@Column(name = "amountPaid", unique = false)
	protected Double amountPaid

	;

	@Column(unique = false)
	protected Double dueAmount

	;

	@Column(name = "dateOfPayment", unique = false)
	protected Date dateOfPayment

	= new Date();

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id", nullable = false, updatable = true)
	@ContainedIn
	protected com.oreon.smartsis.domain.Student student

	;

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "monthlyFee_id", nullable = false, updatable = true)
	@ContainedIn
	protected MonthlyFee monthlyFee

	;

	@Column(name = "year", unique = false)
	protected Integer year

	= ProjectUtils.getCurrentYear();

	@Column(unique = false)
	protected com.oreon.smartsis.domain.FeeMonth month

	;

	public void setAmountOwed(Double amountOwed) {
		this.amountOwed = amountOwed;
	}

	public Double getAmountOwed() {

		try {
			return monthlyFee.getTotal()
					* (100.0 - ((student.getScholarship() == null)
							? 0.0
							: student.getScholarship())) / 100.0;
		} catch (Exception e) {

			return 0.0;

		}

	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Double getAmountPaid() {

		return amountPaid;

	}

	public void setDueAmount(Double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public Double getDueAmount() {

		return dueAmount;

	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public Date getDateOfPayment() {

		return dateOfPayment;

	}

	public void setStudent(com.oreon.smartsis.domain.Student student) {
		this.student = student;
	}

	public com.oreon.smartsis.domain.Student getStudent() {

		return student;

	}

	public void setMonthlyFee(MonthlyFee monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	public MonthlyFee getMonthlyFee() {

		return monthlyFee;

	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYear() {

		return year;

	}

	public void setMonth(com.oreon.smartsis.domain.FeeMonth month) {
		this.month = month;
	}

	public com.oreon.smartsis.domain.FeeMonth getMonth() {

		return month;

	}

	@Transient
	public String getDisplayName() {
		try {
			return amountOwed + "";
		} catch (Exception e) {
			return "Exception - " + e.getMessage();
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

		return listSearchableFields;
	}

	@Field(index = Index.TOKENIZED, name = "searchData")
	@Analyzer(definition = "entityAnalyzer")
	public String getSearchData() {
		StringBuilder builder = new StringBuilder();

		if (getStudent() != null)
			builder.append("student:" + getStudent().getDisplayName() + " ");

		if (getMonthlyFee() != null)
			builder.append("monthlyFee:" + getMonthlyFee().getDisplayName()
					+ " ");

		return builder.toString();
	}

}
