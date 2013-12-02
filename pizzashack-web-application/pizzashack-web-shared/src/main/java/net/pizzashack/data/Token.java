package net.pizzashack.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Token {
	private String tokenType;
	private long expiresIn;
	private String refreshToken;
	private String accessToken;

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public static Builder getBuilder(String tokenType, long expiresIn,
			String refreshToken, String accessToken) {
		return new Builder(tokenType, expiresIn, refreshToken, accessToken);
	}

	public static class Builder {

		private Token built;

		public Builder(String tokenType, long expiresIn, String refreshToken,
				String accessToken) {
			built = new Token();
			built.tokenType = tokenType;
			built.expiresIn = expiresIn;
			built.refreshToken = refreshToken;
			built.accessToken = accessToken;
		}

		public Token build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
