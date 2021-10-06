package allcom.conf;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
//  adds four view controllers
//  Two of the view controllers reference the view whose name is "home" (defined in home.html),
//  and another references the view named "grpc" (defined in grpc.html).
//  The fourth view controller references another view named "login".
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/grpc").setViewName("hello");
        registry.addViewController("/hello2").setViewName("hello2");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin/1").setViewName("adminpage1");
    }

}
