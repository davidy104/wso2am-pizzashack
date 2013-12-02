package net.pizzashack.camel.ds.impl;

import java.util.List;

import net.pizzashack.camel.OrderProducer;
import net.pizzashack.camel.route.OrderBatchRoute;
import net.pizzashack.camel.route.OrderRoute;
import net.pizzashack.camel.support.CamelProducerSupport;
import net.pizzashack.camel.support.CamelRouteCallback;
import net.pizzashack.camel.support.Payload;
import net.pizzashack.data.dto.OrderDto;
import net.pizzashack.ds.NotFoundException;
import net.pizzashack.ds.OrderDS;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderDs")
public class OrderDSImpl implements OrderDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderDSImpl.class);

	@Autowired
	private OrderProducer orderProducer;

	@Autowired
	private CamelProducerSupport camelProducerSupport;

	@EndpointInject(uri = OrderBatchRoute.ENDPOINT_URI)
	private Endpoint endpoint;

	@SuppressWarnings("unchecked")
	@Override
	public OrderDto deliverOrder(Long orderId, String token) throws Exception {
		LOGGER.debug("deliverOrder start:{}", orderId);
		LOGGER.debug("token:{}", token);
		// OrderDto result = null;
		// Payload payload = new Payload();
		// payload.addProperty(OrderRoute.PROCESS_TYPE, "deliverOrder");
		// payload.addHeader("token", token);
		// payload.addHeader("orderId", orderId);
		// payload.addHeader("deliverStatus", "Y");
		// result = (OrderDto) orderProducer.produce(payload);
		// LOGGER.debug("deliverOrder end:{}", result);

		Payload payload = new Payload();
		payload.addHeader("token", token);
		payload.addHeader("customerEmail", "david.yuan@yellow.co.nz");
		payload.addHeader("customerName", "david");

		List<OrderDto> resultList = (List<OrderDto>) camelProducerSupport
				.processTwoWay(endpoint, payload, new CamelRouteCallback() {
					@Override
					public Object postProcess(Exchange exchange)
							throws Exception {
						return exchange.getIn().getBody();
					}
				});

		LOGGER.debug("print order outside***********************:{}");

		for (OrderDto orderDto : resultList) {
			LOGGER.debug("orderDto:{}", orderDto);
		}

		return null;
	}

	@Override
	public OrderDto placeOrder(OrderDto orderDto, String token)
			throws Exception {
		LOGGER.debug("placeOrder start:{}", orderDto, token);
		OrderDto result = null;
		Payload payload = Payload.getBuilder(orderDto).build();
		payload.addProperty(OrderRoute.PROCESS_TYPE, "placeOrder");
		payload.addHeader("token", token);
		result = (OrderDto) orderProducer.produce(payload);
		LOGGER.debug("placeOrder end: {}", result);
		return result;
	}

	@Override
	public OrderDto getOrder(Long orderId, String token)
			throws NotFoundException {
		LOGGER.debug("getOrder start:{}", orderId);
		OrderDto result = null;
		Payload payload = new Payload();
		payload.addProperty(OrderRoute.PROCESS_TYPE, "getOrder");
		payload.addHeader("token", token);
		payload.addHeader("orderId", orderId);
		try {
			result = (OrderDto) orderProducer.produce(payload);
		} catch (Exception e) {
			throw new NotFoundException("order can not be found with id["
					+ orderId + "]", e);
		}

		LOGGER.debug("getOrder end: {}");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDto> getOrders(String customerName, String customerEmail,
			String token) throws Exception {
		LOGGER.debug("getOrder start:{}");
		LOGGER.debug("customerName:{}", customerName);
		LOGGER.debug("customerEmail:{}", customerEmail);
		List<OrderDto> result = null;
		Payload payload = new Payload();
		payload.addProperty(OrderRoute.PROCESS_TYPE, "getOrders");
		payload.addHeader("token", token);
		payload.addHeader("customerEmail", customerEmail);
		payload.addHeader("customerName", customerName);
		result = (List<OrderDto>) orderProducer.produce(payload);
		return result;
	}

	@Override
	public void cancelOrder(Long orderId, String token) throws Exception {
		LOGGER.debug("cancel order start: {}");
		Payload payload = new Payload();
		payload.addProperty(OrderRoute.PROCESS_TYPE, "cancelOrder");
		payload.addHeader("token", token);
		payload.addHeader("orderId", orderId);
		orderProducer.produce(payload);
	}

	@Override
	public OrderDto updateOrder(OrderDto orderDto, String token)
			throws Exception {
		LOGGER.debug("update order start: {}", orderDto);
		OrderDto order = null;
		Payload payload = Payload.getBuilder(orderDto).build();
		payload.addProperty(OrderRoute.PROCESS_TYPE, "updateOrder");
		payload.addHeader("token", token);
		payload.addHeader("orderId", orderDto.getOrderId());
		order = (OrderDto) orderProducer.produce(payload);
		return order;
	}

}
