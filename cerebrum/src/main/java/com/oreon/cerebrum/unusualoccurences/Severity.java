package com.oreon.cerebrum.unusualoccurences;

public enum Severity {

	Mild,

	Moderate,

	Critical,

	;

	Severity() {
	}

	public String getName() {
		return this.toString();
	}

	public String getDisplayName() {
		return this.toString();
	}
}
