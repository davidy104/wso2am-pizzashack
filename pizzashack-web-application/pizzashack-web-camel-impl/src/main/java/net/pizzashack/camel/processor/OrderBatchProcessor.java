package net.pizzashack.camel.processor;

import net.pizzashack.data.dto.OrderDto;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderBatchProcessor implements Processor {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderBatchProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.debug("OrdersProcessor start:{}");
		OrderDto order = (OrderDto) exchange.getIn().getBody();
		order.setDelivered("Y");
		LOGGER.debug("OrdersProcessor end:{}", order);
	}

}
