package org.pizzashack.integration.support;

public interface IntegrationProducer {

  Object produce(final Payload payload)
      throws Exception;
}
