package com.pcas.datapkg.customReports;

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

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.seam.annotations.Name;

import org.witchcraft.base.entity.BusinessEntity;
import org.witchcraft.model.support.audit.Auditable;
import org.witchcraft.base.entity.FileAttachment;

import org.witchcraft.utils.*;

import com.pcas.datapkg.ProjectUtils;

@Entity
@Table(name = "reportparameter")
@Filter(name = "archiveFilterDef")
@Name("reportParameter")
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Analyzer(definition = "entityAnalyzer")
@XmlRootElement
public class ReportParameter extends BusinessEntity
		implements
			java.io.Serializable {
	private static final long serialVersionUID = -1782108681L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "customReport_id", nullable = false, updatable = true)
	@ContainedIn
	protected CustomReport customReport

	;

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "metaField_id", nullable = false, updatable = true)
	@ContainedIn
	protected MetaField metaField

	;

	@Column(unique = false)
	protected ComparisonType comparison

	;

	@Column(unique = false)
	protected Boolean mandatory

	;

	@Column(unique = false)
	@Field(index = Index.TOKENIZED)
	@Analyzer(definition = "entityAnalyzer")
	protected String defaultValue

	;

	@NotNull
	@Length(min = 1, max = 250)
	@Column(unique = false)
	@Field(index = Index.TOKENIZED)
	@Analyzer(definition = "entityAnalyzer")
	protected String name

	;

	public void setCustomReport(CustomReport customReport) {
		this.customReport = customReport;
	}

	public CustomReport getCustomReport() {

		return customReport;

	}

	public void setMetaField(MetaField metaField) {
		this.metaField = metaField;
	}

	public MetaField getMetaField() {

		return metaField;

	}

	public void setComparison(ComparisonType comparison) {
		this.comparison = comparison;
	}

	public ComparisonType getComparison() {

		return comparison;

	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Boolean getMandatory() {

		return mandatory;

	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {

		return defaultValue;

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {

		return name;

	}

	@Transient
	public String getDisplayName() {
		try {
			return name;
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

		listSearchableFields.add("defaultValue");

		listSearchableFields.add("name");

		return listSearchableFields;
	}

	@Field(index = Index.TOKENIZED, name = "searchData")
	@Analyzer(definition = "entityAnalyzer")
	public String getSearchData() {
		StringBuilder builder = new StringBuilder();

		builder.append(getDefaultValue() + " ");

		builder.append(getName() + " ");

		if (getCustomReport() != null)
			builder.append("customReport:" + getCustomReport().getDisplayName()
					+ " ");

		if (getMetaField() != null)
			builder
					.append("metaField:" + getMetaField().getDisplayName()
							+ " ");

		return builder.toString();
	}

}