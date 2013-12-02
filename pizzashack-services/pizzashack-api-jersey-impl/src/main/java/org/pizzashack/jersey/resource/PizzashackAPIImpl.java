package org.pizzashack.jersey.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.pizzashack.GenericAPIError;
import org.pizzashack.data.MenuItemDTO;
import org.pizzashack.data.OrderDTO;
import org.pizzashack.ds.OrderManagerDS;
import org.pizzashack.ds.PizzaMenuDS;
import org.pizzashack.jersey.PizzashackAPIUtils;
import org.pizzashack.resource.PizzashackAPI;
import org.pizzashack.util.PATCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/v1")
public class PizzashackAPIImpl implements PizzashackAPI {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackAPIImpl.class);

	@Autowired
	private OrderManagerDS orderManagerDs;

	@Autowired
	private PizzaMenuDS pizzaMenuDs;

	@Override
	@Path("/menu")
	@GET
	@Produces("application/json")
	public Response getMenu() {
		LOGGER.debug("getMenu start:{}");
		List<MenuItemDTO> resultList = null;
		GenericAPIError genericAPIError = null;
		try {
			resultList = pizzaMenuDs.getAllMenuItems();
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.debug("getMenu end:{}");
		return PizzashackAPIUtils.buildResponse(resultList, genericAPIError);
	}

	@Override
	@PATCH
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/order/delivery/{orderId}")
	public Response updateOrderDeliverStatus(
			@PathParam("orderId") Long orderId,
			@QueryParam("deliverStatus") String status) {
		LOGGER.debug("deliverOrder start:{}", orderId);
		LOGGER.debug("status:{}", status);
		GenericAPIError genericAPIError = null;
		OrderDTO result = null;
		try {
			result = orderManagerDs.updateDeliverStatus(orderId, status);
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.debug("deliverOrder end:{}");
		return PizzashackAPIUtils.buildResponse(result, genericAPIError);
	}

	@Override
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/order")
	public Response placeOrder(OrderDTO order) {
		LOGGER.debug("placeOrder start:{}", order);
		OrderDTO result = null;
		GenericAPIError genericAPIError = null;
		try {
			result = orderManagerDs.placeOrder(order);
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}

		LOGGER.debug("placeOrder end:{}", result);
		return PizzashackAPIUtils.buildResponse(result, genericAPIError);
	}

	@Override
	@Path("/order/{orderId}")
	@GET
	@Produces("application/json")
	public Response getOrderById(@PathParam("orderId") Long orderId) {
		LOGGER.debug("getOrderById start:{}", orderId);
		OrderDTO result = null;
		GenericAPIError genericAPIError = null;
		try {
			result = orderManagerDs.getOrderById(orderId);
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.debug("placeOrder end:{}", result);
		return PizzashackAPIUtils.buildResponse(result, genericAPIError);
	}

	@Override
	@DELETE
	@Produces("application/json")
	@Path("/order/{orderId}")
	public Response cancelOrder(@PathParam("orderId") Long orderId) {
		LOGGER.debug("cancelOrder start:{}", orderId);
		GenericAPIError genericAPIError = null;
		try {
			orderManagerDs.cancelOrder(orderId);
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.debug("cancelOrder end:{}");
		return PizzashackAPIUtils.buildResponse(null, genericAPIError);
	}

	@Override
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/order/{orderId}")
	public Response updateOrder(@PathParam("orderId") Long orderId,
			OrderDTO order) {
		LOGGER.debug("updateOrder start:{}", orderId);
		GenericAPIError genericAPIError = null;
		OrderDTO result = null;
		try {
			result = orderManagerDs.updateOrder(orderId, order);
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.debug("updateOrder end:{}", result);
		return PizzashackAPIUtils.buildResponse(result, genericAPIError);
	}

	@Override
	@GET
	@Produces("application/json")
	@Path("/order/query")
	public Response getOrders(@QueryParam("customerName") String customerName,
			@QueryParam("customerEmail") String customerEmail,
			@QueryParam("delivered") String delivered) {
		LOGGER.debug("getOrders start:{}");
		LOGGER.debug("customerName:{}", customerName);
		LOGGER.debug("customerEmail:{}", customerEmail);
		LOGGER.debug("delivered:{}", delivered);
		List<OrderDTO> orders = null;
		GenericAPIError genericAPIError = null;
		try {
			orders = orderManagerDs.getOrdersByCustomer(customerName,
					customerEmail, delivered);
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.debug("getOrders end:{}");
		return PizzashackAPIUtils.buildResponse(orders, genericAPIError);
	}

}
