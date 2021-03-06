package com.oreon.smartsis.web.action.fees;

import com.oreon.smartsis.fees.StudentPaidFee;

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

import com.oreon.smartsis.fees.StudentPaidFee;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class StudentPaidFeeListQueryBase
		extends
			BaseQuery<StudentPaidFee, Long> {

	private static final String EJBQL = "select studentPaidFee from StudentPaidFee studentPaidFee";

	protected StudentPaidFee studentPaidFee = new StudentPaidFee();

	public StudentPaidFee getStudentPaidFee() {
		return studentPaidFee;
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	public Class<StudentPaidFee> getEntityClass() {
		return StudentPaidFee.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private Range<Double> amountPaidRange = new Range<Double>();
	public Range<Double> getAmountPaidRange() {
		return amountPaidRange;
	}
	public void setAmountPaid(Range<Double> amountPaidRange) {
		this.amountPaidRange = amountPaidRange;
	}

	private Range<Double> dueAmountRange = new Range<Double>();
	public Range<Double> getDueAmountRange() {
		return dueAmountRange;
	}
	public void setDueAmount(Range<Double> dueAmountRange) {
		this.dueAmountRange = dueAmountRange;
	}

	private Range<Date> dateOfPaymentRange = new Range<Date>();
	public Range<Date> getDateOfPaymentRange() {
		return dateOfPaymentRange;
	}
	public void setDateOfPayment(Range<Date> dateOfPaymentRange) {
		this.dateOfPaymentRange = dateOfPaymentRange;
	}

	private Range<Integer> yearRange = new Range<Integer>();
	public Range<Integer> getYearRange() {
		return yearRange;
	}
	public void setYear(Range<Integer> yearRange) {
		this.yearRange = yearRange;
	}

	private static final String[] RESTRICTIONS = {
			"studentPaidFee.id = #{studentPaidFeeList.studentPaidFee.id}",

			"studentPaidFee.amountPaid >= #{studentPaidFeeList.amountPaidRange.begin}",
			"studentPaidFee.amountPaid <= #{studentPaidFeeList.amountPaidRange.end}",

			"studentPaidFee.dueAmount >= #{studentPaidFeeList.dueAmountRange.begin}",
			"studentPaidFee.dueAmount <= #{studentPaidFeeList.dueAmountRange.end}",

			"studentPaidFee.dateOfPayment >= #{studentPaidFeeList.dateOfPaymentRange.begin}",
			"studentPaidFee.dateOfPayment <= #{studentPaidFeeList.dateOfPaymentRange.end}",

			"studentPaidFee.student.id = #{studentPaidFeeList.studentPaidFee.student.id}",

			"studentPaidFee.monthlyFee.id = #{studentPaidFeeList.studentPaidFee.monthlyFee.id}",

			"studentPaidFee.year >= #{studentPaidFeeList.yearRange.begin}",
			"studentPaidFee.year <= #{studentPaidFeeList.yearRange.end}",

			"studentPaidFee.month = #{studentPaidFeeList.studentPaidFee.month}",

			"studentPaidFee.dateCreated <= #{studentPaidFeeList.dateCreatedRange.end}",
			"studentPaidFee.dateCreated >= #{studentPaidFeeList.dateCreatedRange.begin}",};

	@Observer("archivedStudentPaidFee")
	public void onArchive() {
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, StudentPaidFee e) {

		builder.append("\""
				+ (e.getAmountPaid() != null ? e.getAmountPaid() : "") + "\",");

		builder.append("\""
				+ (e.getDueAmount() != null ? e.getDueAmount() : "") + "\",");

		builder.append("\""
				+ (e.getDateOfPayment() != null ? e.getDateOfPayment() : "")
				+ "\",");

		builder.append("\""
				+ (e.getStudent() != null ? e.getStudent().getDisplayName()
						.replace(",", "") : "") + "\",");

		builder.append("\""
				+ (e.getMonthlyFee() != null ? e.getMonthlyFee()
						.getDisplayName().replace(",", "") : "") + "\",");

		builder.append("\"" + (e.getYear() != null ? e.getYear() : "") + "\",");

		builder.append("\"" + (e.getMonth() != null ? e.getMonth() : "")
				+ "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("AmountPaid" + ",");

		builder.append("DueAmount" + ",");

		builder.append("DateOfPayment" + ",");

		builder.append("Student" + ",");

		builder.append("MonthlyFee" + ",");

		builder.append("Year" + ",");

		builder.append("Month" + ",");

		builder.append("\r\n");
	}
}
