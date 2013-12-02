package net.pizzashack.camel;

import java.util.ArrayList;
import java.util.List;

import net.pizzashack.data.dto.OrderDto;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderBatchAggregationStrategy implements AggregationStrategy {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderBatchAggregationStrategy.class);

	@SuppressWarnings("unchecked")
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		OrderDto currentOrder = null;
		// OrderDto previousOrder = null;
		if (oldExchange == null) {
			currentOrder = (OrderDto) newExchange.getIn().getBody();
			LOGGER.debug("current order:{}", currentOrder);
			List<OrderDto> processedList = new ArrayList<OrderDto>();
			processedList.add(currentOrder);
			newExchange.getIn().setBody(processedList);
			return newExchange;
		}

		List<OrderDto> processedList = (List<OrderDto>) oldExchange.getIn()
				.getBody();
		LOGGER.debug("processed order count:{}", processedList.size());

		currentOrder = (OrderDto) newExchange.getIn().getBody();
		LOGGER.debug("current order:{}", currentOrder);
		processedList.add(currentOrder);
		newExchange.getIn().setBody(processedList);

		return newExchange;
	}

}
