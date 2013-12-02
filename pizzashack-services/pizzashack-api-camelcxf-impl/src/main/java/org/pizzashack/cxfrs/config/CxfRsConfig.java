package org.pizzashack.cxfrs.config;

import org.apache.camel.component.cxf.jaxrs.CxfRsComponent;
import org.apache.camel.component.cxf.jaxrs.CxfRsEndpoint;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.cxf.bus.spring.SpringBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:META-INF/cxf/cxf.xml"})
public class CxfRsConfig {

	@Autowired
	private SpringCamelContext camelContext;

	@Bean(destroyMethod = "shutdown")
	public SpringBus cxf() {
		return new SpringBus();
	}

	@Bean
	public CxfRsEndpoint pizzashackCxfRsEndpoint() {
		CxfRsEndpoint pizzashackCxfRsEndpoint = null;
		CxfRsComponent pizzashackCxfRsComponent = new CxfRsComponent(
				camelContext);
		camelContext.addComponent("cxfrs", pizzashackCxfRsComponent);
		pizzashackCxfRsEndpoint = new CxfRsEndpoint(
				"cxfrs:http://localhost:8082/pizzashack/rest/v1?resourceClasses=org.pizzashack.resource.PizzashackAPI&bindingStyle=SimpleConsumer",
				pizzashackCxfRsComponent);
		// pizzashackCxfRsEndpoint.setResourceClasses(PizzashackAPI.class);
		// pizzashackCxfRsEndpoint.setBindingStyle(BindingStyle.SimpleConsumer);
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("pizzashackCxfRsEndpoint", pizzashackCxfRsEndpoint);
		camelContext.setRegistry(registry);

		return pizzashackCxfRsEndpoint;
	}
}
