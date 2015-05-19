package allcom.conf;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;


/**
 * Created by ljy on 15/5/10
 */
@Configuration
public class ApplicationConfig {

        //在标注了@Configuration的java类中，通过在类方法标注@Bean定义一个Bean。方法必须提供Bean的实例化逻辑。
        //通过@Bean的name属性可以定义Bean的名称，未指定时默认名称为方法名。
        @Bean
        public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
            PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
            //ClassPathResource 的根目录在本项目是指resources目录
            //ppc.setLocation(new ClassPathResource("/test.properties"));
            ppc.setLocation(new FileSystemResource("/appconf/s01-scheduling-tasks/test.properties"));
            return ppc;
        }

}
