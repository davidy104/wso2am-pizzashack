package org.pizzashack.ds.test;

import org.pizzashack.data.OrderDTO;

public class PizzashackTestUtils {

	public static OrderDTO createOrderDTOWithExistedCustomer() {
		OrderDTO orderDto = OrderDTO.getBuilder("Spicy Italian", 2, "david",
				"22 marvon downs", "3984938", "david.yuan@yellow.co.nz")
				.build();

		return orderDto;
	}

	public static OrderDTO createOrderDTOWithNewCustomer() {
		OrderDTO orderDto = OrderDTO.getBuilder("Spicy Italian", 1, "John",
				"28 marvon downs", "495849", "John.ni@gmail.com").build();

		return orderDto;
	}
}
