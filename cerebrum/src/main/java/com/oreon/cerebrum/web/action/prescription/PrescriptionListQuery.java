
package com.oreon.cerebrum.web.action.prescription;



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
import org.jboss.seam.annotations.security.Restrict;

import java.math.BigDecimal;
	
	
@Name("prescriptionList")
//@Scope(ScopeType.CONVERSATION)
//@Restrict("#{s:hasRole('Physician')}")

public class PrescriptionListQuery extends PrescriptionListQueryBase implements java.io.Serializable{
	
}
