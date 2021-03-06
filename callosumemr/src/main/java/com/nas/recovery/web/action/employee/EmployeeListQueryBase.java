package com.nas.recovery.web.action.employee;

import com.oreon.callosum.employee.Employee;

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

import com.oreon.callosum.employee.Employee;

/**
 * D
 * @author WitchcraftMDA Seam Cartridge
 *
 */
public abstract class EmployeeListQueryBase extends BaseQuery<Employee, Long> {

	private static final String EJBQL = "select employee from Employee employee";

	protected Employee employee = new com.oreon.callosum.employee.Physician();

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

	private Range<Date> dateOfBirthRange = new Range<Date>();
	public Range<Date> getDateOfBirthRange() {
		return dateOfBirthRange;
	}
	public void setDateOfBirth(Range<Date> dateOfBirthRange) {
		this.dateOfBirthRange = dateOfBirthRange;
	}

	private Range<Integer> ageRange = new Range<Integer>();
	public Range<Integer> getAgeRange() {
		return ageRange;
	}
	public void setAge(Range<Integer> ageRange) {
		this.ageRange = ageRange;
	}

	private static final String[] RESTRICTIONS = {
			"employee.id = #{employeeList.employee.id}",

			"lower(employee.firstName) like concat(lower(#{employeeList.employee.firstName}),'%')",

			"lower(employee.lastName) like concat(lower(#{employeeList.employee.lastName}),'%')",

			"employee.dateOfBirth >= #{employeeList.dateOfBirthRange.begin}",
			"employee.dateOfBirth <= #{employeeList.dateOfBirthRange.end}",

			"employee.gender = #{employeeList.employee.gender}",

			"lower(employee.contactDetails.primaryPhone) like concat(lower(#{employeeList.employee.contactDetails.primaryPhone}),'%')",

			"lower(employee.contactDetails.secondaryPhone) like concat(lower(#{employeeList.employee.contactDetails.secondaryPhone}),'%')",

			"lower(employee.contactDetails.email) like concat(lower(#{employeeList.employee.contactDetails.email}),'%')",

			"employee.age >= #{employeeList.ageRange.begin}",
			"employee.age <= #{employeeList.ageRange.end}",

			"lower(employee.employeeNumber) like concat(lower(#{employeeList.employee.employeeNumber}),'%')",

			"lower(employee.user.userName) like concat(lower(#{employeeList.employee.user.userName}),'%')",

			"employee.user.enabled = #{employeeList.employee.user.enabled}",

			"employee.dateCreated <= #{employeeList.dateCreatedRange.end}",
			"employee.dateCreated >= #{employeeList.dateCreatedRange.begin}",};

	@Observer("archivedEmployee")
	public void onArchive() {
		refresh();
	}

}
