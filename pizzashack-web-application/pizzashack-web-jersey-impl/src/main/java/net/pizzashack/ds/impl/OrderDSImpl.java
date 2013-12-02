package net.pizzashack.ds.impl;

import java.util.List;

import javax.ws.rs.core.MediaType;

import net.pizzashack.data.dto.OrderDto;
import net.pizzashack.ds.JerseyClientSupport;
import net.pizzashack.ds.OrderDS;
import net.pizzashack.util.PizzashackJSONTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

@Service("orderDs")
public class OrderDSImpl extends JerseyClientSupport implements OrderDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderDSImpl.class);

	private final String PIZZA_ORDER_URL = "/order";

	@Override
	public OrderDto deliverOrder(Long orderId, String token) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDto placeOrder(OrderDto orderDto, String token)
			throws Exception {
		LOGGER.debug("placeOrder start: {}", orderDto);
		LOGGER.debug("token:{}", token);
		String jsonString = PizzashackJSONTransformer
				.generateSaveOrderMessage(orderDto);
		OrderDto result = null;

		WebResource webResource = client.resource(wso2apiConfig.getBaseUrl())
				.path(PIZZA_ORDER_URL);

		ClientResponse clientResp = webResource
				.header(AUTH_HEADER, "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, jsonString);

		Status statusCode = clientResp.getClientResponseStatus();
		String responseStr = getResponsePayload(clientResp);

		if (statusCode == Status.OK) {
			result = PizzashackJSONTransformer.getOrder(responseStr);
		} else {
			throw new Exception(responseStr);
		}
		LOGGER.debug("placeOrder end: {}", result);
		return result;
	}

	@Override
	public OrderDto getOrder(Long orderId, String token) throws Exception {
		LOGGER.debug("getOrder start:{}", orderId);
		LOGGER.debug("token:{}", token);
		OrderDto result = null;
		WebResource webResource = client.resource(wso2apiConfig.getBaseUrl())
				.path(PIZZA_ORDER_URL + "/" + orderId);
		ClientResponse clientResp = webResource
				.header(AUTH_HEADER, "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		Status statusCode = clientResp.getClientResponseStatus();
		String responseStr = getResponsePayload(clientResp);

		if (statusCode == Status.OK) {
			result = PizzashackJSONTransformer.getOrder(responseStr);
		} else {
			throw new Exception(responseStr);
		}
		LOGGER.debug("getOrder end:{}");
		return result;
	}

	@Override
	public List<OrderDto> getOrders(String customerName, String customerEmail,
			String token) throws Exception {
		LOGGER.debug("getOrders start:{}");
		LOGGER.debug("customerName:{}", customerName);
		LOGGER.debug("customerEmail:{}", customerEmail);
		LOGGER.debug("token:{}", token);

		List<OrderDto> resultList = null;
		WebResource webResource = client.resource(wso2apiConfig.getBaseUrl())
				.path(PIZZA_ORDER_URL + "/query")
				.queryParam("customerName", customerName)
				.queryParam("customerEmail", customerEmail)
				.queryParam("delivered", "N");
		ClientResponse clientResp = webResource
				.header(AUTH_HEADER, "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		Status statusCode = clientResp.getClientResponseStatus();
		String responseStr = getResponsePayload(clientResp);

		if (statusCode == Status.OK) {
			resultList = PizzashackJSONTransformer
					.getAvailableOrderList(responseStr);
		} else {
			throw new Exception(responseStr);
		}
		return resultList;
	}

	@Override
	public void cancelOrder(Long orderId, String token) throws Exception {
		LOGGER.debug("cancelOrder start:{}", orderId);
		LOGGER.debug("token:{}", token);
		WebResource webResource = client.resource(wso2apiConfig.getBaseUrl())
				.path(PIZZA_ORDER_URL + "/" + orderId);
		ClientResponse clientResp = webResource
				.header(AUTH_HEADER, "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

		Status statusCode = clientResp.getClientResponseStatus();
		String responseStr = getResponsePayload(clientResp);

		if (statusCode != Status.OK) {
			throw new Exception(responseStr);
		}
		LOGGER.debug("cancelOrder end:{}");
	}

	@Override
	public OrderDto updateOrder(OrderDto orderDto, String token)
			throws Exception {
		LOGGER.debug("updateOrder start:{}", orderDto);
		OrderDto result = null;
		String jsonString = PizzashackJSONTransformer
				.generateSaveOrderMessage(orderDto);
		WebResource webResource = client.resource(wso2apiConfig.getBaseUrl())
				.path(PIZZA_ORDER_URL + "/" + orderDto.getOrderId());
		ClientResponse clientResp = webResource
				.header(AUTH_HEADER, "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, jsonString);

		Status statusCode = clientResp.getClientResponseStatus();
		String responseStr = getResponsePayload(clientResp);

		if (statusCode == Status.OK) {
			result = PizzashackJSONTransformer.getOrder(responseStr);
		} else {
			throw new Exception(responseStr);
		}
		LOGGER.debug("updateOrder end:{}", result);
		return result;
	}

}
