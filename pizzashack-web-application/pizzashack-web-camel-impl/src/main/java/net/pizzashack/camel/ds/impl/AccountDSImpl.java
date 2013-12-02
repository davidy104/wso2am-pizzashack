package net.pizzashack.camel.ds.impl;

import javax.annotation.Resource;

import net.pizzashack.camel.route.AccountRoute;
import net.pizzashack.camel.support.CamelProducerSupport;
import net.pizzashack.camel.support.CamelRouteCallback;
import net.pizzashack.camel.support.Payload;
import net.pizzashack.config.Wso2amConfig;
import net.pizzashack.data.Token;
import net.pizzashack.data.dto.AccountDto;
import net.pizzashack.ds.AccountDS;
import net.pizzashack.ds.AuthenticationException;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

@Service("accountDs")
public class AccountDSImpl implements AccountDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountDSImpl.class);

	@Resource
	private Wso2amConfig wso2amConfig;

	@Autowired
	private CamelProducerSupport camelProducerSupport;

	@EndpointInject(uri = AccountRoute.ENDPOINT_URI)
	private Endpoint endpoint;

	@Override
	public void login(AccountDto accountDto) throws AuthenticationException {
		LOGGER.debug("login start: {}" + accountDto.getUsername() + ":"
				+ accountDto.getPassword());
		Token token = null;
		try {
			token = this.getToken(accountDto.getUsername(),
					accountDto.getPassword());
			LOGGER.debug("token: {} " + token);
			if (token == null) {
				throw new AuthenticationException(
						"AuthenticationException fail.");
			}
		} catch (Exception e) {
			throw new AuthenticationException("AuthenticationException fail.",
					e);
		}
		accountDto.setAccessToken(token);
	}

	private Token getToken(String username, String password) throws Exception {
		String applicationToken = wso2amConfig.getConsumerKey() + ":"
				+ wso2amConfig.getConsumerSecret();
		BASE64Encoder base64Encoder = new BASE64Encoder();
		applicationToken = "Basic "
				+ base64Encoder.encode(applicationToken.getBytes()).trim();
		LOGGER.debug("applicationToken:{}", applicationToken);
		Payload payload = new Payload();
		payload.addHeader("applicationToken", applicationToken);
		payload.addHeader("username", username);
		payload.addHeader("password", password);

		LOGGER.debug("Payload:{}", payload);
		Token token = (Token) camelProducerSupport.processTwoWay(endpoint,
				payload, new CamelRouteCallback() {
					@Override
					public Object postProcess(Exchange exchange)
							throws Exception {
						return exchange.getOut().getBody();
					}
				});

		LOGGER.debug("getToken end, response: {}", token);
		return token;

	}
}
