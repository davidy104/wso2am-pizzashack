package net.pizzashack.ds;

import net.pizzashack.data.dto.AccountDto;

public interface AccountDS {

	void login(AccountDto accountDto) throws AuthenticationException;

}
