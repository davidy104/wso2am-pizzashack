package net.pizzashack.camel.support;

public interface IntegrationProducer {

  Object produce(final Payload payload)
      throws Exception;
}
