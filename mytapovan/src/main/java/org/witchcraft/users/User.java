package org.witchcraft.users;

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
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Cascade;

import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Analyzer;
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
import org.witchcraft.base.entity.BusinessEntity;
import org.witchcraft.model.support.audit.Auditable;
import org.witchcraft.base.entity.FileAttachment;
import org.hibernate.annotations.Filter;

import org.witchcraft.utils.*;

@Entity
@Table(name = "user")
@Filter(name = "archiveFilterDef")
@Name("user")
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONE)
@AnalyzerDef(name = "customanalyzer", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
		@TokenFilterDef(factory = LowerCaseFilterFactory.class),
		@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {@Parameter(name = "language", value = "English")})})
public class User extends BusinessEntity implements java.io.Serializable, IUser {
	private static final long serialVersionUID = -1796332121L;

	//@Unique(entityName = "com.oreon.trkincidents.users.User", fieldName = "userName")

	@NotNull
	@Length(min = 2, max = 250)
	@Column(name = "userName", unique = true)
	@Field(index = Index.TOKENIZED)
	@Analyzer(definition = "customanalyzer")
	protected String userName;

	@NotNull
	@Column(name = "password", unique = false)
	@Field(index = Index.TOKENIZED)
	@Analyzer(definition = "customanalyzer")
	protected String password;

	protected Boolean enabled;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , targetEntity = org.witchcraft.users.Role.class)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_ID"), inverseJoinColumns = @JoinColumn(name = "roles_ID"))
	private Set<? extends IRole> roles = new HashSet<Role>();

	@NotNull
	@Column(name = "email", unique = false)
	@Field(index = Index.TOKENIZED)
	@Analyzer(definition = "customanalyzer")
	protected String email;

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#setUserName(java.lang.String)
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#getUserName()
	 */
	public String getUserName() {
		return userName;
	}

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#setPassword(java.lang.String)
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#getPassword()
	 */
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#setEnabled(java.lang.Boolean)
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#getEnabled()
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#setRoles(java.util.Set)
	 */
	public void setRoles(Set<? extends IRole> roles) {
		this.roles = roles;
	}

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#getRoles()
	 */
	public Set<Role> getRoles() {
		return (Set<Role>) roles;
	}

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#setEmail(java.lang.String)
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#getEmail()
	 */
	public String getEmail() {
		return email;
	}

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#getDisplayName()
	 */
	@Transient
	public String getDisplayName() {
		try {
			return userName;
		} catch (Exception e) {
			return "Exception - " + e.getMessage();
		}
	}

	//Empty setter , needed for richfaces autocomplete to work 
	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#setDisplayName(java.lang.String)
	 */
	public void setDisplayName(String name) {
	}

	/* (non-Javadoc)
	 * @see org.witchcraft.users.IUser#listSearchableFields()
	 */
	@Override
	public List<String> listSearchableFields() {
		List<String> listSearchableFields = new ArrayList<String>();
		listSearchableFields.addAll(super.listSearchableFields());

		listSearchableFields.add("userName");

		listSearchableFields.add("password");

		listSearchableFields.add("email");

		return listSearchableFields;
	}

	

}
