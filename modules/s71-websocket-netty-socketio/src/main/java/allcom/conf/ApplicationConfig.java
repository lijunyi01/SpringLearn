package allcom.conf;

import allcom.App;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
//import com.corundumstudio.socketio.Configuration;

/*
从spring3.0开始，Spring将JavaConfig整合到核心模块，普通的POJO只需要标注@Configuration注解，就可以成为spring配置类，
并通过在方法上标注@Bean注解的方式注入bean。
*/
@Configuration
class ApplicationConfig {



}