package com.nhnacademy.todo.config;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 02/05/2023
 */

@RequiredArgsConstructor
@Configuration
@MapperScan(basePackages="**.mapper.**")
public class MybatisConfig {
    private final DataSource dataSource;
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:**/maps/*.xml"));
        return sessionFactory;
    }
}
