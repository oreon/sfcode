package com.oreon.cerebrum.web.action.patient;

import com.oreon.cerebrum.patient.Document;

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

import java.math.BigDecimal;



/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class DocumentListQueryBase extends BaseQuery<Document, Long> {

	private static final String EJBQL = "select document from Document document";

	protected Document document = new Document();

	public Document getDocument() {
		return document;
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	public Class<Document> getEntityClass() {
		return Document.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"document.id = #{documentList.document.id}",

			"lower(document.name) like concat(lower(#{documentList.document.name}),'%')",

			"lower(document.notes) like concat(lower(#{documentList.document.notes}),'%')",

			"document.patient.id = #{documentList.document.patient.id}",

			"document.dateCreated <= #{documentList.dateCreatedRange.end}",
			"document.dateCreated >= #{documentList.dateCreatedRange.begin}",};

	public List<Document> getDocumentsByPatient(
			com.oreon.cerebrum.patient.Patient patient) {
		//setMaxResults(10000);
		document.setPatient(patient);
		return getResultList();
	}

	@Observer("archivedDocument")
	public void onArchive() {
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, Document e) {

		builder.append("\""
				+ (e.getName() != null ? e.getName().replace(",", "") : "")
				+ "\",");

		builder.append("\""
				+ (e.getNotes() != null ? e.getNotes().replace(",", "") : "")
				+ "\",");

		builder.append("\""
				+ (e.getPatient() != null ? e.getPatient().getDisplayName()
						.replace(",", "") : "") + "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Name" + ",");

		builder.append("Notes" + ",");

		builder.append("Patient" + ",");

		builder.append("\r\n");
	}
}
