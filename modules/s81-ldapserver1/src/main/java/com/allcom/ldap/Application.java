package com.allcom.ldap;

/**
 * Created by ljy on 16/7/5.
 * ok
 */
import com.allcom.App;
import com.allcom.ldaputil.LDAPEmbeddedServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = App.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
