package com.pwc.insuranceclaims.web.action.domain;

import com.pwc.insuranceclaims.domain.Employee;

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

import com.pwc.insuranceclaims.domain.Employee;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class EmployeeListQueryBase extends BaseQuery<Employee, Long> {

	private static final String EJBQL = "select employee from Employee employee";

	protected Employee employee = new Employee();

	public Employee getEmployee() {
		return employee;
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	public Class<Employee> getEntityClass() {
		return Employee.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private Range<Date> user_lastLoginRange = new Range<Date>();
	public Range<Date> getUser_lastLoginRange() {
		return user_lastLoginRange;
	}
	public void setUser_lastLogin(Range<Date> user_lastLoginRange) {
		this.user_lastLoginRange = user_lastLoginRange;
	}

	private static final String[] RESTRICTIONS = {
			"employee.id = #{employeeList.employee.id}",

			"lower(employee.firstName) like concat(lower(#{employeeList.employee.firstName}),'%')",

			"lower(employee.lastName) like concat(lower(#{employeeList.employee.lastName}),'%')",

			"lower(employee.contactDetails.phone) like concat(lower(#{employeeList.employee.contactDetails.phone}),'%')",

			"lower(employee.contactDetails.secondaryPhone) like concat(lower(#{employeeList.employee.contactDetails.secondaryPhone}),'%')",

			"lower(employee.contactDetails.city) like concat(lower(#{employeeList.employee.contactDetails.city}),'%')",

			"employee.department.id = #{employeeList.employee.department.id}",

			"lower(employee.employeeNumber) like concat(lower(#{employeeList.employee.employeeNumber}),'%')",

			"employee.employeeType = #{employeeList.employee.employeeType}",

			"lower(employee.user.userName) like concat(lower(#{employeeList.employee.user.userName}),'%')",

			"employee.user.enabled = #{employeeList.employee.user.enabled}",

			"lower(employee.user.email) like concat(lower(#{employeeList.employee.user.email}),'%')",

			"employee.user.lastLogin >= #{employeeList.user_lastLoginRange.begin}",
			"employee.user.lastLogin <= #{employeeList.user_lastLoginRange.end}",

			"employee.dateCreated <= #{employeeList.dateCreatedRange.end}",
			"employee.dateCreated >= #{employeeList.dateCreatedRange.begin}",};

	public List<Employee> getEmployeesByDepartment(
			com.pwc.insuranceclaims.domain.Department department) {
		//setMaxResults(10000);
		employee.setDepartment(department);
		return getResultList();
	}

	@Observer("archivedEmployee")
	public void onArchive() {
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, Employee e) {

		builder.append("\""
				+ (e.getDepartment() != null ? e.getDepartment()
						.getDisplayName().replace(",", "") : "") + "\",");

		builder.append("\""
				+ (e.getEmployeeNumber() != null ? e.getEmployeeNumber()
						.replace(",", "") : "") + "\",");

		builder.append("\""
				+ (e.getEmployeeType() != null ? e.getEmployeeType() : "")
				+ "\",");

		builder.append("\""
				+ (e.getUser() != null ? e.getUser().getDisplayName().replace(
						",", "") : "") + "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Department" + ",");

		builder.append("EmployeeNumber" + ",");

		builder.append("EmployeeType" + ",");

		builder.append("User" + ",");

		builder.append("\r\n");
	}
}
