package org.pizzashack.restlet.route;

import org.apache.camel.spring.SpringRouteBuilder;
import org.pizzashack.restlet.processor.PizzashackProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PizzashackRestletRoute extends SpringRouteBuilder {

	@Autowired
	private PizzashackProcessor pizzashackProcessor;

	@Override
	public void configure() throws Exception {
		from("restlet:/v1/menu?restletMethod=GET")
		.to("log:input")
		.process(pizzashackProcessor)
		.to("log:output");
	}
}
