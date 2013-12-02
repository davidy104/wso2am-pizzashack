package net.pizzashack.test;

import java.util.List;

import net.pizzashack.camel.route.OrderBatchRoute;
import net.pizzashack.camel.route.OrderRoute;
import net.pizzashack.camel.support.CamelProducerSupport;
import net.pizzashack.camel.support.CamelRouteCallback;
import net.pizzashack.camel.support.Payload;
import net.pizzashack.config.WebMvcContextConfiguration;
import net.pizzashack.data.dto.OrderDto;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcContextConfiguration.class})
@Ignore("")
public class OrderBatchTest {

	@Autowired
	private CamelProducerSupport camelProducerSupport;

	@EndpointInject(uri = OrderBatchRoute.ENDPOINT_URI)
	private Endpoint endpoint;

	private static final String TOKEN = "1lmB_ABcnleP7_Yrjm8Jju9sGGoa";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderBatchTest.class);

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		Payload payload = new Payload();
		payload.addProperty(OrderRoute.PROCESS_TYPE, "getOrders");
		payload.addHeader("token", TOKEN);
		payload.addHeader("customerEmail", "david.yuan@yellow.co.nz");
		payload.addHeader("customerName", "david");

		List<OrderDto> resultList = (List<OrderDto>) camelProducerSupport
				.processTwoWay(endpoint, payload, new CamelRouteCallback() {
					@Override
					public Object postProcess(Exchange exchange)
							throws Exception {
						return exchange.getOut().getBody();
					}
				});

		LOGGER.debug("print order outside***********************:{}");

		for (OrderDto orderDto : resultList) {
			LOGGER.debug("orderDto:{}", orderDto);
		}
	}

}
