package com.oreon.kgauge.web.jsf;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import org.witchcraft.model.jsf.BaseBackingBean;
import org.witchcraft.model.support.service.BaseService;

import com.oreon.kgauge.domain.Category;
import com.oreon.kgauge.service.CategoryService;

import java.util.Date;
import java.util.List;
import org.witchcraft.model.support.Range;

import java.util.Collection;
import org.richfaces.component.UITree;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.richfaces.component.html.HtmlMenuItem;

/**
 * This is generated code - to edit code or override methods use - Category class
 * @author witchCraft Code Generator
 * WARNING  - DO NOT EDIT - CHANGES WILL BE OVERWRITTEN
 */

public class CategoryBackingBeanBase extends BaseBackingBean<Category> {

	protected Category category = new Category();

	protected CategoryService categoryService;

	protected TreeNode root = null;

	private Range<Date> rangeCreationDate = new Range<Date>("dateCreated");

	public Range<Date> getRangeCreationDate() {
		return rangeCreationDate;
	}

	public void setRangeCreationDate(Range<Date> rangeCreationDate) {
		this.rangeCreationDate = rangeCreationDate;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public Category getCategory() {
		return category;
	}

	public void set(Category category) {
		this.category = category;
	}

	@SuppressWarnings("unchecked")
	public BaseService<Category> getBaseService() {
		return categoryService;
	}

	public Category getEntity() {
		return getCategory();
	}

	@Override
	protected List<Range> getRangeList() {

		List<Range> listRanges = super.getRangeList();

		listRanges.add(rangeCreationDate);
		return listRanges;
	}

	/** This action Listener Method is called when a row is clicked in the dataTable
	 *  
	 * @param event contains the database id of the row being selected 
	 */
	public void selectEntity(ActionEvent actionEvent) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		String idStr = (String) ctx.getExternalContext()
				.getRequestParameterMap().get("id");
		long id = Long.parseLong(idStr);
		category = categoryService.load(id);
		if (actionEvent.getComponent().getId() == "deleteId") {
			getBaseService().delete(category);
		}
		/*
		UIParameter component = (UIParameter) actionEvent.getComponent().findComponent("editId");
		// parse the value of the UIParameter component    	 
		long id = Long.parseLong(component.getValue().toString());
		 */
	}

	public TreeNode getTree() {
		if (root == null) {
			root = new TreeNodeImpl();
			Collection<Category> categorys = categoryService
					.findTopLevelElements();
			addChildren(categorys, root);
			return root;
		}
		return root;
	}

	private void addChildren(Collection<Category> categorys,
			TreeNode currentNode) {

		for (Category currentEntity : categorys) {

			TreeNodeImpl nodeImpl = new TreeNodeImpl();

			nodeImpl.setData(currentEntity.getDisplayName());
			currentNode.addChild(currentEntity, nodeImpl);

			Collection<Category> childEntites = currentEntity
					.getSubcategories();
			addChildren(childEntites, nodeImpl);
		}
	}

	public void processSelection(NodeSelectedEvent event) {
		UITree tree = (UITree) event.getComponent();
		//nodeTitle = (String) tree.getRowData();
	}

}