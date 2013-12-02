package org.pizzashack.integration.config;

import java.util.List;

import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.SpringRouteBuilder;

public class InitialRouteSetup {

	private SpringCamelContext camelContext;

	private List<SpringRouteBuilder> routeBuilderList;

	public InitialRouteSetup(SpringCamelContext camelContext,
			List<SpringRouteBuilder> routeBuilderList) {
		this.camelContext = camelContext;
		this.routeBuilderList = routeBuilderList;
	}

	public void registerRoutes() throws Exception {
		if (routeBuilderList != null) {
			for (SpringRouteBuilder springRouteBuilder : routeBuilderList) {
				camelContext.addRoutes(springRouteBuilder);
			}
		}
	}
}
