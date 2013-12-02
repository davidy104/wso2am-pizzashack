package org.pizzashack.data.support;

import org.pizzashack.data.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerBuilder extends EntityBuilder<Customer> {
	@Override
	void initProduct() {
	}

	public CustomerBuilder create(String customerName, String creditCardNumber,
			String address, String email) {
		product = Customer.getBuilder(customerName, creditCardNumber, address,
				email).build();
		return this;
	}

	@Override
	Customer assembleProduct() {
		return this.product;
	}
}
