package org.pizzashack.jersey.config;

import org.pizzashack.ds.config.InfrastructureContextConfiguration;
import org.pizzashack.ds.config.TestDataContextConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"org.pizzashack"})
@Import({InfrastructureContextConfiguration.class,
		TestDataContextConfiguration.class})
public class ApplicationConfig {

}
