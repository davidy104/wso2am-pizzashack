package net.pizzashack.ds;

import net.pizzashack.data.Token;

public interface OauthTokenDS {
	Token getToken(String username, String password) throws Exception;
}
