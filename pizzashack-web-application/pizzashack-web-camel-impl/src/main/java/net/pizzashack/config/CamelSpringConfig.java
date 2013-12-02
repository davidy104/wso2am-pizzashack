package net.pizzashack.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.inject.Inject;

import net.pizzashack.camel.mappings.PizzashackGsonDataFormat;
import net.pizzashack.camel.route.AccountRoute;
import net.pizzashack.camel.route.MenuRoute;
import net.pizzashack.camel.route.OrderBatchRoute;
import net.pizzashack.camel.route.OrderRoute;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ThreadPoolRejectedPolicy;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.spi.ThreadPoolProfile;
import org.apache.camel.spring.CamelBeanPostProcessor;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.google.gson.ExclusionStrategy;

@Configuration
@PropertySource({"camelClient.properties",
		"classpath:activitymq-config.properties"})
public class CamelSpringConfig {

	@Inject
	private ApplicationContext context;

	@Autowired
	private OrderRoute orderRoute;

	@Autowired
	private MenuRoute menuRoute;

	@Autowired
	private AccountRoute accountRoute;

	@Autowired
	private OrderBatchRoute orderBatchRoute;

	private static final String PROPERTY_CAMEL_HTTPTIMEOUT = "httpTimeout";

	private static final String PROPERTY_CAMEL_REDELIVERY_DELAY = "camelRedeliveryDelay";

	private static final String PROPERTY_CAMEL_MAXIMUM_REDELIVERIES = "camelMaximumRedeliveries";

	@Autowired
	private PooledConnectionFactory pooledConnectionFactory;

	private static final String ACTIVITYMQ_URL = "activitymq_url";

	private static final String ACTIVITYMQ_TRANSACTED = "activitymq_transacted";

	private static final String ACTIVITYMQ_MAXCONNECTIONS = "activitymq_maxConnections";

	private static final String ACTIVITYMQ_SENDTIMEOUT = "activitymq_sendTimeoutInMillis";

	private static final String ACTIVITYMQ_WATCHTOPICADVISORIES = "activitymq_watchTopicAdvisories";

	@Resource
	private Environment environment;

	@Bean
	public CamelHttpClientConfig camelHttpConfig() {
		return CamelHttpClientConfig
				.getBuilder(
						Long.valueOf(environment
								.getRequiredProperty(PROPERTY_CAMEL_HTTPTIMEOUT)),
						Long.valueOf(environment
								.getRequiredProperty(PROPERTY_CAMEL_REDELIVERY_DELAY)),
						Integer.valueOf(environment
								.getRequiredProperty(PROPERTY_CAMEL_MAXIMUM_REDELIVERIES)))
				.build();

	}

	@Bean
	public CamelBeanPostProcessor camelBeanPostProcessor() {
		CamelBeanPostProcessor camelBeanPostProcessor = new CamelBeanPostProcessor();
		camelBeanPostProcessor.setApplicationContext(context);
		return camelBeanPostProcessor;
	}

	@Bean(initMethod = "initialize")
	public PizzashackGsonDataFormat pizzashackGsonDataFormat() {
		List<ExclusionStrategy> exclusionStrategies = new ArrayList<ExclusionStrategy>();
		exclusionStrategies.add(new JsonAnnotationExclusionStrategy());
		return new PizzashackGsonDataFormat(exclusionStrategies);
	}

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				environment.getRequiredProperty(ACTIVITYMQ_URL));
		connectionFactory.setSendTimeout(Integer.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_SENDTIMEOUT)));
		connectionFactory.setMaxThreadPoolSize(5);
		connectionFactory.setWatchTopicAdvisories(Boolean.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_WATCHTOPICADVISORIES)));
		connectionFactory.setUseDedicatedTaskRunner(false);
		return connectionFactory;
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public PooledConnectionFactory pooledConnectionFactory() {
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setMaxConnections(Integer.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_MAXCONNECTIONS)));
		pooledConnectionFactory
				.setConnectionFactory(activeMQConnectionFactory());
		return pooledConnectionFactory;
	}

	@Bean
	public JmsComponent jmsComponent() {
		JmsComponent jmsComponent = new JmsComponent();
		JmsConfiguration jmsConfiguration = new JmsConfiguration();
		jmsConfiguration.setConnectionFactory(pooledConnectionFactory);
		jmsConfiguration.setTransacted(Boolean.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_TRANSACTED)));
		jmsConfiguration.setCacheLevelName("CACHE_CONSUMER");
		jmsComponent.setConfiguration(jmsConfiguration);

		return jmsComponent;
	}

	@Bean
	public CamelContext camelContext() throws Exception {
		SpringCamelContext camelContext = new SpringCamelContext(context);
		camelContext.addComponent("jms", jmsComponent());
		ThreadPoolProfile profile = new ThreadPoolProfile();
		profile.setId("genericThreadPool");
		profile.setKeepAliveTime(120L);
		profile.setPoolSize(2);
		profile.setMaxPoolSize(10);
		profile.setTimeUnit(TimeUnit.SECONDS);
		profile.setRejectedPolicy(ThreadPoolRejectedPolicy.Abort);
		camelContext.getExecutorServiceManager().registerThreadPoolProfile(
				profile);

		camelContext.addRoutes(orderRoute);
		camelContext.addRoutes(accountRoute);
		camelContext.addRoutes(menuRoute);
		camelContext.addRoutes(orderBatchRoute);
		return camelContext;
	}
}
