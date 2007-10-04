package bizobjects.service;

import bizobjects.PlacedOrder;
import org.springframework.test.jpa.AbstractJpaTests;
import java.util.List;

import org.witchcraft.model.support.TestDataFactory;
import org.witchcraft.model.support.springbeanhelpers.BeanHelper;

import java.text.SimpleDateFormat;

import javax.persistence.PersistenceException;
import org.hibernate.PropertyValueException;

public class PlacedOrderDaoTest extends AbstractJpaTests {

	protected PlacedOrder placedOrderInstance = new PlacedOrder();

	protected PlacedOrderService placedOrderService;

	protected boolean bTest = true;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy.MM.dd HH:mm:ss z");

	public void setPlacedOrderService(PlacedOrderService placedOrderService) {
		this.placedOrderService = placedOrderService;
	}

	protected TestDataFactory placedOrderTestDataFactory = (TestDataFactory) BeanHelper
			.getBean("placedOrderTestDataFactory");

	@Override
	protected String[] getConfigLocations() {
		return new String[]{"classpath:/applicationContext.xml",
				"classpath:/testDataFactories.xml"};
	}

	@Override
	protected void runTest() throws Throwable {
		if (!bTest)
			return;
		super.runTest();
	}

	/**
	 * Do the setup before the test in this method
	 **/
	protected void onSetUpInTransaction() throws Exception {
		try {

			placedOrderInstance.setRemarks("gamma");
			placedOrderInstance.setPaymentMethod("theta");
			placedOrderInstance.setStatus(bizobjects.OrderStatus.SHIPPED);

			TestDataFactory customerTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("customerTestDataFactory");

			placedOrderInstance
					.setCustomer((bizobjects.Customer) customerTestDataFactory
							.loadOneRecord());

			TestDataFactory orderItemTestDataFactory = (TestDataFactory) BeanHelper
					.getBean("orderItemTestDataFactory");

			orderItemTestDataFactory.setPersistable(false);
			placedOrderInstance.getOrderItems().addAll(
					orderItemTestDataFactory.createFewRecords());

			placedOrderService.save(placedOrderInstance);
		} catch (PersistenceException pe) {
			//if this instance can't be created due to back references e.g an orderItem needs an Order - 
			// - we will simply skip generated tests.
			if (pe.getCause() instanceof PropertyValueException
					&& pe.getMessage().contains("Backref")) {
				bTest = false;
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	//test saving a new record and updating an existing record;
	public void testSave() {

		try {
			PlacedOrder placedOrder = new PlacedOrder();

			try {

				placedOrder.setRemarks("Lavendar");
				placedOrder.setPaymentMethod("Mark");
				placedOrder.setStatus(bizobjects.OrderStatus.NEW);

				TestDataFactory customerTestDataFactory = (TestDataFactory) BeanHelper
						.getBean("customerTestDataFactory");

				placedOrder
						.setCustomer((bizobjects.Customer) customerTestDataFactory
								.loadOneRecord());

				TestDataFactory orderItemTestDataFactory = (TestDataFactory) BeanHelper
						.getBean("orderItemTestDataFactory");

				orderItemTestDataFactory.setPersistable(false);
				placedOrder.getOrderItems().addAll(
						orderItemTestDataFactory.createFewRecords());

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			placedOrderService.save(placedOrder);
			assertNotNull(placedOrder.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testEdit() {

		try {
			//test saving a new record and updating an existing record;
			PlacedOrder placedOrder = (PlacedOrder) placedOrderTestDataFactory
					.loadOneRecord();

			placedOrder.setRemarks("Mark");
			placedOrder.setPaymentMethod("Wilson");
			placedOrder.setStatus(bizobjects.OrderStatus.COMPLETED);

			placedOrderService.save(placedOrder);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testDelete() {
		//return false;
	}

	public void testLoad() {

		try {
			PlacedOrder placedOrder = placedOrderService
					.load(placedOrderInstance.getId());
			assertNotNull(placedOrder.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testSearchByExample() {
		try {
			List<PlacedOrder> placedOrders = placedOrderService
					.searchByExample(placedOrderInstance);
			assertTrue(!placedOrders.isEmpty());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
