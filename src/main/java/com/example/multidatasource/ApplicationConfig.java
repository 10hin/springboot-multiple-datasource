package com.example.multidatasource;

import com.example.multidatasource.mysql.MySQLConfig;
import com.example.multidatasource.postgresql.PostgreSQLConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "app.db.mysql.datasource")
    public DataSourceProperties mySQLDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.db.postgresql.datasource")
    public DataSourceProperties postgreSQLDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "app.db.mysql.datasource")
    public DataSource mySQLDataSource(@Autowired @Qualifier("mySQLDataSourceProperties") DataSourceProperties mySQLDataSourceProperties) {
        return mySQLDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.db.postgresql.datasource")
    public DataSource postgreSQLDataSource(@Autowired @Qualifier("postgreSQLDataSourceProperties") DataSourceProperties postgreSQLDataSourceProperties) {
        return postgreSQLDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "app.db.mysql.jpa")
    public JpaProperties mySQLJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.db.postgresql.jpa")
    public JpaProperties postgreSQLJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mySQLEntityManagerFactoryBean( //
            @Autowired //
            @Qualifier("mySQLJpaProperties") //
            final JpaProperties mySQLJpaProperties, //
            @Autowired //
            @Qualifier("mySQLDataSource") //
            final DataSource mySQLDataSource) {
        final LocalContainerEntityManagerFactoryBean mySQLEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        mySQLEntityManagerFactoryBean.setDataSource(mySQLDataSource);
        mySQLEntityManagerFactoryBean.setPackagesToScan(MySQLConfig.class.getPackage().getName());

        final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        mySQLEntityManagerFactoryBean.setJpaVendorAdapter(adapter);

        final HibernateSettings settings = new HibernateSettings() //
                .ddlAuto(() -> "none");
        mySQLEntityManagerFactoryBean.setJpaPropertyMap(mySQLJpaProperties.getHibernateProperties(settings));

        return mySQLEntityManagerFactoryBean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgreSQLEntityManagerFactoryBean( //
            @Autowired //
            @Qualifier("postgreSQLJpaProperties") //
            final JpaProperties postgreSQLJpaProperties, //
            @Autowired //
            @Qualifier("postgreSQLDataSource") //
            final DataSource postgreSQLDataSource) {
        final LocalContainerEntityManagerFactoryBean postgreSQLEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        postgreSQLEntityManagerFactoryBean.setDataSource(postgreSQLDataSource);
        postgreSQLEntityManagerFactoryBean.setPackagesToScan(PostgreSQLConfig.class.getPackage().getName());

        final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        postgreSQLEntityManagerFactoryBean.setJpaVendorAdapter(adapter);

        final HibernateSettings settings = new HibernateSettings() //
                .ddlAuto(() -> "none");
        postgreSQLEntityManagerFactoryBean.setJpaPropertyMap(postgreSQLJpaProperties.getHibernateProperties(settings));

        return postgreSQLEntityManagerFactoryBean;
    }

    @Bean
    @Primary
    public PlatformTransactionManager mySQLTransactionManager(
            @Autowired
            @Qualifier("mySQLEntityManagerFactoryBean")
            final LocalContainerEntityManagerFactoryBean mySQLEntityManagerFactoryBean) {

        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(mySQLEntityManagerFactoryBean.getObject());

        return jpaTransactionManager;
    }

    @Bean
    public PlatformTransactionManager postgreSQLTransactionManager(
            @Autowired
            @Qualifier("postgreSQLEntityManagerFactoryBean")
            final LocalContainerEntityManagerFactoryBean postgreSQLEntityManagerFactoryBean) {

        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(postgreSQLEntityManagerFactoryBean.getObject());

        return jpaTransactionManager;
    }

}
