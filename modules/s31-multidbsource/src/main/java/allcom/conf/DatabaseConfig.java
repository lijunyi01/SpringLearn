package allcom.conf;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;

/**
 * Created by ljy on 15/6/1.
 * ok
 */
@Configuration
@ImportResource("classpath:/databaseconfig.xml")
public class DatabaseConfig {
//    @Value("${jdbc.driverClassName}")
//    private String driver;
//    @Value("${jdbc.username}")
//    private String username;
//    @Value("${jdbc.password}")
//    private String password;
//    @Value("${jdbc.url}")
//    private String url;
//
//    @Value("${jdbc2.driverClassName}")
//    private String driver2;
//    @Value("${jdbc2.username}")
//    private String username2;
//    @Value("${jdbc2.password}")
//    private String password2;
//    @Value("${jdbc2.url}")
//    private String url2;
//
//    @Value("${jdbc3.driverClassName}")
//    private String driver3;
//    @Value("${jdbc3.username}")
//    private String username3;
//    @Value("${jdbc3.password}")
//    private String password3;
//    @Value("${jdbc3.url}")
//    private String url3;
//
//    //@Bean(name = "sd1")@Qualifier("sd1")
//    @Bean(name = "sd1")
//    @Primary
//    public HikariDataSource hikariDataSource(){
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setDriverClassName(driver);
//        hikariDataSource.setJdbcUrl(url);
//        hikariDataSource.setPassword(password);
//        hikariDataSource.setUsername(username);
//        return hikariDataSource;
//    }
//
//    //@Bean(name = "sd2")@Qualifier("sd2")
//    @Bean(name = "sd2")
//    public HikariDataSource hikariDataSource2(){
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setDriverClassName(driver2);
//        hikariDataSource.setJdbcUrl(url2);
//        hikariDataSource.setPassword(password2);
//        hikariDataSource.setUsername(username2);
//        return hikariDataSource;
//    }
//
//    @Bean(name = "sd3")
//    public HikariDataSource hikariDataSource3(){
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setDriverClassName(driver3);
//        hikariDataSource.setJdbcUrl(url3);
//        hikariDataSource.setPassword(password3);
//        hikariDataSource.setUsername(username3);
//        return hikariDataSource;
//    }
}
