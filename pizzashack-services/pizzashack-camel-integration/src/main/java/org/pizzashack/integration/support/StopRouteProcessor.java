package org.pizzashack.integration.support;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class StopRouteProcessor
    implements Processor {
  private final String routeId;

  public StopRouteProcessor(String routeId) {
    this.routeId = routeId;
  }

  public void process(Exchange exchange)
      throws Exception {
    exchange.getContext().getInflightRepository().remove(exchange);
    exchange.getContext().stopRoute(routeId);
  }
}
