package cn.allcom.ljy.springlean.conf;

//import org.junit.Test;
import cn.allcom.ljy.springlean.Application;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;


/**
 * Created by ljy on 15/5/10
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @ComponentScan.Filter({Controller.class, Configuration.class}))
public class ApplicationConfig {

        //在标注了@Configuration的java类中，通过在类方法标注@Bean定义一个Bean。方法必须提供Bean的实例化逻辑。
        //通过@Bean的name属性可以定义Bean的名称，未指定时默认名称为方法名。
        @Bean
        public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
            PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
            ppc.setLocation(new ClassPathResource("/test.properties"));
            return ppc;
        }

}
