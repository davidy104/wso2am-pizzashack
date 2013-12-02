package org.pizzashack.restlet.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class PizzashackAPIRestletInitializer
		implements
			WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfig.class);

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"RestletServlet", RestletServlet.class);
		dispatcher.addMapping("/rest/*");

		// Context loader listener
		servletContext.addListener(new ContextLoaderListener(rootContext));
	}

}
