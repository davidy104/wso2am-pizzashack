package org.pizzashack.integration.route;

import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.spring.SpringRouteBuilder;
import org.pizzashack.integration.mappings.transform.CustomerToJAXBTransformer;
import org.pizzashack.integration.mappings.transform.OrderToCSVTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderRoute extends SpringRouteBuilder {

	public final static String SENDCUST_ENDPOINT = "direct:sendCustomer";

	public final static String SENDORDER_ENDPOINT = "direct:sendOrder";

	public final static String PLACEORDER_ENDPOINT = "direct:placeOrder";

	public final static String PLACEORDER_OUTPUT_ENDPOINT = "direct:placeOrderOutput";

	public final static String ORDERTOFILE_ENDPOINT = "direct:orderToFile";

	public final static String SENDCUST_REQUIRED = "sendCustRequired";

	public final static String CUSTOMER = "customer";

	@Autowired
	private CustomerToJAXBTransformer customerToJAXBTransformer;

	@Autowired
	private OrderToCSVTransformer orderToCSVTransformer;

	@Autowired
	private JaxbDataFormat customerJaxbDataFormat;

	@Autowired
	private GsonDataFormat orderJSONDataFormat;

	@Value("${ordersCSVOutputFolder}")
	private String ordersOutputFolder;

	@Override
	public void configure() throws Exception {

		from(PLACEORDER_ENDPOINT).to("log:input")
				.wireTap(PLACEORDER_OUTPUT_ENDPOINT)
				.executorServiceRef("genericThreadPool");

		from(PLACEORDER_OUTPUT_ENDPOINT).multicast()
				.parallelProcessing()
				// .stopOnException()
				.executorServiceRef("genericThreadPool")
				.to(SENDCUST_ENDPOINT, SENDORDER_ENDPOINT, ORDERTOFILE_ENDPOINT);

		from(ORDERTOFILE_ENDPOINT)
				.transform(orderToCSVTransformer)
				.marshal()
				.bindy(BindyType.Csv,
						"org.pizzashack.integration.mappings.csv.simple")
				.to("file:" + ordersOutputFolder
						+ "?fileName=orders.txt&fileExist=Append");

		from(SENDCUST_ENDPOINT).choice()
				.when(property(SENDCUST_REQUIRED).isEqualTo(true))
				.transform(customerToJAXBTransformer)
				.marshal(customerJaxbDataFormat)
				.to("jms:queue:pizzashackcustomer?jmsMessageType=Text");

		from(SENDORDER_ENDPOINT).marshal(orderJSONDataFormat).to(
				"jms:queue:pizzashackorder?jmsMessageType=Text");

	}
}
