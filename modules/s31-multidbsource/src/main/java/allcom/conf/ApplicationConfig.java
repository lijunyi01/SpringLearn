package allcom.conf;

import allcom.App;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/*
从spring3.0开始，Spring将JavaConfig整合到核心模块，普通的POJO只需要标注@Configuration注解，就可以成为spring配置类，
并通过在方法上标注@Bean注解的方式注入bean。
*/
@Configuration
@ComponentScan(basePackageClasses = App.class)
class ApplicationConfig {

    //在标注了@Configuration的java类中，通过在类方法标注@Bean定义一个Bean。方法必须提供Bean的实例化逻辑。
    //通过@Bean的name属性可以定义Bean的名称，未指定时默认名称为方法名。
    @Bean
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocation(new ClassPathResource("/jdbc.properties"));
		return ppc;
	}
	
}