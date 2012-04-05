package com.hrb.tservices.domain.department;

public enum EmployeeType {

	FULL_TIME,

	CONTRACT,

	PART_TIME,

	;

	EmployeeType() {
	}

	public String getName() {
		return this.toString();
	}

	public String getDisplayName() {
		return this.toString();
	}
}