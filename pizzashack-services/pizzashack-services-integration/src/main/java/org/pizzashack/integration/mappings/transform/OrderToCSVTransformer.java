package org.pizzashack.integration.mappings.transform;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Message;
import org.pizzashack.data.OrderDTO;
import org.pizzashack.integration.mappings.csv.simple.CustomerCSVModel;
import org.pizzashack.integration.mappings.csv.simple.OrderCSVModel;
import org.pizzashack.util.PizzashackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderToCSVTransformer implements Expression {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderToCSVTransformer.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T evaluate(Exchange exchange, Class<T> type) {
		LOGGER.debug("transform to orderCSV start:{}");
		Message message = exchange.getIn();

		OrderDTO orderDto = (OrderDTO) message.getBody();
		LOGGER.debug("get order:{}", orderDto);

		CustomerCSVModel customerCSVModel = CustomerCSVModel.getBuilder(
				orderDto.getCustomerName(), orderDto.getCreditCardNumber(),
				orderDto.getAddress(), orderDto.getCustomerEmail()).build();
		customerCSVModel.setCustId(orderDto.getCustId());

		OrderCSVModel orderCSVModel = OrderCSVModel.getBuilder(
				orderDto.getPizzaType(),
				orderDto.getQuantity(),
				orderDto.getDelivered(),
				PizzashackUtils.strToDateIgnoreException(orderDto
						.getOrderTime())).build();
		orderCSVModel.setOrderId(orderDto.getOrderId());
		customerCSVModel.addOrder(orderCSVModel);

		return (T) customerCSVModel;
	}

}
