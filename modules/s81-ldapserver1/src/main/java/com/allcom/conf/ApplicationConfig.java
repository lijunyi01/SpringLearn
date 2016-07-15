package com.allcom.conf;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
//import com.zaxxer.hikari.HikariDataSource;
import com.allcom.ldaputil.LDAPEmbeddedServer;
import com.allcom.ldaputil.MySchema;
import org.apache.directory.api.ldap.model.schema.SchemaObject;
import org.apache.directory.api.ldap.model.schema.SchemaObjectType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
//import org.springframework.jdbc.core.JdbcTemplate;

//import javax.sql.DataSource;
import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * Created by ljy on 15/5/10
 */
@Configuration
public class ApplicationConfig {

    //从配置文件读取数据库参数信息 -- 多库配置
//    @Value("${dataSource.idbuser}")
//    private String idbUsername;
//    @Value("${dataSource.driver}")
//    private String jdbcDriver;
//    @Value("${dataSource.idbpass}")
//    private String idbPassword;
//    @Value("${dataSource.idburl}")
//    private String idbUrl;

//    @Value("${dataSource.idbuser2}")
//    private String idbUsername2;
//    @Value("${dataSource.driver2}")
//    private String jdbcDriver2;
//    @Value("${dataSource.idbpass2}")
//    private String idbPassword2;
//    @Value("${dataSource.idburl2}")
//    private String idbUrl2;


    //在标注了@Configuration的java类中，通过在类方法标注@Bean定义一个Bean。方法必须提供Bean的实例化逻辑。
    //通过@Bean的name属性可以定义Bean的名称，未指定时默认名称为方法名。
    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setFileEncoding("utf-8");
        ppc.setLocation(new FileSystemResource("/appconf/ldapserver/app.properties"));
        return ppc;
    }

    @Bean
    public static JoranConfigurator readLogbackPropertyFile() {
        File logbackFile = new File("/appconf/ldapserver/logback.xml");
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        try {
            configurator.doConfigure(logbackFile);
        } catch (JoranException e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
        return configurator;

    }

    @Bean(initMethod = "execute")
    LDAPEmbeddedServer ldapEmbeddedServer(){
        return new LDAPEmbeddedServer();
    }

    @Bean
    MySchema mySchema(){
        return new MySchema();
    }


//    @Bean(name = "jdbcZJK")
//    JdbcTemplate jdbcTemplate(@Qualifier("db1") DataSource dataSource) {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        return jdbcTemplate;
//    }

//	@Bean(name="jdbcMysql")
//	JdbcTemplate jdbcTemplate2(@Qualifier("db2")DataSource dataSource) {
//	    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//	    return jdbcTemplate;
//	}



//	@Bean(name="dataSource2")
//	@Qualifier("db2")
//	@Scope("prototype")
//	DataSource dataSource2(){
//	    HikariDataSource hikariDataSource = new HikariDataSource();
//	    hikariDataSource.setUsername(idbUsername2);
//	    hikariDataSource.setDriverClassName(jdbcDriver2);
//	    hikariDataSource.setPassword(idbPassword2);
//	    hikariDataSource.setJdbcUrl(idbUrl2);
//	    hikariDataSource.setMaximumPoolSize(3);
//	    hikariDataSource.setConnectionTestQuery("select count(*) from tbl_profile");
//	    return hikariDataSource;
//	}
}