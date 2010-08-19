package com.nas.recovery.domain.propertymanagement;

public enum Occupancy {

	Owner,

	Tenanted,

	Unconfirmed,

	;

	Occupancy() {
	}

	public String getName() {
		return this.toString();
	}
}
