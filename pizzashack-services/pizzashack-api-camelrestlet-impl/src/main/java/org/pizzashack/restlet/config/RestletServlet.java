package org.pizzashack.restlet.config;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.restlet.ext.spring.SpringServerServlet;

@SuppressWarnings("serial")
@WebServlet(initParams = {@WebInitParam(name = "org.restlet.component", value = "RestletComponent")})
public class RestletServlet extends SpringServerServlet {

}
