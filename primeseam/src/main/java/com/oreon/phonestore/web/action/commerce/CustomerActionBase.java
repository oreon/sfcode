package com.oreon.phonestore.web.action.commerce;

import com.oreon.phonestore.domain.commerce.Customer;

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
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.annotations.web.RequestParameter;

import org.witchcraft.base.entity.FileAttachment;

import org.apache.commons.io.FileUtils;

import org.primefaces.model.DualListModel;

import org.witchcraft.seam.action.BaseAction;
import org.witchcraft.base.entity.BaseEntity;

import com.oreon.phonestore.domain.commerce.CustomerQuestion;
import com.oreon.phonestore.domain.commerce.CustomerOrder;

//
public abstract class CustomerActionBase
		extends
			com.oreon.phonestore.web.action.domain.PersonAction<Customer>
		implements
			java.io.Serializable {

	@RequestParameter
	protected Long customerId;

	@In(create = true, value = "customerQuestionAction")
	com.oreon.phonestore.web.action.commerce.CustomerQuestionAction customerQuestionsAction;

	@In(create = true, value = "customerOrderAction")
	com.oreon.phonestore.web.action.commerce.CustomerOrderAction customerOrdersAction;

	public void setCustomerId(Long id) {
		setEntityId(id);
	}

	/** for modal dlg we need to load associaitons regardless of postback
	 * @param id
	 */
	public void setCustomerIdForModalDlg(Long id) {
		setEntityIdForModalDlg(id);
	}

	public Long getCustomerId() {
		return (Long) getId();
	}

	public Customer getEntity() {
		return instance;
	}

	//@Override
	public void setEntity(Customer t) {
		this.instance = t;
		loadAssociations();
	}

	public Customer getCustomer() {
		return (Customer) getInstance();
	}

	@Override
	//@Restrict("#{s:hasPermission('customer', 'edit')}")
	public String doSave() {
		return super.doSave();
	}

	@Override
	//@Restrict("#{s:hasPermission('customer', 'delete')}")
	public void archiveById() {
		super.archiveById();
	}

	@Override
	protected Customer createInstance() {
		Customer instance = super.createInstance();

		return instance;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}

	}

	/**
	 * Adds the contained associations that should be available for a newly created object e.g. 
	 * An order should always have at least one order item . Marked in uml with 1..* multiplicity
	 */
	private void addDefaultAssociations() {
		instance = getInstance();

	}

	public void wire() {
		getInstance();

	}

	public boolean isWired() {
		return true;
	}

	public Customer getDefinedInstance() {
		return (Customer) (isIdDefined() ? getInstance() : null);
	}

	public void setCustomer(Customer t) {
		this.instance = t;
		if (getInstance() != null && t != null) {
			setCustomerId(t.getId());
			loadAssociations();
		}
	}

	@Override
	public Class<Customer> getEntityClass() {
		return Customer.class;
	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		initListCustomerQuestions();

		initListCustomerOrders();

		addDefaultAssociations();
	}

	public void updateAssociations() {

	}

	protected List<com.oreon.phonestore.domain.commerce.CustomerQuestion> listCustomerQuestions = new ArrayList<com.oreon.phonestore.domain.commerce.CustomerQuestion>();

	void initListCustomerQuestions() {

		if (listCustomerQuestions.isEmpty())
			listCustomerQuestions.addAll(getInstance().getCustomerQuestions());

	}

	public List<com.oreon.phonestore.domain.commerce.CustomerQuestion> getListCustomerQuestions() {

		prePopulateListCustomerQuestions();
		return listCustomerQuestions;
	}

	public void prePopulateListCustomerQuestions() {
	}

	public void setListCustomerQuestions(
			List<com.oreon.phonestore.domain.commerce.CustomerQuestion> listCustomerQuestions) {
		this.listCustomerQuestions = listCustomerQuestions;
	}

	public void deleteCustomerQuestions(int index) {
		listCustomerQuestions.remove(index);
	}

	@Begin(join = true)
	public void addCustomerQuestions() {

		initListCustomerQuestions();
		CustomerQuestion customerQuestions = new CustomerQuestion();

		customerQuestions.setCustomer(getInstance());

		getListCustomerQuestions().add(customerQuestions);

	}

	protected List<com.oreon.phonestore.domain.commerce.CustomerOrder> listCustomerOrders = new ArrayList<com.oreon.phonestore.domain.commerce.CustomerOrder>();

	void initListCustomerOrders() {

		if (listCustomerOrders.isEmpty())
			listCustomerOrders.addAll(getInstance().getCustomerOrders());

	}

	public List<com.oreon.phonestore.domain.commerce.CustomerOrder> getListCustomerOrders() {

		prePopulateListCustomerOrders();
		return listCustomerOrders;
	}

	public void prePopulateListCustomerOrders() {
	}

	public void setListCustomerOrders(
			List<com.oreon.phonestore.domain.commerce.CustomerOrder> listCustomerOrders) {
		this.listCustomerOrders = listCustomerOrders;
	}

	public void deleteCustomerOrders(int index) {
		listCustomerOrders.remove(index);
	}

	@Begin(join = true)
	public void addCustomerOrders() {

		initListCustomerOrders();
		CustomerOrder customerOrders = new CustomerOrder();

		customerOrders.setCustomer(getInstance());

		getListCustomerOrders().add(customerOrders);

	}

	public void updateComposedAssociations() {

		if (listCustomerQuestions != null) {

			java.util.Set<CustomerQuestion> items = getInstance()
					.getCustomerQuestions();
			for (CustomerQuestion item : items) {
				if (!listCustomerQuestions.contains(item))
					getEntityManager().remove(item);
			}

			getInstance().getCustomerQuestions().clear();
			getInstance().getCustomerQuestions().addAll(listCustomerQuestions);
		}

		if (listCustomerOrders != null) {

			java.util.Set<CustomerOrder> items = getInstance()
					.getCustomerOrders();
			for (CustomerOrder item : items) {
				if (!listCustomerOrders.contains(item))
					getEntityManager().remove(item);
			}

			getInstance().getCustomerOrders().clear();
			getInstance().getCustomerOrders().addAll(listCustomerOrders);
		}
	}

	public void clearLists() {
		listCustomerQuestions.clear();
		listCustomerOrders.clear();

	}

	public String viewCustomer() {
		load(currentEntityId);
		return "viewCustomer";
	}

}
