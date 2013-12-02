package net.pizzashack.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.pizzashack.data.dto.AccountDto;
import net.pizzashack.ds.AccountDS;
import net.pizzashack.ds.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoginController.class);

	@Resource
	private AccountDS accountDs;

	private static final String FEEDBACK_MESSAGE_KEY_LOGIN_FAILED = "feedback.message.login.failed";

	private static final String LOGIN_VIEW = "login";

	private static final String INDEX_VIEW = "/index";

	public static final String MODEL_ATTRIBUTE_ACCOUNT = "account";

	public static final String REQUEST_MAPPING_VIEW_CONFIG = "/login";

	public static final String REQUESTED_URL = "REQUESTED_URL";

	@RequestMapping(method = RequestMethod.GET)
	public String showLoginPage(Model model) {
		LOGGER.debug("Rendering login page {}");
		model.addAttribute(MODEL_ATTRIBUTE_ACCOUNT, new AccountDto());
		return LOGIN_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String handleLogin(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_ACCOUNT) AccountDto accountDto,
			BindingResult result, RedirectAttributes redirect,
			HttpSession session) throws AuthenticationException {

		if (result.hasErrors()) {
			LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
			return LOGIN_VIEW;
		}

		try {
			accountDs.login(accountDto);
			LOGGER.debug("after login: {}", accountDto);
			session.setAttribute(MODEL_ATTRIBUTE_ACCOUNT, accountDto);
			String url = (String) session.getAttribute(REQUESTED_URL);
			session.removeAttribute(REQUESTED_URL);
			if (StringUtils.hasText(url) && !url.contains(LOGIN_VIEW)) {
				return createRedirectViewPath(url);
			}
			return createRedirectViewPath(INDEX_VIEW);

		} catch (AuthenticationException ae) {
			addFeedbackMessage(redirect, FEEDBACK_MESSAGE_KEY_LOGIN_FAILED,
					accountDto.getUsername(), accountDto.getPassword());
			return createRedirectViewPath(REQUEST_MAPPING_VIEW_CONFIG);
		}
	}

}
