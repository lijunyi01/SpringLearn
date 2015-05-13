package hello;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/*
这个类的作用与在web.xml中配置负责初始化Spring应用上下文的监听器作用类似，只不过在这里不需要编写额外的XML文件了
 */
@ComponentScan
@EnableAutoConfiguration
public class InitWeb extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //String connectid = "/topic/greetings-1416799539104-797";
        //connectid = connectid.substring(17);
        return application.sources(InitWeb.class);
    }
}
