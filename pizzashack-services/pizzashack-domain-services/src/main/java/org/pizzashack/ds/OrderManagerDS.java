package org.pizzashack.ds;

import java.util.List;

import org.pizzashack.data.OrderDTO;

public interface OrderManagerDS {

	OrderDTO placeOrder(OrderDTO orderDto) throws Exception;

	OrderDTO getOrderById(Long orderId) throws Exception;

	OrderDTO updateOrder(Long orderId, OrderDTO orderDto) throws Exception;

	void cancelOrder(Long orderId) throws Exception;

	OrderDTO updateDeliverStatus(Long orderId, String status) throws Exception;

	List<OrderDTO> getOrdersByCustomer(String customerName, String email,
			String delivered) throws Exception;

	List<OrderDTO> listOrders() throws Exception;
}
