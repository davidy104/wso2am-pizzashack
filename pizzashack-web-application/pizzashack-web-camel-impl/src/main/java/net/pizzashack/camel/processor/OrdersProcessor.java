package net.pizzashack.camel.processor;

import java.util.List;

import net.pizzashack.data.dto.OrderDto;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrdersProcessor implements Processor {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrdersProcessor.class);

	@SuppressWarnings({"unchecked"})
	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.debug("OrdersProcessor start:{}");
		List<OrderDto> orderDtoList = (List<OrderDto>) exchange.getOut()
				.getBody();
	}

}
