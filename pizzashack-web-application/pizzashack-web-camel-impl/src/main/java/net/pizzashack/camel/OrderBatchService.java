package net.pizzashack.camel;

import java.util.List;

import net.pizzashack.data.dto.OrderDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderBatchService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderBatchService.class);

	public List<OrderDto> process(List<OrderDto> orders) throws Exception {
		LOGGER.debug("after aggregation:{}");
		for (OrderDto order : orders) {
			LOGGER.debug("order:{}", order);
			order.setCustomerName("admin");
		}
		return orders;
	}
}
