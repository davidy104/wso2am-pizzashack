package net.pizzashack.config;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CamelHttpClientConfig {
	private Long camelHttpTimeout;
	private Long camelRedeliveryDelay;
	private Integer camelMaximumRedeliveries;

	public Long getCamelHttpTimeout() {
		return camelHttpTimeout;
	}

	public void setCamelHttpTimeout(Long camelHttpTimeout) {
		this.camelHttpTimeout = camelHttpTimeout;
	}

	public Long getCamelRedeliveryDelay() {
		return camelRedeliveryDelay;
	}

	public void setCamelRedeliveryDelay(Long camelRedeliveryDelay) {
		this.camelRedeliveryDelay = camelRedeliveryDelay;
	}

	public Integer getCamelMaximumRedeliveries() {
		return camelMaximumRedeliveries;
	}

	public void setCamelMaximumRedeliveries(Integer camelMaximumRedeliveries) {
		this.camelMaximumRedeliveries = camelMaximumRedeliveries;
	}

	public static Builder getBuilder(Long camelHttpTimeout,
			Long camelRedeliveryDelay, Integer camelMaximumRedeliveries) {
		return new Builder(camelHttpTimeout, camelRedeliveryDelay,
				camelMaximumRedeliveries);
	}

	public static class Builder {

		private CamelHttpClientConfig built;

		public Builder(Long camelHttpTimeout, Long camelRedeliveryDelay,
				Integer camelMaximumRedeliveries) {
			built = new CamelHttpClientConfig();
			built.camelHttpTimeout = camelHttpTimeout;
			built.camelRedeliveryDelay = camelRedeliveryDelay;
			built.camelMaximumRedeliveries = camelMaximumRedeliveries;
		}

		public CamelHttpClientConfig build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
