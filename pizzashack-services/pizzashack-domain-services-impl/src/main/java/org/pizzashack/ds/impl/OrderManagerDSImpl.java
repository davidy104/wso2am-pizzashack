package org.pizzashack.ds.impl;

import static org.pizzashack.data.predicates.CustomerPredicates.findByNameAndEmail;
import static org.pizzashack.data.predicates.OrderPredicates.findDeliveredByCustomer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.pizzashack.NotFoundException;
import org.pizzashack.data.Customer;
import org.pizzashack.data.Order;
import org.pizzashack.data.OrderDTO;
import org.pizzashack.data.repository.CustomerRepository;
import org.pizzashack.data.repository.OrderRepository;
import org.pizzashack.ds.OrderManagerDS;
import org.pizzashack.ds.converter.GeneralConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("orderManagerDS")
@Transactional(readOnly = true)
public class OrderManagerDSImpl implements OrderManagerDS {
	@Resource
	private OrderRepository orderRepository;

	@Resource
	private CustomerRepository customerRepository;

	// @Autowired
	// private PlaceOrderProducer placeOrderProducer;

	@Autowired
	private GeneralConverter<OrderDTO, Order> orderConverter;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderManagerDSImpl.class);

	@Transactional(readOnly = false)
	@Override
	public OrderDTO placeOrder(OrderDTO orderDto) throws Exception {
		LOGGER.debug("placeOrder start:{}", orderDto);
		Order order = orderConverter.convertTo(orderDto);
		LOGGER.debug("after convert:{}", order);
		Customer customer = order.getCustomer();
		Customer existedCustomer = customerRepository
				.findOne(findByNameAndEmail(customer.getCustomerName(),
						customer.getEmail()));

		if (existedCustomer == null) {
			customerRepository.save(customer);
			order.setCustomer(customer);
		} else {
			order.setCustomer(existedCustomer);
		}

		LOGGER.debug("customerId: {}", customer.getCustId());
		order = orderRepository.save(order);
		orderDto = orderConverter.convertFrom(order);
		LOGGER.debug("placeOrder end:{}", orderDto);
		return orderDto;
	}

	// @Override
	// public OrderDTO placeOrder(OrderDTO orderDto) throws Exception {
	// LOGGER.debug("placeOrder start:{}", orderDto);
	// Map<String, Object> resultMap = this.doPlaceOrder(orderDto);
	// OrderDTO added = (OrderDTO) resultMap.get("OrderDTO");
	// LOGGER.debug("after persist Order:{}", added);
	// boolean ifCustomerExisted = (Boolean) resultMap
	// .get("ifCustomerExisted");
	// LOGGER.debug("ifCustomerExisted:{}", ifCustomerExisted);
	// boolean sendCustRequired = !ifCustomerExisted;
	// LOGGER.debug("sendCustRequired:{}", sendCustRequired);
	// placeOrderProducer.sendOrder(added, sendCustRequired);
	//
	// LOGGER.debug("placeOrder end:{}", added);
	// return added;
	// }
	//
	// @Transactional(readOnly = false)
	// private Map<String, Object> doPlaceOrder(OrderDTO orderDto)
	// throws Exception {
	// LOGGER.debug("placeOrder start:{}", orderDto);
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	// boolean ifCustomerExisted = false;
	// Order order = orderConverter.convertTo(orderDto);
	// LOGGER.debug("after convert:{}", order);
	// Customer customer = order.getCustomer();
	// Customer existedCustomer = customerRepository
	// .findOne(findByNameAndEmail(customer.getCustomerName(),
	// customer.getEmail()));
	//
	// if (existedCustomer == null) {
	// customerRepository.save(customer);
	// order.setCustomer(customer);
	// } else {
	// ifCustomerExisted = true;
	// order.setCustomer(existedCustomer);
	// }
	//
	// LOGGER.debug("customerId: {}", customer.getCustId());
	// order = orderRepository.save(order);
	// orderDto = orderConverter.convertFrom(order);
	// LOGGER.debug("placeOrder end:{}", orderDto);
	//
	// resultMap.put("ifCustomerExisted", ifCustomerExisted);
	// resultMap.put("OrderDTO", orderDto);
	//
	// return resultMap;
	// }

	@Override
	public OrderDTO getOrderById(Long orderId) throws Exception {
		LOGGER.debug("getOrderById start:{}", orderId);
		OrderDTO result = null;
		Order found = this.getOrderModelById(orderId);
		result = orderConverter.convertFrom(found);
		LOGGER.debug("getOrderById end:{}", result);
		return result;
	}

	private Order getOrderModelById(Long orderId) throws NotFoundException {
		Order found = orderRepository.findOne(orderId);
		if (found == null) {
			LOGGER.debug("No Order found with id: {}", orderId);
			throw new NotFoundException("No Order found with id: " + orderId);
		}
		return found;
	}

	@Override
	@Transactional(readOnly = false)
	public OrderDTO updateOrder(Long orderId, OrderDTO orderDto)
			throws Exception {
		LOGGER.debug("updateOrder start:{}", orderDto);
		Customer customer = null;
		OrderDTO result = null;
		Order found = this.getOrderModelById(orderId);

		found.update(orderDto.getPizzaType(), orderDto.getQuantity(),
				orderDto.getDelivered());

		String custEmail = orderDto.getCustomerEmail();
		String custName = orderDto.getCustomerName();

		if (!StringUtils.isEmpty(custEmail) && !StringUtils.isEmpty(custName)) {
			customer = customerRepository.findOne(findByNameAndEmail(custName,
					custEmail));

			if (customer == null) {
				customer = Customer.getBuilder(custName,
						orderDto.getCreditCardNumber(), orderDto.getAddress(),
						custEmail).build();
				customer = customerRepository.save(customer);
			}
			found.setCustomer(customer);
		}
		result = this.orderConverter.convertFrom(found);
		LOGGER.debug("updateOrder end:{}", result);
		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public void cancelOrder(Long orderId) throws Exception {
		LOGGER.debug("cancelOrder start:{}", orderId);
		Order found = this.getOrderModelById(orderId);
		orderRepository.delete(found);
		LOGGER.debug("cancelOrder end:{}");
	}

	@Override
	@Transactional(readOnly = false)
	public OrderDTO updateDeliverStatus(Long orderId, String status)
			throws Exception {
		LOGGER.debug("deliverOrder start:{}", orderId);
		OrderDTO result = null;
		Order found = this.getOrderModelById(orderId);
		found.updateDelivered(status);
		result = this.orderConverter.convertFrom(found);
		LOGGER.debug("deliverOrder end:{}", result);
		return result;
	}

	@Override
	public List<OrderDTO> getOrdersByCustomer(String customerName,
			String email, String delivered) throws Exception {
		LOGGER.debug("getOrdersByCustomer start:{}");
		LOGGER.debug("customerName:{}", customerName);
		LOGGER.debug("email:{}", email);
		LOGGER.debug("delivered:{}", delivered);
		List<OrderDTO> orderDTOList = null;

		Iterator<Order> orderIter = orderRepository.findAll(
				findDeliveredByCustomer(customerName, email, delivered))
				.iterator();

		if (orderIter != null) {
			orderDTOList = new ArrayList<OrderDTO>();
			while (orderIter.hasNext()) {
				Order order = orderIter.next();
				OrderDTO orderDto = orderConverter.convertFrom(order);
				orderDTOList.add(orderDto);
			}
		}
		LOGGER.debug("getOrdersByCustomer end:{}");
		return orderDTOList;
	}

	@Override
	public List<OrderDTO> listOrders() throws Exception {
		List<OrderDTO> orderDtoList = null;
		List<Order> orderList = orderRepository.findAll();
		if (orderList != null && orderList.size() > 0) {
			orderDtoList = new ArrayList<OrderDTO>();
			for (Order order : orderList) {
				orderDtoList.add(orderConverter.convertFrom(order));
			}
		}

		return orderDtoList;
	}

}
