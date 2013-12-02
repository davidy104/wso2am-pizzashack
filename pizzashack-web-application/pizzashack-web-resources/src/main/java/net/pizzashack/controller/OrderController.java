package net.pizzashack.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.pizzashack.data.dto.AccountDto;
import net.pizzashack.data.dto.OrderDto;
import net.pizzashack.ds.NotFoundException;
import net.pizzashack.ds.OrderDS;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderController.class);

	private static final String MODEL_ATTRIBUTE_ORDER = "order";

	public static final String MODEL_ATTRIBUTE_ORDERS = "orders";

	private static final String PLACE_ORDER_VIEW = "placeOrder";

	private static final String FEEDBACK_MESSAGE_KEY_PIZZA_ORDERED = "feedback.message.pizza.ordered";

	private static final String FEEDBACK_MESSAGE_KEY_ORDER_CANCELED = "feedback.message.order.canceled";

	private static final String FEEDBACK_MESSAGE_KEY_ORDER_UPDATED = "feedback.message.order.updated";

	private static final String REQUEST_MAPPING_VIEW_CONFIG = "/order/{orderId}";

	private static final String REQUEST_INDEX_MAPPING_VIEW_CONFIG = "/index";

	private static final String ORDER_VIEW = "order";

	private static final String FIND_VIEW = "findOrder";

	private static final String ORDERS_VIEW = "orders";

	private static final String UPDATE_ORDER_VIEW = "uptOrder";

	private static final String PARAMETER_ORDER_ID = "orderId";

	@Resource
	private OrderDS orderDs;

	@RequestMapping(value = "/place/{pizzaType}", method = RequestMethod.GET)
	public String showPlaceOrder(@PathVariable("pizzaType") String pizzaType,
			Model model) throws Exception {
		LOGGER.debug("showPlaceOrder: {}", pizzaType);
		OrderDto orderDto = new OrderDto();
		orderDto.setPizzaType(pizzaType);
		model.addAttribute(MODEL_ATTRIBUTE_ORDER, orderDto);
		return PLACE_ORDER_VIEW;
	}

	@RequestMapping(value = "/place", method = RequestMethod.POST)
	public String placeOrder(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_ORDER) OrderDto orderDto,
			BindingResult result, RedirectAttributes redirect,
			HttpSession session) throws Exception {
		LOGGER.debug("place order start:{}", orderDto);

		if (result.hasErrors()) {
			LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
			return PLACE_ORDER_VIEW;
		}

		AccountDto account = (AccountDto) session
				.getAttribute(LoginController.MODEL_ATTRIBUTE_ACCOUNT);
		String accessToken = account.getAccessToken().getAccessToken();

		OrderDto resultDto = orderDs.placeOrder(orderDto, accessToken);

		LOGGER.debug("result got: {}", resultDto);

		addFeedbackMessage(redirect, FEEDBACK_MESSAGE_KEY_PIZZA_ORDERED,
				resultDto.getOrderId());
		return createRedirectViewPath(REQUEST_INDEX_MAPPING_VIEW_CONFIG);
	}

	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	public String showMyOrderPage(@PathVariable("orderId") Long orderId,
			Model model, HttpSession session) throws Exception {
		LOGGER.debug("Showing order page for order with orderId: {}", orderId);
		AccountDto account = (AccountDto) session
				.getAttribute(LoginController.MODEL_ATTRIBUTE_ACCOUNT);
		String accessToken = account.getAccessToken().getAccessToken();
		OrderDto found = orderDs.getOrder(orderId, accessToken);

		LOGGER.debug("Found order: {}", found);

		model.addAttribute(MODEL_ATTRIBUTE_ORDER, found);
		return ORDER_VIEW;
	}

	@RequestMapping(value = "/myOrders", method = RequestMethod.GET)
	public String showOrdersPage(Model model, HttpSession session)
			throws Exception {
		LOGGER.debug("showOrdersPage: {}");
		model.addAttribute(MODEL_ATTRIBUTE_ORDER, new OrderDto());
		return FIND_VIEW;
	}

	@RequestMapping(value = "/findOrders", method = RequestMethod.POST)
	public String findOrders(
			@ModelAttribute(MODEL_ATTRIBUTE_ORDER) OrderDto orderDto,
			BindingResult result, Model model, HttpSession session)
			throws Exception {
		LOGGER.debug("place order start:{}", orderDto);

		AccountDto account = (AccountDto) session
				.getAttribute(LoginController.MODEL_ATTRIBUTE_ACCOUNT);
		String accessToken = account.getAccessToken().getAccessToken();
		List<OrderDto> orderList = null;
		if (!StringUtils.isEmpty(orderDto.getCustomerName())) {
			orderList = orderDs.getOrders(orderDto.getCustomerName(),
					orderDto.getCustomerEmail(), accessToken);
			LOGGER.debug("orderList size: {}", orderList.size());
		}
		model.addAttribute(MODEL_ATTRIBUTE_ORDERS, orderList);
		return ORDERS_VIEW;
	}

	/**
	 * Cancel Order
	 *
	 * @param orderId
	 * @param session
	 * @return
	 * @throws NotFoundException
	 */
	@RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteConfig(@PathVariable("orderId") Long orderId,
			HttpSession session) throws Exception {
		LOGGER.debug("Deleting order with id: {}", orderId);
		AccountDto account = (AccountDto) session
				.getAttribute(LoginController.MODEL_ATTRIBUTE_ACCOUNT);
		String accessToken = account.getAccessToken().getAccessToken();
		orderDs.cancelOrder(orderId, accessToken);

		return getMessage(FEEDBACK_MESSAGE_KEY_ORDER_CANCELED, orderId);
	}

	@RequestMapping(value = "/update/{orderId}", method = RequestMethod.GET)
	public String showUpdateConfigPage(@PathVariable("orderId") Long orderId,
			Model model, HttpSession session) throws Exception {
		LOGGER.debug("Showing edit order with id: {}", orderId);

		AccountDto account = (AccountDto) session
				.getAttribute(LoginController.MODEL_ATTRIBUTE_ACCOUNT);
		String accessToken = account.getAccessToken().getAccessToken();
		OrderDto found = orderDs.getOrder(orderId, accessToken);

		LOGGER.debug("Found order: {}", found);
		model.addAttribute(MODEL_ATTRIBUTE_ORDER, found);
		return UPDATE_ORDER_VIEW;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateOrder(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_ORDER) OrderDto orderDto,
			BindingResult result, RedirectAttributes attributes,
			HttpSession session) throws Exception {
		LOGGER.debug("Updating orderDto with information: {}", orderDto);

		if (result.hasErrors()) {
			LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
			return UPDATE_ORDER_VIEW;
		}
		AccountDto account = (AccountDto) session
				.getAttribute(LoginController.MODEL_ATTRIBUTE_ACCOUNT);
		String accessToken = account.getAccessToken().getAccessToken();
		OrderDto updated = orderDs.updateOrder(orderDto, accessToken);
		LOGGER.debug("Updated order with information: {}", updated);

		attributes.addAttribute(PARAMETER_ORDER_ID, updated.getOrderId());
		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ORDER_UPDATED,
				updated.getOrderId());

		return createRedirectViewPath(REQUEST_MAPPING_VIEW_CONFIG);
	}

	@RequestMapping(value = "/deliver/{orderId}", method = RequestMethod.PUT)
	public String deliverOrder(@PathVariable("orderId") Long orderId,
			HttpSession session) throws Exception {
		LOGGER.debug("deliverOrder start: {}", orderId);
		AccountDto account = (AccountDto) session
				.getAttribute(LoginController.MODEL_ATTRIBUTE_ACCOUNT);
		String accessToken = account.getAccessToken().getAccessToken();
		OrderDto delivered = orderDs.deliverOrder(orderId, accessToken);
		LOGGER.debug("deliver order end: {}", delivered);
		return getMessage(FEEDBACK_MESSAGE_KEY_ORDER_CANCELED);
		// return createRedirectViewPath(REQUEST_MAPPING_VIEW_CONFIG);
	}

}
