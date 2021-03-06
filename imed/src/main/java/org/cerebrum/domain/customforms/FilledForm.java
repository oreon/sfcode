package org.cerebrum.domain.customforms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Date;

import javax.persistence.*;
import org.hibernate.validator.*;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.IndexedEmbedded;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;
import org.witchcraft.base.entity.*;
import org.hibernate.annotations.Filter;

@Entity
@Table(name = "filledform")
@Name("filledForm")
@Filter(name = "archiveFilterDef")
@Indexed
@AnalyzerDef(name = "customanalyzer", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
		@TokenFilterDef(factory = LowerCaseFilterFactory.class),
		@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {@Parameter(name = "language", value = "English")})})
public class FilledForm extends BusinessEntity {

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "customForm_id", nullable = false, updatable = true)
	@ContainedIn
	protected CustomForm customForm;

	//filledFields->filledForm ->FilledForm->FilledField->FilledField

	@OneToMany(mappedBy = "filledForm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FilledForm_ID", nullable = true)
	@IndexedEmbedded
	private Set<FilledField> filledFields = new HashSet<FilledField>();

	public void setCustomForm(CustomForm customForm) {
		this.customForm = customForm;
	}

	public CustomForm getCustomForm() {
		return customForm;
	}

	public void setFilledFields(Set<FilledField> filledFields) {
		this.filledFields = filledFields;
	}

	public Set<FilledField> getFilledFields() {
		return filledFields;
	}

	@Transient
	public String getDisplayName() {
		return customForm + "";
	}

	/** This method is used by hibernate full text search - override to add additional fields
	 * @see org.witchcraft.model.support.BusinessEntity#retrieveSearchableFieldsArray()
	 */
	@Override
	public List<String> listSearchableFields() {
		List<String> listSearchableFields = new ArrayList<String>();
		listSearchableFields.addAll(super.listSearchableFields());

		listSearchableFields.add("filledFields.value");

		return listSearchableFields;
	}

}
