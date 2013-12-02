package net.pizzashack.camel.mappings;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("tokenReqTransformer")
public class TokenReqTransformer
    implements Expression {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(TokenReqTransformer.class);

  @SuppressWarnings("unchecked")
  @Override
  public <T> T evaluate(Exchange exchange, Class<T> type) {

    Message in = exchange.getIn();
    String applicationToken = (String) in.getHeader("applicationToken");
    String username = (String) in.getHeader("username");
    String password = (String) in.getHeader("password");

    LOGGER.debug("applicationToken in transformer :{}", applicationToken);
    String payload = "grant_type=password&username=" + username
        + "&password=" + password;

    LOGGER.debug("after transform, body:{}", payload);
    return (T) payload;
  }

}
