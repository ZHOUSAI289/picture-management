package com.gzlg.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.gzlg.mapper")
public class MyBatisConfig {

    @Value("${mybatis.mapper-locations:classpath:mapper/*.xml}")
    private String mapperLocations;

    @Value("${mybatis.type-aliases-package:com.gzlg.pojo.entity}")
    private String typeAliasesPackage;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(mapperLocations));
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        return sessionFactory.getObject();
    }
}