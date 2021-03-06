package com.oreon.cerebrum.web.action.facility;

import com.oreon.cerebrum.facility.Room;

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
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.annotations.web.RequestParameter;

import org.witchcraft.base.entity.FileAttachment;

import org.apache.commons.io.FileUtils;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import org.witchcraft.seam.action.BaseAction;
import org.witchcraft.base.entity.BaseEntity;

import com.oreon.cerebrum.facility.Bed;

public abstract class RoomActionBase extends BaseAction<Room>
		implements
			java.io.Serializable {

	@RequestParameter
	protected Long roomId;

	@In(create = true, value = "roomTypeAction")
	com.oreon.cerebrum.web.action.facility.RoomTypeAction roomTypeAction;

	@In(create = true, value = "wardAction")
	com.oreon.cerebrum.web.action.facility.WardAction wardAction;

	public void setRoomId(Long id) {
		if (id == 0) {
			clearInstance();
			clearLists();
			loadAssociations();
			return;
		}
		setId(id);
		instance = loadInstance();
		if (!isPostBack())
			loadAssociations();
	}

	/** for modal dlg we need to load associaitons regardless of postback
	 * @param id
	 */
	public void setRoomIdForModalDlg(Long id) {
		setId(id);
		instance = loadInstance();
		clearLists();
		loadAssociations();
	}

	public void setRoomTypeId(Long id) {

		if (id != null && id > 0)
			getInstance().setRoomType(roomTypeAction.loadFromId(id));

	}

	public Long getRoomTypeId() {
		if (getInstance().getRoomType() != null)
			return getInstance().getRoomType().getId();
		return 0L;
	}

	public void setWardId(Long id) {

		if (id != null && id > 0)
			getInstance().setWard(wardAction.loadFromId(id));

	}

	public Long getWardId() {
		if (getInstance().getWard() != null)
			return getInstance().getWard().getId();
		return 0L;
	}

	public Long getRoomId() {
		return (Long) getId();
	}

	public Room getEntity() {
		return instance;
	}

	//@Override
	public void setEntity(Room t) {
		this.instance = t;
		loadAssociations();
	}

	public Room getRoom() {
		return (Room) getInstance();
	}

	@Override
	//@Restrict("#{s:hasPermission('room', 'edit')}")
	public String doSave() {
		return super.doSave();
	}

	@Override
	//@Restrict("#{s:hasPermission('room', 'delete')}")
	public void archiveById() {
		super.archiveById();
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

	/**
	 * Adds the contained associations that should be available for a newly created object e.g. 
	 * An order should always have at least one order item . Marked in uml with 1..* multiplicity
	 */
	private void addDefaultAssociations() {
		instance = getInstance();

	}

	public void wire() {
		getInstance();

		com.oreon.cerebrum.facility.RoomType roomType = roomTypeAction
				.getDefinedInstance();
		if (roomType != null && isNew()) {
			getInstance().setRoomType(roomType);
		}

		com.oreon.cerebrum.facility.Ward ward = wardAction.getDefinedInstance();
		if (ward != null && isNew()) {
			getInstance().setWard(ward);
		}

	}

	public boolean isWired() {
		return true;
	}

	public Room getDefinedInstance() {
		return (Room) (isIdDefined() ? getInstance() : null);
	}

	public void setRoom(Room t) {
		this.instance = t;
		if (getInstance() != null)
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

		if (instance.getRoomType() != null) {
			criteria = criteria.add(Restrictions.eq("roomType.id", instance
					.getRoomType().getId()));
		}

		if (instance.getWard() != null) {
			criteria = criteria.add(Restrictions.eq("ward.id", instance
					.getWard().getId()));
		}

	}

	/** This function is responsible for loading associations for the given entity e.g. when viewing an order, we load the customer so
	 * that customer can be shown on the customer tab within viewOrder.xhtml
	 * @see org.witchcraft.seam.action.BaseAction#loadAssociations()
	 */
	public void loadAssociations() {

		if (getInstance().getRoomType() != null) {
			roomTypeAction.setInstance(getInstance().getRoomType());
			roomTypeAction.loadAssociations();
		}

		if (getInstance().getWard() != null) {
			wardAction.setInstance(getInstance().getWard());
			wardAction.loadAssociations();
		}

		initListBeds();

		addDefaultAssociations();
	}

	public void updateAssociations() {

	}

	protected List<com.oreon.cerebrum.facility.Bed> listBeds = new ArrayList<com.oreon.cerebrum.facility.Bed>();

	void initListBeds() {

		if (listBeds.isEmpty())
			listBeds.addAll(getInstance().getBeds());

	}

	public List<com.oreon.cerebrum.facility.Bed> getListBeds() {

		prePopulateListBeds();
		return listBeds;
	}

	public void prePopulateListBeds() {
	}

	public void setListBeds(List<com.oreon.cerebrum.facility.Bed> listBeds) {
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
