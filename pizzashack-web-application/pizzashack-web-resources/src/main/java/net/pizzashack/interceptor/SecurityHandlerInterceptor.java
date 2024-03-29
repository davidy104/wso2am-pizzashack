package net.pizzashack.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.pizzashack.controller.LoginController;
import net.pizzashack.data.dto.AccountDto;
import net.pizzashack.ds.AuthenticationException;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class SecurityHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		AccountDto account = (AccountDto) WebUtils.getSessionAttribute(request,
				LoginController.MODEL_ATTRIBUTE_ACCOUNT);

		if (account == null) {
			// Retrieve and store the original URL.
			String url = request.getRequestURL().toString();
			WebUtils.setSessionAttribute(request,
					LoginController.REQUESTED_URL, url);
			throw new AuthenticationException("Authentication required.",
					"authentication.required");
		}
		return true;
	}

}
