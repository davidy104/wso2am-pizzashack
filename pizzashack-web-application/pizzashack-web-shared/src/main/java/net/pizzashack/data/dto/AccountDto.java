package net.pizzashack.data.dto;

import java.io.Serializable;

import net.pizzashack.data.Token;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
public class AccountDto implements Serializable {

	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	private String customerName;

	private Token accessToken;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Token getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(Token accessToken) {
		this.accessToken = accessToken;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public static Builder getBuilder(String username, String password) {
		return new Builder(username, password);
	}

	public static Builder getBuilder(String username, String password,
			Token token) {
		return new Builder(username, password, token);
	}

	public static class Builder {

		private AccountDto built;

		public Builder(String username, String password) {
			built = new AccountDto();
			built.username = username;
			built.password = password;
		}

		public Builder(String username, String password, Token accessToken) {
			built = new AccountDto();
			built.username = username;
			built.password = password;
			built.accessToken = accessToken;
		}

		public AccountDto build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
