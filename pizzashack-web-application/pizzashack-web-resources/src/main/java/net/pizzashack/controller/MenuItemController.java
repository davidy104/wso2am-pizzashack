package net.pizzashack.controller;

import java.util.List;

import javax.annotation.Resource;

import net.pizzashack.data.Pizza;
import net.pizzashack.data.Token;
import net.pizzashack.data.dto.AccountDto;
import net.pizzashack.ds.MenuItemDS;
import net.pizzashack.support.SessionAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/index")
@SessionAttributes(types = AccountDto.class)
public class MenuItemController extends BaseController {

	public static final String INDEX_VIEW = "index";

	protected static final String MODEL_ATTRIBUTE_PIZZAS = "pizzas";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MenuItemController.class);

	@Resource
	private MenuItemDS menuItemDs;

	@RequestMapping(method = RequestMethod.GET)
	public String index(
			Model model,
			@SessionAttribute(value = LoginController.MODEL_ATTRIBUTE_ACCOUNT, exposeAsModelAttribute = true) AccountDto account)
			throws Exception {
		LOGGER.debug("index: {}");
		Token token = account.getAccessToken();
		LOGGER.debug("token: {}", token);
		List<Pizza> pizzaList = menuItemDs.getAvailablePizzaList(token
				.getAccessToken());
		model.addAttribute(MODEL_ATTRIBUTE_PIZZAS, pizzaList);
		return INDEX_VIEW;
	}
}
