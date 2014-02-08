package com.oreon.phonestore.users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Date;
import javax.ws.rs.core.Response;

import javax.persistence.*;
import org.hibernate.validator.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;
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

import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.witchcraft.base.entity.FileAttachment;
import org.witchcraft.base.entity.BaseEntity;

import com.oreon.phonestore.ProjectUtils;

@Entity
@Table(name = "user")
@Filters({@Filter(name = "archiveFilterDef"), @Filter(name = "tenantFilterDef")})
@Cache(usage = CacheConcurrencyStrategy.NONE)
@XmlRootElement
public class User extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = -1796332121L;

	//@Unique(entityName = "com.oreon.phonestore.users.User", fieldName = "userName")

	@NotNull
	@Length(min = 1, max = 250)
	@Column(name = "userName", unique = true)
	@Field(index = Index.YES)
	@Analyzer(definition = "entityAnalyzer")
	protected String userName

	;

	@NotNull
	@Column(name = "password", unique = false)
	@Field(index = Index.YES)
	@Analyzer(definition = "entityAnalyzer")
	protected String password

	;

	@Column(unique = false)
	protected Boolean enabled = true

	;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_ID"), inverseJoinColumns = @JoinColumn(name = "roles_ID"))
	private Set<Role> roles = new HashSet<Role>();

	public void addRole(Role role) {

		this.roles.add(role);
	}

	@Transient
	public List<com.oreon.phonestore.users.Role> getListRoles() {
		return new ArrayList<com.oreon.phonestore.users.Role>(roles);
	}

	//JSF Friendly function to get count of collections
	public int getRolesCount() {
		return roles.size();
	}

	@NotNull
	@Length(min = 1, max = 250)
	@Column(unique = false)
	@Field(index = Index.YES)
	@Analyzer(definition = "entityAnalyzer")
	protected String email

	;

	@Column(name = "lastLogin", unique = false)
	protected Date lastLogin

	;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {

		return userName;

	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {

		return password;

	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getEnabled() {

		return enabled;

	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {

		return email;

	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getLastLogin() {

		return lastLogin;

	}

	@Transient
	public String getDisplayName() {
		try {
			return userName;
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

		listSearchableFields.add("userName");

		listSearchableFields.add("password");

		listSearchableFields.add("email");

		return listSearchableFields;
	}

	@Field(index = Index.YES, name = "searchData")
	@Analyzer(definition = "entityAnalyzer")
	public String getSearchData() {
		StringBuilder builder = new StringBuilder();

		builder.append(getUserName() + " ");

		builder.append(getPassword() + " ");

		builder.append(getEmail() + " ");

		return builder.toString();
	}

}