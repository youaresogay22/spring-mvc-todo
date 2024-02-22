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
        // 세션팩토리를 빈으로 등록했으므로 세션팩토리에서 필요할 때마다 세션을 만들어서 트랜잭션을 수행함
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:**/maps/*.xml"));
        return sessionFactory;
    }
}
