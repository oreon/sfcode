package com.oreon.cerebrum.web.action.codes;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.oreon.cerebrum.codes.SimpleCode;

@Name("simpleCodeList")
@Scope(ScopeType.CONVERSATION)
public class SimpleCodeListQuery extends SimpleCodeListQueryBase
		implements
			java.io.Serializable {
	
	
	 public List<SimpleCode> searchDataBaseForAutocomplete(String qry) {
		 getSimpleCode().setName(qry);
		 return getResultList();
	 }

}