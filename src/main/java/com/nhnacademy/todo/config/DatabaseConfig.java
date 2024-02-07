package com.nhnacademy.todo.config;

import com.nhnacademy.todo.properties.DatabaseProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 02/05/2023
 */
@RequiredArgsConstructor
@Configuration
public class DatabaseConfig {

    private final DatabaseProperties databaseProperties;

    @Bean
    public DataSource dataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(databaseProperties.getDriverClassName());
        basicDataSource.setUrl(databaseProperties.getUrl());
        basicDataSource.setUsername(databaseProperties.getUsername());
        basicDataSource.setPassword(databaseProperties.getPassword());
        basicDataSource.setInitialSize(databaseProperties.getInitialSize());
        basicDataSource.setMaxTotal(databaseProperties.getMaxTotal());
        basicDataSource.setMinIdle(databaseProperties.getMinIdle());
        basicDataSource.setMaxIdle(databaseProperties.getMaxIdle());
        basicDataSource.setTestOnBorrow(databaseProperties.isTestOnBorrow());
        if(databaseProperties.isTestOnBorrow()) {
            basicDataSource.setValidationQuery(basicDataSource.getValidationQuery());
        }
        return basicDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

}
