package com.oreon.talent.web.action.users;

import com.oreon.talent.users.AppRole;

import org.witchcraft.seam.action.BaseAction;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import org.apache.commons.lang.StringUtils;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.Component;
import org.jboss.seam.security.Identity;

import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.annotations.Observer;

import org.witchcraft.base.entity.FileAttachment;

import org.apache.commons.io.FileUtils;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

public abstract class AppRoleActionBase extends BaseAction<AppRole>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private AppRole appRole;

	@DataModel
	private List<AppRole> appRoleRecordList;

	public void setAppRoleId(Long id) {
		if (id == 0) {
			clearInstance();
			clearLists();
			loadAssociations();
			return;
		}
		setId(id);
		if (!isPostBack())
			loadAssociations();
	}

	/** for modal dlg we need to load associaitons regardless of postback
	 * @param id
	 */
	public void setAppRoleIdForModalDlg(Long id) {
		setId(id);
		clearLists();
		loadAssociations();
	}

	public Long getAppRoleId() {
		return (Long) getId();
	}

	public AppRole getEntity() {
		return appRole;
	}

	//@Override
	public void setEntity(AppRole t) {
		this.appRole = t;
		loadAssociations();
	}

	public AppRole getAppRole() {
		return (AppRole) getInstance();
	}

	@Override
	protected AppRole createInstance() {
		AppRole instance = super.createInstance();

		return instance;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();

	}

	public boolean isWired() {
		return true;
	}

	public AppRole getDefinedInstance() {
		return (AppRole) (isIdDefined() ? getInstance() : null);
	}

	public void setAppRole(AppRole t) {
		this.appRole = t;
		if (appRole != null)
			setAppRoleId(t.getId());
		loadAssociations();
	}

	@Override
	public Class<AppRole> getEntityClass() {
		return AppRole.class;
	}

	public com.oreon.talent.users.AppRole findByUnqName(String name) {
		return executeSingleResultNamedQuery("appRole.findByUnqName", name);
	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

	}

	public void updateAssociations() {

	}

	public void updateComposedAssociations() {
	}

	public void clearLists() {

	}

	public String viewAppRole() {
		load(currentEntityId);
		return "viewAppRole";
	}

}
