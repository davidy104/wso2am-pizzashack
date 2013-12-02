package net.pizzashack.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.pizzashack.data.Pizza;
import net.pizzashack.data.Token;
import net.pizzashack.data.dto.OrderDto;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PizzashackJSONTransformer {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackJSONTransformer.class);

	/**
	 * @param pizzaContents
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Pizza> getAvailablePizzaList(String pizzaContents) {
		List<Pizza> pizzaList = null;
		if (!StringUtils.isEmpty(pizzaContents)) {
			JSONParser parser = new JSONParser();
			pizzaList = new ArrayList<Pizza>();
			try {
				Object obj = parser.parse(pizzaContents);

				JSONArray array = (JSONArray) obj;
				Iterator<JSONObject> iterator = array.iterator();
				Pizza pizza = null;
				while (iterator.hasNext()) {
					pizza = new Pizza();
					JSONObject pizzaItem = iterator.next();
					pizza.setName((String) pizzaItem.get("name"));
					pizza.setDescription((String) pizzaItem.get("description"));
					pizza.setImageUrl((String) pizzaItem.get("icon"));
					double price = Double.valueOf(
							(String) pizzaItem.get("price")).doubleValue();

					pizza.setPrice(price);
					pizzaList.add(pizza);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return pizzaList;
	}

	@SuppressWarnings("unchecked")
	public static List<OrderDto> getAvailableOrderList(String orderContents) {
		JSONParser parser = new JSONParser();
		List<OrderDto> orderList = new ArrayList<OrderDto>();
		try {
			Object obj = parser.parse(orderContents);

			JSONArray array = (JSONArray) obj;
			LOGGER.debug("array size:{}", array.size());
			Iterator<JSONObject> iterator = array.iterator();
			OrderDto order;
			while (iterator.hasNext()) {
				order = new OrderDto();
				JSONObject jsonObject = iterator.next();
				order.setAddress((String) jsonObject.get("address"));
				int quantity = ((Long) jsonObject.get("quantity")).intValue();
				order.setQuantity(quantity);
				order.setCustomerName((String) jsonObject.get("customerName"));
				order.setCustomerEmail((String) jsonObject.get("customerEmail"));
				if (jsonObject.get("custId") != null) {
					order.setCustId((Long) jsonObject.get("custId"));
				}

				order.setCreditCardNumber((String) jsonObject
						.get("creditCardNumber"));
				String delivered = (String) jsonObject.get("delivered");
				order.setDelivered(delivered);
				order.setPizzaType((String) jsonObject.get("pizzaType"));
				order.setOrderId((Long) jsonObject.get("orderId"));
				orderList.add(order);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	/**
	 * Constructs a JSON String similar to:
	 * {"address":"59 Flower Road","pizzaType"
	 * :"Pizza1","quantity":1,"customerName":"Hiranya",
	 * "creditCardNumber":"123456","delivered":false}
	 * 
	 * @param order
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String generateSaveOrderMessage(OrderDto order) {
		JSONObject orderJSON = new JSONObject();

		orderJSON.put("address", order.getAddress());
		orderJSON.put("pizzaType", order.getPizzaType());
		orderJSON.put("quantity", order.getQuantity());
		orderJSON.put("customerName", order.getCustomerName());
		orderJSON.put("customerEmail", order.getCustomerEmail());
		if (order.getCustId() != null) {
			orderJSON.put("custId", order.getCustId());
		}
		if (order.getOrderId() != null) {
			orderJSON.put("orderId", order.getOrderId());
		}
		orderJSON.put("creditCardNumber", order.getCreditCardNumber());
		return orderJSON.toJSONString();
	}

	/**
	 * Returns a populated Order object when a JSON message similar to following
	 * is passed. { "address": "Malpara", "quantity": 2, "customerName":
	 * "Ritigala Jayasena", "creditCardNumber": "3442-3453-5643-2334",
	 * "delivered": false, "pizzaType": "Vegidelight", "orderId":
	 * "b06fe761-c95e-4b55-91ff-7e1f8ea8f3a0" }
	 * 
	 * @param orderJson
	 * @return
	 */
	public static OrderDto getOrder(String orderJson) {
		JSONParser parser = new JSONParser();

		OrderDto order = new OrderDto();
		try {
			Object obj = parser.parse(orderJson);
			JSONObject jsonObject = (JSONObject) obj;
			order.setAddress((String) jsonObject.get("address"));
			int quantity = ((Long) jsonObject.get("quantity")).intValue();
			order.setQuantity(quantity);
			order.setCustomerName((String) jsonObject.get("customerName"));
			order.setCustomerEmail((String) jsonObject.get("customerEmail"));
			if (jsonObject.get("custId") != null) {
				order.setCustId((Long) jsonObject.get("custId"));
			}

			order.setCreditCardNumber((String) jsonObject
					.get("creditCardNumber"));
			String delivered = (String) jsonObject.get("delivered");
			order.setDelivered(delivered);
			order.setPizzaType((String) jsonObject.get("pizzaType"));
			order.setOrderId((Long) jsonObject.get("orderId"));
			return order;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Populates Token object using folloing JSON String { "token_type":
	 * "bearer", "expires_in": 3600000, "refresh_token":
	 * "f43de118a489d56c3b3b7ba77a1549e", "access_token":
	 * "269becaec9b8b292906b3f9e69b5a9" }
	 * 
	 * @param accessTokenJson
	 * @return
	 */
	public static Token getAccessToken(String accessTokenJson) {
		JSONParser parser = new JSONParser();

		Token token = new Token();
		try {
			Object obj = parser.parse(accessTokenJson);
			JSONObject jsonObject = (JSONObject) obj;
			token.setAccessToken((String) jsonObject.get("access_token"));
			long expiresIn = ((Long) jsonObject.get("expires_in")).intValue();
			token.setExpiresIn(expiresIn);
			token.setRefreshToken((String) jsonObject.get("refresh_token"));
			token.setTokenType((String) jsonObject.get("token_type"));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;

	}
}
