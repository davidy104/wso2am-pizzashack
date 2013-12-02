package org.pizzashack.jersey.config;

import javax.servlet.annotation.WebServlet;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import javax.servlet.annotation.WebInitParam;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/rest/*" }, initParams = {
		@WebInitParam(name = "com.sun.jersey.config.property.packages", value = "org.pizzashack.jersey.resource"),
		@WebInitParam(name = "com.sun.jersey.api.json.POJOMappingFeature", value = "true") })
public class JerseySpringServlet extends SpringServlet {

}
