package com.oreon.smartsis.web.action.hostel;

import com.oreon.smartsis.hostel.Room;

import org.witchcraft.seam.action.BaseAction;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import org.apache.commons.lang.StringUtils;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.Component;
import org.jboss.seam.security.Identity;

import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.annotations.Observer;

import org.witchcraft.base.entity.FileAttachment;

import org.apache.commons.io.FileUtils;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import com.oreon.smartsis.hostel.Bed;

public abstract class RoomActionBase extends BaseAction<Room>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private Room room;

	@In(create = true, value = "hostelAction")
	com.oreon.smartsis.web.action.hostel.HostelAction hostelAction;

	@DataModel
	private List<Room> roomRecordList;

	public void setRoomId(Long id) {
		if (id == 0) {
			clearInstance();
			clearLists();
			loadAssociations();
			return;
		}
		setId(id);
		if (!isPostBack())
			loadAssociations();
	}

	/** for modal dlg we need to load associaitons regardless of postback
	 * @param id
	 */
	public void setRoomIdForModalDlg(Long id) {
		setId(id);
		clearLists();
		loadAssociations();
	}

	public void setHostelId(Long id) {

		if (id != null && id > 0)
			getInstance().setHostel(hostelAction.loadFromId(id));

	}

	public Long getHostelId() {
		if (getInstance().getHostel() != null)
			return getInstance().getHostel().getId();
		return 0L;
	}

	public Long getRoomId() {
		return (Long) getId();
	}

	public Room getEntity() {
		return room;
	}

	//@Override
	public void setEntity(Room t) {
		this.room = t;
		loadAssociations();
	}

	public Room getRoom() {
		return (Room) getInstance();
	}

	@Override
	protected Room createInstance() {
		Room instance = super.createInstance();

		return instance;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();

		com.oreon.smartsis.hostel.Hostel hostel = hostelAction
				.getDefinedInstance();
		if (hostel != null && isNew()) {
			getInstance().setHostel(hostel);
		}

	}

	public boolean isWired() {
		return true;
	}

	public Room getDefinedInstance() {
		return (Room) (isIdDefined() ? getInstance() : null);
	}

	public void setRoom(Room t) {
		this.room = t;
		if (room != null)
			setRoomId(t.getId());
		loadAssociations();
	}

	@Override
	public Class<Room> getEntityClass() {
		return Room.class;
	}

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseAction#createExampleCriteria(java.lang.Object)
	 */
	@Override
	public void addAssociations(Criteria criteria) {

		if (room.getHostel() != null) {
			criteria = criteria.add(Restrictions.eq("hostel.id", room
					.getHostel().getId()));
		}

	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		if (room.getHostel() != null) {
			hostelAction.setInstance(getInstance().getHostel());
		}

		initListBeds();

	}

	public void updateAssociations() {

	}

	protected List<com.oreon.smartsis.hostel.Bed> listBeds = new ArrayList<com.oreon.smartsis.hostel.Bed>();

	void initListBeds() {

		if (listBeds.isEmpty())
			listBeds.addAll(getInstance().getBeds());

	}

	public List<com.oreon.smartsis.hostel.Bed> getListBeds() {

		prePopulateListBeds();
		return listBeds;
	}

	public void prePopulateListBeds() {
	}

	public void setListBeds(List<com.oreon.smartsis.hostel.Bed> listBeds) {
		this.listBeds = listBeds;
	}

	public void deleteBeds(int index) {
		listBeds.remove(index);
	}

	@Begin(join = true)
	public void addBeds() {
		initListBeds();
		Bed beds = new Bed();

		beds.setRoom(getInstance());

		getListBeds().add(beds);
	}

	public void updateComposedAssociations() {

		if (listBeds != null) {
			getInstance().getBeds().clear();
			getInstance().getBeds().addAll(listBeds);
		}
	}

	public void clearLists() {
		listBeds.clear();

	}

	public String viewRoom() {
		load(currentEntityId);
		return "viewRoom";
	}

}
