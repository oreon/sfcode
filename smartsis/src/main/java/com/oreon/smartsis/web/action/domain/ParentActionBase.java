package com.oreon.smartsis.web.action.domain;

import com.oreon.smartsis.domain.Parent;

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

public abstract class ParentActionBase
		extends
			com.oreon.smartsis.web.action.domain.PersonAction<Parent>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private Parent parent;

	@In(create = true, value = "userAction")
	com.oreon.smartsis.web.action.users.UserAction userAction;

	@In(create = true, value = "parentGroupAction")
	com.oreon.smartsis.web.action.domain.ParentGroupAction parentGroupAction;

	@DataModel
	private List<Parent> parentRecordList;

	public void setParentId(Long id) {
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
	public void setParentIdForModalDlg(Long id) {
		setId(id);
		clearLists();
		loadAssociations();
	}

	public void setUserId(Long id) {

		if (id != null && id > 0)
			getInstance().setUser(userAction.loadFromId(id));

	}

	public Long getUserId() {
		if (getInstance().getUser() != null)
			return getInstance().getUser().getId();
		return 0L;
	}

	public void setParentGroupId(Long id) {

		if (id != null && id > 0)
			getInstance().setParentGroup(parentGroupAction.loadFromId(id));

	}

	public Long getParentGroupId() {
		if (getInstance().getParentGroup() != null)
			return getInstance().getParentGroup().getId();
		return 0L;
	}

	public Long getParentId() {
		return (Long) getId();
	}

	public Parent getEntity() {
		return parent;
	}

	//@Override
	public void setEntity(Parent t) {
		this.parent = t;
		loadAssociations();
	}

	public Parent getParent() {
		return (Parent) getInstance();
	}

	@Override
	protected Parent createInstance() {
		return new Parent();
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();

		com.oreon.smartsis.users.User user = userAction.getDefinedInstance();
		if (user != null && isNew()) {
			getInstance().setUser(user);
		}

		com.oreon.smartsis.domain.ParentGroup parentGroup = parentGroupAction
				.getDefinedInstance();
		if (parentGroup != null && isNew()) {
			getInstance().setParentGroup(parentGroup);
		}

	}

	public boolean isWired() {
		return true;
	}

	public Parent getDefinedInstance() {
		return (Parent) (isIdDefined() ? getInstance() : null);
	}

	public void setParent(Parent t) {
		this.parent = t;
		if (parent != null)
			setParentId(t.getId());
		loadAssociations();
	}

	@Override
	public Class<Parent> getEntityClass() {
		return Parent.class;
	}

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseAction#createExampleCriteria(java.lang.Object)
	 */
	@Override
	public void addAssociations(Criteria criteria) {

		if (parent.getUser() != null) {
			criteria = criteria.add(Restrictions.eq("user.id", parent.getUser()
					.getId()));
		}

		if (parent.getParentGroup() != null) {
			criteria = criteria.add(Restrictions.eq("parentGroup.id", parent
					.getParentGroup().getId()));
		}

	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		if (parent.getUser() != null) {
			userAction.setInstance(getInstance().getUser());
		}

		if (parent.getParentGroup() != null) {
			parentGroupAction.setInstance(getInstance().getParentGroup());
		}

	}

	public void updateAssociations() {

	}

	public void updateComposedAssociations() {
	}

	public void clearLists() {

	}

	public Parent getCurrentLoggedInParent() {
		String query = "Select e from Parent e where e.user.userName = ?1";
		return (Parent) executeSingleResultQuery(query, Identity.instance()
				.getCredentials().getUsername());
	}

}