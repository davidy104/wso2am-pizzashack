package net.pizzashack.ds.impl;

import net.pizzashack.data.Token;
import net.pizzashack.data.dto.AccountDto;
import net.pizzashack.ds.AccountDS;
import net.pizzashack.ds.AuthenticationException;
import net.pizzashack.ds.JerseyClientSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("accountDs")
public class AccountDSImpl extends JerseyClientSupport implements AccountDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountDSImpl.class);

	@Override
	public void login(AccountDto accountDto) throws AuthenticationException {
		LOGGER.debug("login start:{}", accountDto);

		Token token = null;
		try {
			token = getToken(accountDto.getUsername(), accountDto.getPassword());
			LOGGER.debug("token: {} " + token);
			if (token == null) {
				throw new AuthenticationException(
						"AuthenticationException fail.");
			}
		} catch (Exception e) {
			throw new AuthenticationException("AuthenticationException fail.");
		}
		accountDto.setAccessToken(token);
	}

}
