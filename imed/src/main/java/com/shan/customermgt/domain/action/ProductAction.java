package com.shan.customermgt.domain.action;

import com.shan.customermgt.domain.Product;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import org.apache.commons.lang.StringUtils;

import org.jboss.seam.ScopeType;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import org.witchcraft.seam.action.BaseAction;
import org.jboss.seam.annotations.Observer;

@Scope(ScopeType.CONVERSATION)
@Name("productAction")
public class ProductAction extends BaseAction<Product>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private Product product;

	@DataModel
	private List<Product> productList;

	@Factory("productList")
	@Observer("archivedProduct")
	public void findRecords() {
		search();
	}

	public Product getEntity() {
		return product;
	}

	@Override
	public void setEntity(Product t) {
		this.product = t;
	}

	@Override
	public void setEntityList(List<Product> list) {
		this.productList = list;
	}

	public void updateAssociations() {

	}

	public List<Product> getEntityList() {
		if (productList == null) {
			findRecords();
		}
		return productList;
	}

}
