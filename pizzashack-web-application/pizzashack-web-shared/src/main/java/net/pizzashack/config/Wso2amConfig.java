package net.pizzashack.config;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Wso2amConfig {

	private String tokenUrl;

	private String consumerKey;

	private String consumerSecret;

	private String baseUrl;

	private String defaultUsername;

	private String defaultPassword;

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getDefaultUsername() {
		return defaultUsername;
	}

	public void setDefaultUsername(String defaultUsername) {
		this.defaultUsername = defaultUsername;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	public static Builder getBuilder(String tokenUrl, String consumerKey,
			String consumerSecret, String baseUrl) {
		return new Builder(tokenUrl, consumerKey, consumerSecret, baseUrl);
	}

	public static Builder getBuilder(String defaultUsername,
			String defaultPassword, String tokenUrl, String consumerKey,
			String consumerSecret, String baseUrl) {
		return new Builder(defaultUsername, defaultPassword, tokenUrl,
				consumerKey, consumerSecret, baseUrl);
	}

	public static class Builder {

		private Wso2amConfig built;

		public Builder(String tokenUrl, String consumerKey,
				String consumerSecret, String baseUrl) {
			built = new Wso2amConfig();
			built.tokenUrl = tokenUrl;
			built.consumerKey = consumerKey;
			built.consumerSecret = consumerSecret;
			built.baseUrl = baseUrl;
		}

		public Builder(String defaultUsername, String defaultPassword,
				String tokenUrl, String consumerKey, String consumerSecret,
				String baseUrl) {
			built = new Wso2amConfig();
			built.tokenUrl = tokenUrl;
			built.consumerKey = consumerKey;
			built.consumerSecret = consumerSecret;
			built.baseUrl = baseUrl;
			built.defaultUsername = defaultUsername;
			built.defaultPassword = defaultPassword;
		}

		public Wso2amConfig build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
