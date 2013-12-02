package org.pizzashack.restlet.processor;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.pizzashack.data.MenuItemDTO;
import org.pizzashack.ds.PizzaMenuDS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PizzashackProcessor implements Processor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackProcessor.class);

	@Autowired
	private PizzaMenuDS pizzaMenuDs;
	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.debug("PizzashackProcessor start:{}");
		Message inMessage = exchange.getIn();
		String uri = inMessage.getHeader(Exchange.HTTP_URI, String.class);
		String method = inMessage.getHeader(Exchange.HTTP_METHOD, String.class);
		LOGGER.debug("uri:{}", uri);
		LOGGER.debug("method:{}", method);

		List<MenuItemDTO> menus = pizzaMenuDs.getAllMenuItems();
		exchange.getOut().setBody(menus);
		LOGGER.debug("PizzashackProcessor end:{}");
	}

}
