package org.pizzashack.restlet.config;

import org.apache.camel.CamelContext;
import org.apache.camel.component.restlet.RestletComponent;
import org.pizzashack.restlet.route.PizzashackRestletRoute;
import org.restlet.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestletConfig {

	@Autowired
	private CamelContext camelContext;

	@Autowired
	private PizzashackRestletRoute pizzashackRestletRoute;

	@Bean(name = "RestletComponent")
	public Component restletComponent() {
		return new Component();
	}

	@Bean(name = "RestletComponentService")
	public RestletComponent restletComponentService() throws Exception {
		RestletComponent restletComponent = new RestletComponent(
				restletComponent());
//		restletComponent.setCamelContext(camelContext);
//		camelContext.addComponent("restlet", restletComponent);
		camelContext.addRoutes(pizzashackRestletRoute);
		return restletComponent;
	}
}
