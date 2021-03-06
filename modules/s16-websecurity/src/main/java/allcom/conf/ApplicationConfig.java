package allcom.conf;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import allcom.App;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.DispatcherServlet;

import javax.sql.DataSource;
import java.io.File;

/*
从spring3.0开始，Spring将JavaConfig整合到核心模块，普通的POJO只需要标注@Configuration注解，就可以成为spring配置类，
并通过在方法上标注@Bean注解的方式注入bean。
*/
@Configuration
@ComponentScan(basePackageClasses = App.class)
class ApplicationConfig {

	@Value("${dataSource.idbuser}")
	private String idbUsername;
	@Value("${dataSource.driver}")
	private String jdbcDriver;
	@Value("${dataSource.idbpass}")
	private String idbPassword;
	@Value("${dataSource.idburl}")
	private String idbUrl;

    //在标注了@Configuration的java类中，通过在类方法标注@Bean定义一个Bean。方法必须提供Bean的实例化逻辑。
    //通过@Bean的name属性可以定义Bean的名称，未指定时默认名称为方法名。
    @Bean
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocation(new FileSystemResource("/appconf/s16-websecurity/app.properties"));        //JPA的标准配置文件
		return ppc;
	}

	@Bean
	public static JoranConfigurator readLogbackPropertyFile(){
		File logbackFile = new File("/appconf/s16-websecurity/logback.xml");
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			configurator.doConfigure(logbackFile);
		}
		catch (JoranException e) {
			e.printStackTrace(System.err);
			System.exit(-1);
		}
		return configurator;

	}

	@Bean(name="jdbctemplate")
	JdbcTemplate jdbcTemplate(@Qualifier("db1")DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

//	@Bean(name="jdbctemplatelog")
//	JdbcTemplate jdbcTemplate2(@Qualifier("db2")DataSource dataSource) {
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//		return jdbcTemplate;
//	}


	@Bean(name="dataSource")
	@Qualifier("db1")
	@Scope("prototype")
	DataSource dataSource(){
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setUsername(idbUsername);
		hikariDataSource.setDriverClassName(jdbcDriver);
		hikariDataSource.setPassword(idbPassword);
		hikariDataSource.setJdbcUrl(idbUrl);
		hikariDataSource.setMaximumPoolSize(3);
		hikariDataSource.setConnectionTestQuery("select count(*) from t_admin_user");
		return hikariDataSource;
	}


//	@Bean(name="dataSource2")
//	@Qualifier("db2")
//	@Scope("prototype")
//	DataSource dataSource2(){
//		HikariDataSource hikariDataSource = new HikariDataSource();
//		hikariDataSource.setUsername(idbUsername2);
//		hikariDataSource.setDriverClassName(jdbcDriver2);
//		hikariDataSource.setPassword(idbPassword2);
//		hikariDataSource.setJdbcUrl(idbUrl2);
//		hikariDataSource.setMaximumPoolSize(3);
//		hikariDataSource.setConnectionTestQuery("select count(*) from loginfaillog_m");
//		return hikariDataSource;
//	}


	// 装载BCrypt密码编码器
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}