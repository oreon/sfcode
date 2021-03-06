package bizobjects.service.impl;

import bizobjects.PlacedOrder;
import bizobjects.service.PlacedOrderService;
import bizobjects.dao.PlacedOrderDao;
import java.util.List;
import bizobjects.service.PlacedOrderService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.apache.log4j.Logger;

import usermanagement.Authority;
import usermanagement.service.AuthorityService;

import org.witchcraft.model.support.dao.GenericDAO;
import org.witchcraft.model.support.errorhandling.BusinessException;
import org.witchcraft.model.support.service.BaseServiceImpl;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PlacedOrderServiceImplBase extends BaseServiceImpl<PlacedOrder>
		implements
			PlacedOrderService {

	private static final Logger log = Logger
			.getLogger(PlacedOrderServiceImplBase.class);

	private PlacedOrderDao placedOrderDao;

	public void setPlacedOrderDao(PlacedOrderDao placedOrderDao) {
		this.placedOrderDao = placedOrderDao;
	}

	@Override
	public GenericDAO<PlacedOrder> getDao() {
		return placedOrderDao;
	}

	//// Delegate all crud operations to the Dao ////

	public PlacedOrder save(PlacedOrder placedOrder) {
		Long id = placedOrder.getId();

		placedOrderDao.save(placedOrder);

		return placedOrder;
	}

	public void delete(PlacedOrder placedOrder) {
		placedOrderDao.delete(placedOrder);
	}

	public PlacedOrder load(Long id) {
		return placedOrderDao.load(id);
	}

	public List<PlacedOrder> loadAll() {
		return placedOrderDao.loadAll();
	}

	public List<PlacedOrder> searchByExample(PlacedOrder placedOrder) {
		return placedOrderDao.searchByExample(placedOrder);
	}

	/*
	public List query(String queryString, Object... params) {
		return basicDAO.query(queryString, params);
	}*/

}
