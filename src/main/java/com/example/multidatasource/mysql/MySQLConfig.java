package com.example.multidatasource.mysql;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories( //
        basePackageClasses = {MySQLConfig.class}, //
        entityManagerFactoryRef = "mySQLEntityManagerFactoryBean", //
        transactionManagerRef = "mySQLTransactionManager")
public class MySQLConfig {

}
