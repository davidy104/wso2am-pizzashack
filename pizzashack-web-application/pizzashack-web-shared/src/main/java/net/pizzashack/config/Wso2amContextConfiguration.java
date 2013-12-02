package net.pizzashack.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({ "wso2api-pizzashack.properties" })
public class Wso2amContextConfiguration {
	private static final String PROPERTY_API_TOKEN_URL = "tokenURL";

	private static final String PROPERTY_API_BASE_URL = "baseURL";

	private static final String PROPERTY_API_CONSUMER_KEY = "consumerKey";

	private static final String PROPERTY_API_CONSUMER_SECRET = "consumerSecret";

	@Resource
	private Environment environment;

	@Bean
	public Wso2amConfig wso2amConfig() {
		return Wso2amConfig.getBuilder(
				environment.getRequiredProperty(PROPERTY_API_TOKEN_URL),
				environment.getRequiredProperty(PROPERTY_API_CONSUMER_KEY),
				environment.getRequiredProperty(PROPERTY_API_CONSUMER_SECRET),
				environment.getRequiredProperty(PROPERTY_API_BASE_URL)).build();
	}

}
