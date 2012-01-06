package com.oreon.cerebrum.web.action.billing;

import com.oreon.cerebrum.billing.InvoiceItem;

import org.witchcraft.seam.action.BaseAction;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import org.witchcraft.base.entity.BaseQuery;
import org.witchcraft.base.entity.Range;

import org.jboss.seam.annotations.Observer;

import com.oreon.cerebrum.billing.InvoiceItem;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class InvoiceItemListQueryBase
		extends
			BaseQuery<InvoiceItem, Long> {

	private static final String EJBQL = "select invoiceItem from InvoiceItem invoiceItem";

	protected InvoiceItem invoiceItem = new InvoiceItem();

	public InvoiceItem getInvoiceItem() {
		return invoiceItem;
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	public Class<InvoiceItem> getEntityClass() {
		return InvoiceItem.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private Range<Integer> unitsRange = new Range<Integer>();
	public Range<Integer> getUnitsRange() {
		return unitsRange;
	}
	public void setUnits(Range<Integer> unitsRange) {
		this.unitsRange = unitsRange;
	}

	private static final String[] RESTRICTIONS = {
			"invoiceItem.id = #{invoiceItemList.invoiceItem.id}",

			"invoiceItem.units >= #{invoiceItemList.unitsRange.begin}",
			"invoiceItem.units <= #{invoiceItemList.unitsRange.end}",

			"invoiceItem.service.id = #{invoiceItemList.invoiceItem.service.id}",

			"invoiceItem.invoice.id = #{invoiceItemList.invoiceItem.invoice.id}",

			"invoiceItem.dateCreated <= #{invoiceItemList.dateCreatedRange.end}",
			"invoiceItem.dateCreated >= #{invoiceItemList.dateCreatedRange.begin}",};

	public List<InvoiceItem> getInvoiceItemsByInvoice(
			com.oreon.cerebrum.billing.Invoice invoice) {
		//setMaxResults(10000);
		invoiceItem.setInvoice(invoice);
		return getResultList();
	}

	@Observer("archivedInvoiceItem")
	public void onArchive() {
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, InvoiceItem e) {

		builder.append("\"" + (e.getUnits() != null ? e.getUnits() : "")
				+ "\",");

		builder.append("\""
				+ (e.getService() != null ? e.getService().getDisplayName()
						.replace(",", "") : "") + "\",");

		builder.append("\""
				+ (e.getInvoice() != null ? e.getInvoice().getDisplayName()
						.replace(",", "") : "") + "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Units" + ",");

		builder.append("Service" + ",");

		builder.append("Invoice" + ",");

		builder.append("\r\n");
	}
}