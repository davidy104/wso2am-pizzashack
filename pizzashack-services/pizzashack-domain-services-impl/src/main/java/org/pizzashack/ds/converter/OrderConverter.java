package org.pizzashack.ds.converter;

import org.pizzashack.ConvertException;
import org.pizzashack.data.Customer;
import org.pizzashack.data.Order;
import org.pizzashack.data.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("orderConverter")
public class OrderConverter implements GeneralConverter<OrderDTO, Order> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderConverter.class);

	@Override
	public OrderDTO convertFrom(Order model, Object... additionalSourceObj)
			throws ConvertException {
		OrderDTO orderDto = null;
		LOGGER.debug("convert to DTO start:{}", model);
		Customer customer = model.getCustomer();
		Long custId = null;
		String custName = null;
		String address = null;
		String creditCardNumber = null;
		String custEmail = null;

		if (customer != null) {
			custId = customer.getCustId();
			custName = customer.getCustomerName() == null ? "" : customer
					.getCustomerName();
			address = customer.getAddress() == null ? "" : customer
					.getAddress();
			creditCardNumber = customer.getCreditCardNumber() == null ? ""
					: customer.getCreditCardNumber();
			custEmail = customer.getEmail() == null ? "" : customer.getEmail();
		}
		orderDto = OrderDTO.getBuilder(model.getOrderId(),
				model.getPizzaType(), model.getQuantity(), custName, address,
				creditCardNumber, custEmail, model.getDelivered()).build();
		if (custId != null) {
			orderDto.setCustId(custId);
		}
		orderDto.setOrderId(model.getOrderId());
		LOGGER.debug("convert to DTO end:{}", orderDto);
		return orderDto;
	}

	@Override
	public Order convertTo(OrderDTO dto, Object... additionalSourceObj)
			throws ConvertException {
		LOGGER.debug("convert to Model start:{}", dto);
		Customer customer = Customer.getBuilder(dto.getCustomerName(),
				dto.getCreditCardNumber(), dto.getAddress(),
				dto.getCustomerEmail()).build();

		Order order = Order.getBuilder(dto.getPizzaType(), dto.getQuantity(),
				customer, dto.getDelivered()).build();

		LOGGER.debug("convert to Model end:{}", order);
		return order;
	}

}
