package net.pizzashack.camel.route;

import javax.annotation.Resource;

import net.pizzashack.camel.OrderBatchAggregationStrategy;
import net.pizzashack.camel.OrderBatchService;
import net.pizzashack.camel.mappings.PizzashackGsonDataFormat;
import net.pizzashack.camel.processor.OrderBatchProcessor;
import net.pizzashack.config.CamelHttpClientConfig;
import net.pizzashack.config.Wso2amConfig;

import org.apache.camel.Exchange;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderBatchRoute extends SpringRouteBuilder {
	@Resource
	private CamelHttpClientConfig camelHttpConfig;

	@Autowired
	private PizzashackGsonDataFormat pizzashackGsonDataFormat;

	@Autowired
	private OrderBatchProcessor orderBatchProcessor;

	@Autowired
	private OrderBatchService orderBatchService;

	@Resource
	private Wso2amConfig wso2amConfig;

	public static final String ENDPOINT_URI = "direct:processOrders";
	@Override
	public void configure() throws Exception {
		from(ENDPOINT_URI)
				.routeId(ENDPOINT_URI)
				.setHeader("Authorization", simple("Bearer ${header.token}"))
				.setHeader(Exchange.HTTP_PATH, simple("/order/query"))
				.setHeader(
						Exchange.HTTP_QUERY,
						simple("customerName=${header.customerName}&customerEmail=${header.customerEmail}"))
				.to(wso2amConfig.getBaseUrl() + "?httpClient.soTimeout="
						+ camelHttpConfig.getCamelHttpTimeout() + "")
				.to("log:output").choice()
				.when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(200))
				.to("direct:orderPrcess").bean(orderBatchService, "process")
				.otherwise()
				.throwException(new Exception("query orders error"));

		from("direct:orderPrcess")
				.unmarshal(pizzashackGsonDataFormat.getOrderListDataFormat())
				.to("log:output")
				.split(body(), new OrderBatchAggregationStrategy())
				.process(orderBatchProcessor).end();
	}

}
