package allcom.app;

import allcom.App;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@ComponentScan(basePackageClasses = App.class)
public class Application implements EmbeddedServletContainerCustomizer {

//    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Value("${system.embeddedtomcatport}")
    private int tomcatport;
    @Value("${system.sessiontimeout}")
    private int sessionTimeout;

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container){
        container.setPort(tomcatport);
        container.setSessionTimeout(sessionTimeout, TimeUnit.SECONDS);
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);

    }
}



//public class Application {
//
//    public static void main(String[] args) throws Throwable {
//        SpringApplication.run(Application.class, args);
//    }
//
//}


