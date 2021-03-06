package com.nas.recovery.domain.legal;

public enum LegalProcessType {

	ASSIGNMENT_JUDGEMENT,

	DEMAND_LETTER,

	EVICTION_LETTER,

	JUDGEMENT,

	NOTICE_OF_SALE,

	RIGHT_TO_SELL,

	STATEMENT_OF_CLAIM,

	STATEMENT_OF_DEFENCE,

	TENANCY_ATTORNMENT_NOTICE,

	TENANCY_EVICTION_ORDER,

	TENANCY_LTB_DEARING_DATE,

	TENANCY_N4_ISSUED,

	WRIT_POSSESSION,

	WRIT_SEIZURE_AND_SALE,

	OTHER,

	;

	LegalProcessType() {
	}

	public String getName() {
		return this.toString();
	}
}
