package org.pizzashack.data.predicates;

import org.apache.commons.lang3.StringUtils;
import org.pizzashack.data.QOrder;

import com.mysema.query.types.Predicate;

public class OrderPredicates {

	public static Predicate findByCustomer(final String customerName,
			final String email) {
		QOrder order = QOrder.order;
		return (order.customer.customerName.eq(customerName))
				.and(order.customer.email.eq(email));
	}

	public static Predicate findDeliveredByCustomer(final String customerName,
			final String email, final String delivered) {
		QOrder order = QOrder.order;
		if (StringUtils.isEmpty(delivered)) {
			return findByCustomer(customerName, email);
		} else {
			return order.delivered.eq(delivered).and(
					findByCustomer(customerName, email));
		}
	}
}
