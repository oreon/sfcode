
package com.nas.recovery.web.action.issues;



import java.util.Arrays;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import org.witchcraft.base.entity.BaseQuery;
import org.witchcraft.base.entity.Range;

import org.jboss.seam.annotations.Observer;
	
	
@Name("issueList")
@Scope(ScopeType.CONVERSATION)
public class IssueListQuery extends IssueListQueryBase implements java.io.Serializable{
	
}