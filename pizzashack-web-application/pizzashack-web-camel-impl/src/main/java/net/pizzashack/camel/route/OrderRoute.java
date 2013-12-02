package net.pizzashack.camel.route;

import javax.annotation.Resource;

import net.pizzashack.camel.mappings.OrderListTransformer;
import net.pizzashack.camel.mappings.PizzashackGsonDataFormat;
import net.pizzashack.config.CamelHttpClientConfig;
import net.pizzashack.config.Wso2amConfig;

import org.apache.camel.Exchange;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderRoute extends SpringRouteBuilder {

	@Resource
	private CamelHttpClientConfig camelHttpConfig;

	@Autowired
	private PizzashackGsonDataFormat pizzashackGsonDataFormat;

	@Resource
	private Wso2amConfig wso2amConfig;

	public final static String ENDPOINT_URI = "direct:orderStart";

	public final static String PLACE_ORDER_ENDPOINT_URI = "direct:placeOrder";

	public final static String GET_ORDER_ENDPOINT_URI = "direct:getOrder";

	public final static String GET_ORDERS_ENDPOINT_URI = "direct:getOrders";

	public final static String UPDATE_ORDER_ENDPOINT_URI = "direct:updateOrder";

	public final static String CANCEL_ORDER_ENDPOINT_URI = "direct:cancelOrder";

	public final static String DELIVER_ORDER_ENDPOINT_URI = "direct:deliverOrder";

	public final static String PROCESS_TYPE = "processType";

	private final String PIZZA_ORDER_URL = "/order";

	@Autowired
	private OrderListTransformer orderListTransformer;

	@Override
	public void configure() throws Exception {
		onException(java.net.SocketTimeoutException.class)
				.maximumRedeliveries(
						camelHttpConfig.getCamelMaximumRedeliveries())
				.redeliveryDelay(camelHttpConfig.getCamelRedeliveryDelay())
				.handled(true).to("log:errors?level=ERROR");

		from(ENDPOINT_URI)
				.routeId(ENDPOINT_URI)
				.choice()
				.when(property(PROCESS_TYPE).isEqualTo("placeOrder"))
				.to(PLACE_ORDER_ENDPOINT_URI)
				.when(property(PROCESS_TYPE).isEqualTo("getOrder"))
				.to(GET_ORDER_ENDPOINT_URI)
				.when(property(PROCESS_TYPE).isEqualTo("updateOrder"))
				.to(UPDATE_ORDER_ENDPOINT_URI)
				.when(property(PROCESS_TYPE).isEqualTo("getOrders"))
				.to(GET_ORDERS_ENDPOINT_URI)
				.when(property(PROCESS_TYPE).isEqualTo("cancelOrder"))
				.to(CANCEL_ORDER_ENDPOINT_URI)
				.when(property(PROCESS_TYPE).isEqualTo("deliverOrder"))
				.to(DELIVER_ORDER_ENDPOINT_URI)
				.otherwise()
				.to("log:undefined process Type?level=ERROR")
				.end()
				.to("log:input")

				.choice()
				.when(property(PROCESS_TYPE).isNotEqualTo("deliverOrder"))
				.to(wso2amConfig.getBaseUrl() + "?httpClient.soTimeout="
						+ camelHttpConfig.getCamelHttpTimeout() + "")
				.to("log:output").choice()
				.when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(200))
				.to("direct:marshalResp");

		from("direct:marshalResp").choice()
				.when(property(PROCESS_TYPE).isEqualTo("getOrders"))
				.transform(orderListTransformer)
				.when(property(PROCESS_TYPE).isNotEqualTo("cancelOrder"))
				.unmarshal(pizzashackGsonDataFormat.getOrderGsonDataFormat());

		from(PLACE_ORDER_ENDPOINT_URI).routeId(PLACE_ORDER_ENDPOINT_URI)
				.marshal(pizzashackGsonDataFormat.getOrderGsonDataFormat())
				.setHeader("Authorization", simple("Bearer ${header.token}"))
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.setHeader(Exchange.HTTP_PATH, constant(PIZZA_ORDER_URL));

		from(GET_ORDER_ENDPOINT_URI)
				.routeId(GET_ORDER_ENDPOINT_URI)
				.setHeader("Authorization", simple("Bearer ${header.token}"))
				.setHeader(Exchange.HTTP_PATH,
						simple(PIZZA_ORDER_URL + "/${header.orderId}"));

		from(GET_ORDERS_ENDPOINT_URI)
				.routeId(GET_ORDERS_ENDPOINT_URI)
				.setHeader("Authorization", simple("Bearer ${header.token}"))
				.setHeader(Exchange.HTTP_PATH,
						simple(PIZZA_ORDER_URL + "/query"))
				.setHeader(
						Exchange.HTTP_QUERY,
						simple("customerName=${header.customerName}&customerEmail=${header.customerEmail}"));

		from(UPDATE_ORDER_ENDPOINT_URI)
				.routeId(UPDATE_ORDER_ENDPOINT_URI)
				.marshal(pizzashackGsonDataFormat.getOrderGsonDataFormat())
				.setHeader("Authorization", simple("Bearer ${header.token}"))
				.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.setHeader(Exchange.HTTP_PATH,
						simple(PIZZA_ORDER_URL + "/${header.orderId}"));

		from(DELIVER_ORDER_ENDPOINT_URI)
				.routeId(DELIVER_ORDER_ENDPOINT_URI)
//				.setHeader("Authorization", simple("Bearer ${header.token}"))
				.setHeader(Exchange.HTTP_METHOD, constant("PATCH"))
//				.setHeader("toPatch", constant("true"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.setHeader(Exchange.HTTP_PATH,
						simple(PIZZA_ORDER_URL + "/delivery/${header.orderId}"))
				.setHeader(Exchange.HTTP_QUERY,
						simple("deliverStatus=${header.deliverStatus}"))
				.to(wso2amConfig.getBaseUrl() + "http://localhost:8082/pizzashack/rest?httpClient.soTimeout="+ camelHttpConfig.getCamelHttpTimeout() + "")
				.to("log:output").choice()
				.when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(200))
				.to("direct:marshalResp");


		from(CANCEL_ORDER_ENDPOINT_URI)
				.routeId(CANCEL_ORDER_ENDPOINT_URI)
				.setHeader("Authorization", simple("Bearer ${header.token}"))
				.setHeader(Exchange.HTTP_METHOD, constant("DELETE"))
				.setHeader(Exchange.HTTP_PATH,
						simple(PIZZA_ORDER_URL + "/${header.orderId}"));

	}
}
