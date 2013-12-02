package net.pizzashack.camel.ds.impl;

import java.util.List;

import net.pizzashack.camel.route.MenuRoute;
import net.pizzashack.camel.support.CamelProducerSupport;
import net.pizzashack.camel.support.CamelRouteCallback;
import net.pizzashack.camel.support.Payload;
import net.pizzashack.data.Pizza;
import net.pizzashack.ds.MenuItemDS;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("menuItemDs")
public class MenuItemDSImpl
    implements MenuItemDS {

  @Autowired
  private CamelProducerSupport camelProducerSupport;

  @EndpointInject(uri = MenuRoute.ENDPOINT_URI)
  private Endpoint endpoint;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MenuItemDSImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<Pizza> getAvailablePizzaList(String accessToken)
      throws Exception {
    LOGGER.debug("getAvailablePizzaList start: {}", accessToken);
    Payload payload = new Payload();
    payload.addHeader("token", accessToken);
    List<Pizza> resultList = null;

    resultList = (List<Pizza>) camelProducerSupport.processTwoWay(endpoint, payload, new CamelRouteCallback() {
      @Override
      public Object postProcess(Exchange exchange)
          throws Exception {
        return exchange.getOut().getBody();
      }
    });
    return resultList;
  }

}
