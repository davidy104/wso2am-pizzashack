package net.pizzashack.camel.route;

import javax.annotation.Resource;

import net.pizzashack.camel.mappings.PizzashackGsonDataFormat;
import net.pizzashack.config.CamelHttpClientConfig;
import net.pizzashack.config.Wso2amConfig;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuRoute extends SpringRouteBuilder {
	@Resource
	private CamelHttpClientConfig camelHttpConfig;

	public final static String ENDPOINT_URI = "direct:startMenu";

	@Resource
	private Wso2amConfig wso2amConfig;

	// @Autowired
	// private PizzaListTransformer pizzaListTransformer;

	@Autowired
	private PizzashackGsonDataFormat pizzashackGsonDataFormat;

	@Override
	public void configure() throws Exception {
		onException(java.net.SocketTimeoutException.class)
				.maximumRedeliveries(
						camelHttpConfig.getCamelMaximumRedeliveries())
				.redeliveryDelay(camelHttpConfig.getCamelRedeliveryDelay())
				.handled(true).to("log:errors?level=ERROR");

		from(ENDPOINT_URI)
				.routeId(ENDPOINT_URI)
				.setHeader("Authorization", simple("Bearer ${header.token}"))
				.to(wso2amConfig.getBaseUrl() + "/menu?httpClient.soTimeout="
						+ camelHttpConfig.getCamelHttpTimeout() + "")

				// .transform(pizzaListTransformer);
				.unmarshal(pizzashackGsonDataFormat.getPizzaListDataFormat());

	}

}
