package org.pizzashack.ds.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.pizzashack.data.support.InitialDataSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class TestDataContextConfiguration {

	@Autowired
	private JpaTransactionManager transactionManager;

	@Bean(initMethod = "initialize")
	public InitialDataSetup setupData() {
		return new InitialDataSetup(new TransactionTemplate(
				this.transactionManager));
	}

	@Bean(initMethod = "start", destroyMethod = "shutdown")
	@DependsOn("dataSource")
	public Server dataSourceTcpConnector() {
		try {
			return Server.createTcpServer();
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		}
	}
}
