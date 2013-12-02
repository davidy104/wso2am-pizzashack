package org.pizzashack.ds.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pizzashack.data.OrderDTO;
import org.pizzashack.ds.OrderManagerDS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestAppConfig.class})
@Ignore("not run all the time when activemq not started")
public class OrderManagerDSTest {

//	private static String CUSTOMER_NAME = "david";
//
//	private static String EMAIL = "david.yuan@yellow.co.nz";
//
//	private static String DELIVERED = "Y";
//
//	private static String NO_DELIVERED = "N";
//
//	@Autowired
//	private OrderManagerDS orderManagerDs;
//
//	private static final Logger LOGGER = LoggerFactory
//			.getLogger(OrderManagerDSTest.class);
//
//	@Test
//	public void testGetById() throws Exception {
//		OrderDTO orderDto = orderManagerDs.getOrderById(1L);
//		assertNotNull(orderDto);
//		LOGGER.debug("order:{}", orderDto);
//	}
//
//	@Test
//	public void testGetByCustomer() throws Exception {
//		List<OrderDTO> resultList = orderManagerDs.getOrdersByCustomer(
//				CUSTOMER_NAME, EMAIL, NO_DELIVERED);
//		assertNotNull(resultList);
//		for (OrderDTO order : resultList) {
//			LOGGER.debug("get order:{}", order);
//		}
//	}
//
//	@Test
//	public void testPlaceOrderForNewCustomer() throws Exception {
//		OrderDTO orderDto = PizzashackTestUtils.createOrderDTOWithNewCustomer();
//		orderDto = orderManagerDs.placeOrder(orderDto);
//		assertNotNull(orderDto);
//		List<OrderDTO> resultList = orderManagerDs.getOrdersByCustomer("John",
//				"John.ni@gmail.com", NO_DELIVERED);
//		assertNotNull(resultList);
//		assertEquals(resultList.size(), 1);
//		for (OrderDTO order : resultList) {
//			LOGGER.debug("get order:{}", order);
//		}
//	}
//
//	@Test
//	public void testPlaceOrder() throws Exception {
//		OrderDTO orderDto = PizzashackTestUtils
//				.createOrderDTOWithExistedCustomer();
//		orderDto = orderManagerDs.placeOrder(orderDto);
//		assertNotNull(orderDto);
//		List<OrderDTO> resultList = orderManagerDs.getOrdersByCustomer(
//				CUSTOMER_NAME, EMAIL, NO_DELIVERED);
//		assertNotNull(resultList);
//		for (OrderDTO order : resultList) {
//			LOGGER.debug("get order:{}", order);
//		}
//	}

}
