package org.pizzashack.integration.support;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("camelProducerSupport")
public class CamelProducerSupport
    implements CamelContextAware {

  private ProducerTemplate producer;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(CamelProducerSupport.class);

  private CamelContext camelContext;

  @Override
  public void setCamelContext(CamelContext camelContext) {
    this.camelContext = camelContext;
  }

  @Override
  public CamelContext getCamelContext() {
    return camelContext;
  }

  public void processOneWay(Endpoint endpoint, Payload payload)
      throws Exception {
    LOGGER.info("processOneWay start:{}");
    ExchangePattern pattern = ExchangePattern.InOnly;
    DefaultExchange exchange = setup(endpoint, pattern, payload);
    try {
      producer.send(endpoint, exchange);
      LOGGER.info("processOneWay end:{}");
    }
    catch (Exception e) {
      throw new Exception("producer processOneWay failed", e);
    }
    finally {
      close();
    }
  }

  public Object processTwoWay(Endpoint endpoint, Payload payload,
      CamelRouteCallback callback)
      throws Exception {
    LOGGER.info("processTwoWay start:{}");
    Object result = null;
    ExchangePattern pattern = ExchangePattern.InOut;
    DefaultExchange exchange = setup(endpoint, pattern, payload);
    try {
      Exchange resultExchange = producer.send(endpoint, exchange);
      if (callback != null) {
        result = callback.postProcess(resultExchange);
        LOGGER.debug("processTwoWay end:{}", result);
      }
    }
    catch (Exception e) {
      throw new Exception("producer processTwoWay failed", e);
    }
    finally {
      close();
    }
    return result;
  }

  private DefaultExchange setup(Endpoint endpoint, ExchangePattern pattern,
      Payload payload) {
    producer = camelContext.createProducerTemplate();
    DefaultExchange exchange = new DefaultExchange(endpoint, pattern);
    Map<String, Object> properties = payload.getProperties();
    Map<String, Object> headers = payload.getHeaders();
    Object body = payload.getBody();

    if (properties != null) {
      exchange.setProperties(properties);
    }

    if (headers != null && !headers.isEmpty()) {
      exchange.getIn().setHeaders(headers);
    }
    exchange.getIn().setBody(body);
    return exchange;
  }

  private void close()
      throws Exception {
    if (producer != null) {
      producer.stop();
    }
  }

}
