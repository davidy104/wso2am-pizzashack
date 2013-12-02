package net.pizzashack.camel.support;

import org.apache.camel.Exchange;

public interface CamelRouteCallback {

  Object postProcess(Exchange exchange)
      throws Exception;
}
