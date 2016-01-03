package allcom.hello;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by ljy on 15/5/12.
 * Although it is possible to package this service as a traditional WAR file for deployment to an external application server,
 * the simpler approach demonstrated below creates a standalone application.
 * You package everything in a single, executable JAR file, driven by a good old Java main() method. Along the way,
 * you use Spring’s support for embedding the Tomcat servlet container as the HTTP runtime,
 * instead of deploying to an external instance.
 */

/**
 * 关于@SpringBootApplication的官方说明
 * @SpringBootApplication is a convenience annotation that adds all of the following:
 * @Configuration tags the class as a source of bean definitions for the application context.
 * @EnableAutoConfiguration tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
 * Normally you would add @EnableWebMvc for a Spring MVC app, but Spring Boot adds it automatically when it sees spring-webmvc on the classpath. This flags the application as a web application and activates key behaviors such as setting up a DispatcherServlet.
 * @ComponentScan tells Spring to look for other components, configurations, and services in the the hello package, allowing it to find the HelloController.
**/

/*
 * 本class 配合pom.xml实现了web项目的jar包部署（内含嵌入式tomcat），从本类的main()方法启动；
 * 当修改pom.xml并增加class InitWeb 将项目作为普通war包部署于中间件时，本类是无意义的，完全可以删除
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //run()返回spring的IOC容器，参考module s30
        SpringApplication.run(Application.class, args);
    }
}
