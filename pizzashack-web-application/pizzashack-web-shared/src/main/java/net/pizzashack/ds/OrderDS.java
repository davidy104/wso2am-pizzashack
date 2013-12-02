package net.pizzashack.ds;

import java.util.List;

import net.pizzashack.data.dto.OrderDto;

public interface OrderDS {

	OrderDto placeOrder(OrderDto orderDto, String token) throws Exception;

	OrderDto getOrder(Long orderId, String token) throws Exception;

	List<OrderDto> getOrders(String customerName, String customerEmail,
			String token) throws Exception;

	void cancelOrder(Long orderId, String token) throws Exception;

	OrderDto updateOrder(OrderDto orderDto, String token) throws Exception;

	OrderDto deliverOrder(Long orderId, String token) throws Exception;
}
