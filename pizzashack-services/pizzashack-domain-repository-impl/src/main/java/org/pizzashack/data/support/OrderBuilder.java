package org.pizzashack.data.support;

import org.pizzashack.data.Customer;
import org.pizzashack.data.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderBuilder extends EntityBuilder<Order> {

	@Override
	void initProduct() {
	}

	public OrderBuilder create(String pizzaType, int quantity,
			Customer customer, String delivered) {
		this.product = Order.getBuilder(pizzaType, quantity, customer,
				delivered).build();
		return this;
	}

	@Override
	Order assembleProduct() {
		return this.product;
	}

}
