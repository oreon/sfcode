
/**
 * This is generated code - to edit code or override methods use - User class
 * @author witchCraft Code Generator
 * WARNING  - DO NOT EDIT - CHANGES WILL BE OVERWRITTEN
 */

package com.oreon.kgauge.domain;

import javax.persistence.*;
import org.hibernate.annotations.Cascade;

import org.witchcraft.model.jsf.Image;
import java.util.Date;
import javax.xml.bind.annotation.XmlTransient;

@MappedSuperclass
public abstract class UserBase
		extends
			org.witchcraft.model.support.BusinessEntity
		implements
			java.io.Serializable,
			org.witchcraft.model.support.audit.Auditable {

	private static final long serialVersionUID = 1L;

	protected String userName;

	protected String password;

	protected Boolean enabled;

	/* Default Constructor */
	public UserBase() {
	}

	/* Constructor with all attributes */
	public UserBase(String userName, String password, Boolean enabled) {
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
	}

	@Column(nullable = false, unique = true)
	/*
	
	 */
	public String getUserName() {

		return this.userName;
	}

	@Column(nullable = false, unique = false)
	/*
	
	 */
	public String getPassword() {

		return this.password;
	}

	/*
	
	 */
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	private java.util.Set<com.oreon.kgauge.domain.Authority> authorities = new java.util.HashSet<com.oreon.kgauge.domain.Authority>();

	public void addAuthoritie(com.oreon.kgauge.domain.Authority authorities) {
		authorities.setUser(userInstance());
		this.authorities.add(authorities);
	}

	public void removeAuthoritie(com.oreon.kgauge.domain.Authority authorities) {
		this.authorities.remove(authorities);
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_ID", nullable = false)
	public java.util.Set<com.oreon.kgauge.domain.Authority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(
			java.util.Set<com.oreon.kgauge.domain.Authority> authorities) {
		this.authorities = authorities;
	}

	@Transient
	public java.util.Iterator<com.oreon.kgauge.domain.Authority> getAuthoritiesIterator() {
		return this.authorities.iterator();
	}

	/** Method size on the set doesn't work with technologies requiring 
	 *  java beans get/set  interface so we provide a getter method 
	 * @return
	 */
	@Transient
	public int getAuthoritiesCount() {
		return this.authorities.size();
	}

	public abstract User userInstance();

	@Transient
	public String getDisplayName() {
		return userName + "";
	}

}