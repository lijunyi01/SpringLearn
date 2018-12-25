package com.allcom.conf;

import com.allcom.db.DynamicDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ljy on 15/6/1.
 * ok
 */
@Configuration
public class DatabaseConfig {
    @Value("${jdbc1.driverClassName}")
    private String driver;
    @Value("${jdbc1.username}")
    private String username;
    @Value("${jdbc1.password}")
    private String password;
    @Value("${jdbc1.url}")
    private String url;

    @Value("${jdbc2.driverClassName}")
    private String driver2;
    @Value("${jdbc2.username}")
    private String username2;
    @Value("${jdbc2.password}")
    private String password2;
    @Value("${jdbc2.url}")
    private String url2;

//    @Value("${spring.datasource.type}")
//    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "ds1")
    @Primary
//    @ConfigurationProperties(prefix = "jdbc1")
    public DataSource hikariDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driver);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setPassword(password);
        hikariDataSource.setUsername(username);
        return hikariDataSource;
//        return DataSourceBuilder.create().type(dataSourceType).build();
    }
//

    @Bean(name = "ds2")
//    @ConfigurationProperties(prefix = "jdbc2")
    public DataSource hikariDataSource2(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driver2);
        hikariDataSource.setJdbcUrl(url2);
        hikariDataSource.setPassword(password2);
        hikariDataSource.setUsername(username2);
        return hikariDataSource;
//        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name="dataSource")
    public AbstractRoutingDataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(hikariDataSource());
        Map<Object,Object> dataSources = new HashMap<>();
        dataSources.put("db1",hikariDataSource());
        dataSources.put("db2",hikariDataSource2());
        dynamicDataSource.setTargetDataSources(dataSources);

        return dynamicDataSource;
    }


    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.hui.readwrite.po");
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:mapper/master1*//*.xml"));
//        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }
}
