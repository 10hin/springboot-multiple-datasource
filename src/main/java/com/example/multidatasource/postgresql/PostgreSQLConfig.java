package com.example.multidatasource.postgresql;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories( //
        basePackageClasses = {PostgreSQLConfig.class}, //
        entityManagerFactoryRef = "postgreSQLEntityManagerFactoryBean", //
        transactionManagerRef = "postgreSQLTransactionManager")
public class PostgreSQLConfig {

}
