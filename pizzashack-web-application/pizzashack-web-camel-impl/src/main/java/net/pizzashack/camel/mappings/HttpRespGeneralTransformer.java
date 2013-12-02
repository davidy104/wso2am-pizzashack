package net.pizzashack.camel.mappings;

import java.io.InputStream;

import net.pizzashack.util.PizzashackUtils;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("httpRespGeneralTransformer")
public class HttpRespGeneralTransformer implements Expression {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpRespGeneralTransformer.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T evaluate(Exchange exchange, Class<T> type) {
		Message in = exchange.getIn();

		int responseCode = in.getHeader(Exchange.HTTP_RESPONSE_CODE,
				Integer.class);
		LOGGER.debug("responseCode: {}", responseCode);

		String responseStr = PizzashackUtils.convertToString(in
				.getBody(InputStream.class));
		LOGGER.debug("responseStr: {}", responseStr);
		return (T) responseStr;
	}

}
