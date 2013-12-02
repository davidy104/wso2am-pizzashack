package net.pizzashack.ds.impl;

import java.util.List;

import javax.ws.rs.core.MediaType;

import net.pizzashack.data.Pizza;
import net.pizzashack.ds.JerseyClientSupport;
import net.pizzashack.ds.MenuItemDS;
import net.pizzashack.util.PizzashackJSONTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

@Service("menuItemDs")
public class MenuItemDSImpl extends JerseyClientSupport implements MenuItemDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountDSImpl.class);

	public final String PIZZA_LIST_URL = "/menu";

	@Override
	public List<Pizza> getAvailablePizzaList(String accessToken)
			throws Exception {
		LOGGER.debug("getAvailablePizzaList start:{}", accessToken);
		List<Pizza> resultList = null;

		WebResource webResource = client.resource(wso2apiConfig.getBaseUrl())
				.path(PIZZA_LIST_URL);

		ClientResponse clientResp = webResource
				.header(AUTH_HEADER, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		Status statusCode = clientResp.getClientResponseStatus();
		String responseStr = getResponsePayload(clientResp);
		if (statusCode == Status.OK) {
			resultList = PizzashackJSONTransformer
					.getAvailablePizzaList(responseStr);
		} else {
			throw new Exception(responseStr);
		}

		LOGGER.debug("getAvailablePizzaList end:{}");
		return resultList;
	}

}
