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

import org.witchcraft.base.entity.BaseEntity;
import org.witchcraft.model.support.audit.Auditable;
import org.witchcraft.base.entity.FileAttachment;

import org.witchcraft.utils.*;

import com.pcas.datapkg.ProjectUtils;

@Entity
@Table(name = "roleprevilige")
@Filter(name = "archiveFilterDef")
@Name("rolePrevilige")
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Analyzer(definition = "entityAnalyzer")
@XmlRootElement
public class RolePrevilige extends BaseEntity
		implements
			java.io.Serializable {
	private static final long serialVersionUID = -211228803L;

	@Column(unique = false)
	protected Boolean edit

	;

	@Column(unique = false)
	protected Boolean view

	;

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "appRole_id", nullable = false, updatable = true)
	@ContainedIn
	protected com.pcas.datapkg.users.AppRole appRole

	;

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fieldPrevilige_id", nullable = false, updatable = true)
	@ContainedIn
	protected FieldPrevilige fieldPrevilige

	;

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public Boolean getEdit() {

		return edit;

	}

	public void setView(Boolean view) {
		this.view = view;
	}

	public Boolean getView() {

		return view;

	}

	public void setAppRole(com.pcas.datapkg.users.AppRole appRole) {
		this.appRole = appRole;
	}

	public com.pcas.datapkg.users.AppRole getAppRole() {

		return appRole;

	}

	public void setFieldPrevilige(FieldPrevilige fieldPrevilige) {
		this.fieldPrevilige = fieldPrevilige;
	}

	public FieldPrevilige getFieldPrevilige() {

		return fieldPrevilige;

	}

	@Transient
	public String getDisplayName() {
		try {
			return edit + "";
		} catch (Exception e) {
			return "Exception - " + e.getMessage();
		}
	}

	//Empty setter , needed for richfaces autocomplete to work 
	public void setDisplayName(String name) {
	}

	/** This method is used by hibernate full text search - override to add additional fields
	 * @see org.witchcraft.model.support.BaseEntity#retrieveSearchableFieldsArray()
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

		if (getAppRole() != null)
			builder.append("appRole:" + getAppRole().getDisplayName() + " ");

		if (getFieldPrevilige() != null)
			builder.append("fieldPrevilige:"
					+ getFieldPrevilige().getDisplayName() + " ");

		return builder.toString();
	}

}