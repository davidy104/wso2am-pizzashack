package net.pizzashack.camel.route;

import javax.annotation.Resource;

import net.pizzashack.camel.mappings.PizzashackGsonDataFormat;
import net.pizzashack.camel.mappings.TokenReqTransformer;
import net.pizzashack.config.CamelHttpClientConfig;
import net.pizzashack.config.Wso2amConfig;

import org.apache.camel.Exchange;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRoute extends SpringRouteBuilder {

	@Resource
	private CamelHttpClientConfig camelHttpConfig;

	public final static String ENDPOINT_URI = "direct:startAccount";

	@Autowired
	private PizzashackGsonDataFormat pizzashackGsonDataFormat;

	@Autowired
	private TokenReqTransformer tokenReqTransformer;

	@Resource
	private Wso2amConfig wso2amConfig;

	@Override
	public void configure() throws Exception {
		onException(java.net.SocketTimeoutException.class)
				.maximumRedeliveries(
						camelHttpConfig.getCamelMaximumRedeliveries())
				.redeliveryDelay(camelHttpConfig.getCamelRedeliveryDelay())
				.handled(true).to("log:errors?level=ERROR");

		from(ENDPOINT_URI)
				.routeId(ENDPOINT_URI)
				.to("log:input")
				.transform(tokenReqTransformer)
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE,
						constant("application/x-www-form-urlencoded"))
				.setHeader("Authorization",
						simple("${header.applicationToken}"))
				.to(wso2amConfig.getTokenUrl() + "?httpClient.soTimeout="
						+ camelHttpConfig.getCamelHttpTimeout() + "")
				.to("log:output")
				.unmarshal(pizzashackGsonDataFormat.getTokenGsonDataFormat())
				.log("output-body: ${body}");
	}

}
