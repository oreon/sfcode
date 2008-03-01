package com.oreon.kgauge.web.jsf;

import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import org.witchcraft.model.jsf.BaseBackingBean;
import org.witchcraft.model.support.service.BaseService;

import com.oreon.kgauge.domain.Section;
import com.oreon.kgauge.service.SectionService;

public class SectionBackingBean extends BaseBackingBean<Section> {

	private Section section = new Section();

	private SectionService sectionService;

	public void setSectionService(SectionService sectionService) {
		this.sectionService = sectionService;
	}

	public Section getSection() {
		return section;
	}

	public void set(Section section) {
		this.section = section;
	}

	@SuppressWarnings("unchecked")
	public BaseService<Section> getBaseService() {
		return sectionService;
	}

	public Section getEntity() {
		return getSection();
	}

	public String search() {
		action = SEARCH;
		return "search";
	}

	/** Returns a success string upon selection of an entity in model - majority of work is done
	 * in the actionListener selectEntity
	 * @return - "success" if everthing goes fine
	 * @see - 
	 */
	public String select() {
		return "edit";
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
		section = sectionService.load(id);
		if (actionEvent.getComponent().getId() == "deleteId") {
			getBaseService().delete(section);

		}

		/*
		UIParameter component = (UIParameter) actionEvent.getComponent().findComponent("editId");
		// parse the value of the UIParameter component    	 
		long id = Long.parseLong(component.getValue().toString());
		 */

	}

}
