package org.pizzashack.resource;

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

import org.pizzashack.data.OrderDTO;
import org.pizzashack.util.PATCH;

/**
 *
 * @author david
 *
 */
@Path("/v1")
public interface PizzashackAPI {

	@Path("/menu")
	@GET
	@Produces("application/json")
	Response getMenu();

	@PATCH
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/order/delivery/{orderId}")
	Response updateOrderDeliverStatus(@PathParam("orderId") Long orderId,
			@QueryParam("deliverStatus") String status);

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/order")
	Response placeOrder(OrderDTO order);

	@Path("/order/{orderId}")
	@GET
	@Produces("application/json")
	Response getOrderById(@PathParam("orderId") Long orderId);

	@DELETE
	@Produces("application/json")
	@Path("/order/{orderId}")
	Response cancelOrder(@PathParam("orderId") Long orderId);

	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/order/{orderId}")
	Response updateOrder(@PathParam("orderId") Long orderId, OrderDTO order);

	@GET
	@Produces("application/json")
	@Path("/order/query")
	Response getOrders(@QueryParam("customerName") String customerName,
			@QueryParam("customerEmail") String customerEmail,
			@QueryParam("delivered") String delivered);
}
