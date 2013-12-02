package net.pizzashack.camel;

import net.pizzashack.camel.route.OrderRoute;
import net.pizzashack.camel.support.CamelProducerSupport;
import net.pizzashack.camel.support.CamelRouteCallback;
import net.pizzashack.camel.support.IntegrationProducer;
import net.pizzashack.camel.support.Payload;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("orderProducer")
public class OrderProducer
    implements IntegrationProducer {

  @Autowired
  private CamelProducerSupport camelProducerSupport;

  @EndpointInject(uri = OrderRoute.ENDPOINT_URI)
  private Endpoint endpoint;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(OrderProducer.class);

  @Override
  public Object produce(Payload payload)
      throws Exception {
    LOGGER.debug("order produce start:{}", payload);
    return camelProducerSupport.processTwoWay(endpoint, payload,
        new CamelRouteCallback() {
          @Override
          public Object postProcess(Exchange exchange)
              throws Exception {
            Integer status = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
            Object output = exchange.getOut().getBody();
            if (status != 200) {
              throw new Exception((String) output);
            }
            return exchange.getOut().getBody();
          }
        });
  }
}
