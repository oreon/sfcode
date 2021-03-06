package com.nas.recovery.web.action.billing;

import com.oreon.callosum.billing.InvoiceItem;

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

public abstract class InvoiceItemActionBase extends BaseAction<InvoiceItem>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private InvoiceItem invoiceItem;

	@In(create = true, value = "serviceAction")
	com.nas.recovery.web.action.billing.ServiceAction serviceAction;

	@In(create = true, value = "invoiceAction")
	com.nas.recovery.web.action.billing.InvoiceAction invoiceAction;

	@DataModel
	private List<InvoiceItem> invoiceItemRecordList;

	public void setInvoiceItemId(Long id) {
		if (id == 0) {
			clearInstance();
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
	public void setInvoiceItemIdForModalDlg(Long id) {
		setId(id);
		loadAssociations();
	}

	public void setServiceId(Long id) {

		if (id != null && id > 0)
			getInstance().setService(serviceAction.loadFromId(id));

	}

	public Long getServiceId() {
		if (getInstance().getService() != null)
			return getInstance().getService().getId();
		return 0L;
	}

	public void setInvoiceId(Long id) {

		if (id != null && id > 0)
			getInstance().setInvoice(invoiceAction.loadFromId(id));

	}

	public Long getInvoiceId() {
		if (getInstance().getInvoice() != null)
			return getInstance().getInvoice().getId();
		return 0L;
	}

	public Long getInvoiceItemId() {
		return (Long) getId();
	}

	public InvoiceItem getEntity() {
		return invoiceItem;
	}

	//@Override
	public void setEntity(InvoiceItem t) {
		this.invoiceItem = t;
		loadAssociations();
	}

	public InvoiceItem getInvoiceItem() {
		return (InvoiceItem) getInstance();
	}

	@Override
	protected InvoiceItem createInstance() {
		return new InvoiceItem();
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();

		com.oreon.callosum.billing.Service service = serviceAction
				.getDefinedInstance();
		if (service != null) {
			getInstance().setService(service);
		}

		com.oreon.callosum.billing.Invoice invoice = invoiceAction
				.getDefinedInstance();
		if (invoice != null) {
			getInstance().setInvoice(invoice);
		}

	}

	public boolean isWired() {
		return true;
	}

	public InvoiceItem getDefinedInstance() {
		return (InvoiceItem) (isIdDefined() ? getInstance() : null);
	}

	public void setInvoiceItem(InvoiceItem t) {
		this.invoiceItem = t;
		loadAssociations();
	}

	@Override
	public Class<InvoiceItem> getEntityClass() {
		return InvoiceItem.class;
	}

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseAction#createExampleCriteria(java.lang.Object)
	 */
	@Override
	public void addAssociations(Criteria criteria) {

		if (invoiceItem.getService() != null) {
			criteria = criteria.add(Restrictions.eq("service.id", invoiceItem
					.getService().getId()));
		}

		if (invoiceItem.getInvoice() != null) {
			criteria = criteria.add(Restrictions.eq("invoice.id", invoiceItem
					.getInvoice().getId()));
		}

	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		if (invoiceItem.getService() != null) {
			serviceAction.setInstance(getInstance().getService());
		}

		if (invoiceItem.getInvoice() != null) {
			invoiceAction.setInstance(getInstance().getInvoice());
		}

	}

	public void updateAssociations() {

	}

	public void updateComposedAssociations() {
	}

}
