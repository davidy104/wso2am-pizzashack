package org.pizzashack.data.predicates;

import org.pizzashack.data.QCustomer;

import com.mysema.query.types.Predicate;

public class CustomerPredicates {
	public static Predicate findByNameAndEmail(final String customerName,
			final String email) {
		QCustomer customer = QCustomer.customer;
		return (customer.customerName.eq(customerName)).and(customer.email
				.eq(email));
	}
}
