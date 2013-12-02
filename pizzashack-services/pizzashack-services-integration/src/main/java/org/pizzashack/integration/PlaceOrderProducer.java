package org.pizzashack.integration;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.pizzashack.data.OrderDTO;
import org.pizzashack.integration.route.OrderRoute;
import org.pizzashack.integration.support.CamelProducerSupport;
import org.pizzashack.integration.support.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaceOrderProducer {
	@EndpointInject(uri = OrderRoute.PLACEORDER_ENDPOINT)
	private Endpoint endpoint;

	@Autowired
	private CamelProducerSupport camelProducerSupport;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlaceOrderProducer.class);

	public void sendOrder(OrderDTO orderDto, boolean sendCustRequired)
			throws Exception {
		LOGGER.debug("sendOrder start:{}");
		LOGGER.debug("orderDto:{}", orderDto);
		LOGGER.debug("sendCustRequired:{}", sendCustRequired);
		Payload payload = Payload.getBuilder(orderDto).build();
		payload.addHeader(OrderRoute.CUSTOMER, orderDto.getCustomerEmail());
		payload.addProperty(OrderRoute.SENDCUST_REQUIRED, sendCustRequired);
		camelProducerSupport.processOneWay(endpoint, payload);
		LOGGER.debug("sendOrder end:{}");
	}
}
