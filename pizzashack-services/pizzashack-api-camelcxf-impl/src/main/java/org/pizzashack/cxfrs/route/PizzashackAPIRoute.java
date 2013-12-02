package org.pizzashack.cxfrs.route;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PizzashackAPIRoute extends SpringRouteBuilder {

	@Override
	public void configure() throws Exception {
//		from("cxfrs:bean:pizzashackCxfRsEndpoint")
	}

}
