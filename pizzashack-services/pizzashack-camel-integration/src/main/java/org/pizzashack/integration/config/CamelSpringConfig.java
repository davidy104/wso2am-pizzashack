package org.pizzashack.integration.config;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.inject.Inject;

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

@Configuration
@PropertySource("classpath:activitymq-config.properties")
public class CamelSpringConfig {

	@Inject
	private ApplicationContext context;

	@Autowired
	private PooledConnectionFactory pooledConnectionFactory;

	@Resource
	private Environment environment;

	private static final String ACTIVITYMQ_URL = "activitymq_url";

	private static final String ACTIVITYMQ_TRANSACTED = "activitymq_transacted";

	private static final String ACTIVITYMQ_MAXCONNECTIONS = "activitymq_maxConnections";

	private static final String ACTIVITYMQ_SENDTIMEOUT = "activitymq_sendTimeoutInMillis";

	private static final String ACTIVITYMQ_WATCHTOPICADVISORIES = "activitymq_watchTopicAdvisories";

	@Bean
	public CamelBeanPostProcessor camelBeanPostProcessor() {
		CamelBeanPostProcessor camelBeanPostProcessor = new CamelBeanPostProcessor();
		camelBeanPostProcessor.setApplicationContext(context);
		return camelBeanPostProcessor;
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
		// jmsConfiguration.setTransactionManager(jmsTransactionManager());
		jmsConfiguration.setTransacted(Boolean.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_TRANSACTED)));
		jmsConfiguration.setCacheLevelName("CACHE_CONSUMER");
		jmsComponent.setConfiguration(jmsConfiguration);

		return jmsComponent;
	}

	@Bean(name = "camelContext")
	public CamelContext camelContext() throws Exception {
		SpringCamelContext camelContext = new SpringCamelContext(context);
		camelContext.addComponent("jms", jmsComponent());
		camelContext.getExecutorServiceManager().registerThreadPoolProfile(
				initCustThreadPoolProfile());
		return camelContext;
	}

	private ThreadPoolProfile initCustThreadPoolProfile() {
		ThreadPoolProfile profile = new ThreadPoolProfile();
		profile.setId("genericThreadPool");
		profile.setKeepAliveTime(120L);
		profile.setPoolSize(2);
		profile.setMaxPoolSize(10);
		profile.setTimeUnit(TimeUnit.SECONDS);
		profile.setRejectedPolicy(ThreadPoolRejectedPolicy.Abort);
		return profile;
	}

	// @Bean
	// public JmsTransactionManager jmsTransactionManager() {
	// JmsTransactionManager jmsTransactionManager = new
	// JmsTransactionManager();
	// jmsTransactionManager.setConnectionFactory(pooledConnectionFactory);
	// return jmsTransactionManager;
	// }

}
