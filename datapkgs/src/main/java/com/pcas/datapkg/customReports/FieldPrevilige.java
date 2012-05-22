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
@Table(name = "fieldprevilige")
@Filter(name = "archiveFilterDef")
@Name("fieldPrevilige")
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Analyzer(definition = "entityAnalyzer")
@XmlRootElement
public class FieldPrevilige extends BusinessEntity
		implements
			java.io.Serializable {
	private static final long serialVersionUID = -463823175L;

	@OneToMany(mappedBy = "fieldPrevilige", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinColumn(name = "fieldPrevilige_ID", nullable = true)
	@OrderBy("dateCreated DESC")
	@IndexedEmbedded
	private Set<RolePrevilige> rolePreviliges = new HashSet<RolePrevilige>();

	public void addRolePrevilige(RolePrevilige rolePrevilige) {
		rolePrevilige.setFieldPrevilige(this);
		this.rolePreviliges.add(rolePrevilige);
	}

	@Transient
	public List<com.pcas.datapkg.customReports.RolePrevilige> getListRolePreviliges() {
		return new ArrayList<com.pcas.datapkg.customReports.RolePrevilige>(
				rolePreviliges);
	}

	//JSF Friendly function to get count of collections
	public int getRolePreviligesCount() {
		return rolePreviliges.size();
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "metaField_id", nullable = false, updatable = true)
	@ContainedIn
	protected MetaField metaField

	;

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "entityPrevilige_id", nullable = false, updatable = true)
	@ContainedIn
	protected EntityPrevilige entityPrevilige

	;

	public void setRolePreviliges(Set<RolePrevilige> rolePreviliges) {
		this.rolePreviliges = rolePreviliges;
	}

	public Set<RolePrevilige> getRolePreviliges() {
		return rolePreviliges;
	}

	public void setMetaField(MetaField metaField) {
		this.metaField = metaField;
	}

	public MetaField getMetaField() {

		return metaField;

	}

	public void setEntityPrevilige(EntityPrevilige entityPrevilige) {
		this.entityPrevilige = entityPrevilige;
	}

	public EntityPrevilige getEntityPrevilige() {

		return entityPrevilige;

	}

	@Transient
	public String getDisplayName() {
		try {
			return rolePreviliges + "";
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

		if (getMetaField() != null)
			builder
					.append("metaField:" + getMetaField().getDisplayName()
							+ " ");

		if (getEntityPrevilige() != null)
			builder.append("entityPrevilige:"
					+ getEntityPrevilige().getDisplayName() + " ");

		for (BusinessEntity e : rolePreviliges) {
			builder.append(e.getDisplayName() + " ");
		}

		return builder.toString();
	}

}
