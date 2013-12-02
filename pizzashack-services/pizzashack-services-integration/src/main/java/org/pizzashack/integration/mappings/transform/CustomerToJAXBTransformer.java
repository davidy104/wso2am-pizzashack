package org.pizzashack.integration.mappings.transform;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Message;
import org.pizzashack.data.OrderDTO;
import org.pizzashack.integration.mappings.jaxb.CustomerJaxbModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerToJAXBTransformer implements Expression {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerToJAXBTransformer.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T evaluate(Exchange exchange, Class<T> type) {
		LOGGER.debug("transform to customerJAXB start:{}");
		Message message = exchange.getIn();

		OrderDTO orderDto = (OrderDTO) message.getBody();
		LOGGER.debug("get order:{}", orderDto);

		CustomerJaxbModel customerJaxbModel = CustomerJaxbModel.getBuilder(
				orderDto.getCustomerName(), orderDto.getAddress(),
				orderDto.getCustomerName()).build();
		LOGGER.debug("transform to customerJAXB end:{}", customerJaxbModel);
		return (T) customerJaxbModel;
	}

}
