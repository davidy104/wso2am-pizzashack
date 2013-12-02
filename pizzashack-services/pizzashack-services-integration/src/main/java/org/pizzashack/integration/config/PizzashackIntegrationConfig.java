package org.pizzashack.integration.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;

import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.SpringRouteBuilder;
import org.pizzashack.data.Customer;
import org.pizzashack.data.Order;
import org.pizzashack.integration.mappings.jaxb.CustomerJaxbModel;
import org.pizzashack.integration.mappings.jaxb.OrderJaxbModel;
import org.pizzashack.integration.mappings.json.JsonAnnotationExclusionStrategy;
import org.pizzashack.integration.route.OrderRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingPolicy;

@Configuration
public class PizzashackIntegrationConfig {

	@Autowired
	private SpringCamelContext camelContext;

	@Autowired
	private OrderRoute orderRoute;

	@Bean
	public JaxbDataFormat customerJaxbDataFormat() throws Exception {
		JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
		jaxbDataFormat.setPrettyPrint(true);
		jaxbDataFormat.setContext(JAXBContext.newInstance(
				CustomerJaxbModel.class, OrderJaxbModel.class));
		// jaxbDataFormat.setContextPath(CustomerJaxbModel.class.getPackage()
		// .getName());
		return jaxbDataFormat;
	}

	@Bean
	public GsonDataFormat orderJSONDataFormat() {
		GsonDataFormat gsonDataFormat = new GsonDataFormat();
		List<ExclusionStrategy> exclusionStrategies = new ArrayList<ExclusionStrategy>();
		exclusionStrategies.add(new JsonAnnotationExclusionStrategy());
		gsonDataFormat.setExclusionStrategies(exclusionStrategies);
		gsonDataFormat.setPrettyPrinting(true);
		gsonDataFormat.setUnmarshalType(Order.class);
		gsonDataFormat
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		return gsonDataFormat;
	}

	@Bean
	public GsonDataFormat customerOrdersJSONDataFormat() {
		GsonDataFormat gsonDataFormat = new GsonDataFormat();
		List<ExclusionStrategy> exclusionStrategies = new ArrayList<ExclusionStrategy>();
		exclusionStrategies.add(new JsonAnnotationExclusionStrategy());
		gsonDataFormat.setExclusionStrategies(exclusionStrategies);
		gsonDataFormat.setPrettyPrinting(true);
		gsonDataFormat.setUnmarshalType(Customer.class);
		gsonDataFormat
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		return gsonDataFormat;
	}

	@Bean
	static public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
		final PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
		final org.springframework.core.io.Resource[] resourceLocations = properties();
		p.setLocations(resourceLocations);
		p.setIgnoreResourceNotFound(false);
		p.setIgnoreUnresolvablePlaceholders(false);
		return p;
	}

	static org.springframework.core.io.Resource[] properties() {
		return new org.springframework.core.io.Resource[]{new ClassPathResource(
				"/pizzashack-integration.properties")};
	}

	@Bean(initMethod = "registerRoutes")
	public InitialRouteSetup setupRoutes() {
		List<SpringRouteBuilder> routeBuilderList = new ArrayList<SpringRouteBuilder>();
		routeBuilderList.add(orderRoute);
		return new InitialRouteSetup(camelContext, routeBuilderList);
	}
}
