
package com.wc.jshopper.domain.action;



import java.util.Arrays;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import org.witchcraft.base.entity.BaseQuery;
import org.witchcraft.base.entity.Range;
	
	
@Name("productList")
@Scope(ScopeType.CONVERSATION)
public class ProductListQuery extends ProductListQueryBase implements java.io.Serializable{
	
}
