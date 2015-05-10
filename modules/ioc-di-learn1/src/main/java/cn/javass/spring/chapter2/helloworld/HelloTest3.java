package cn.javass.spring.chapter2.helloworld;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ljy on 15/5/8.
 * 测试三种Bean配置方式之：基于注解的配置。@ComponentScan与@Component注释配合使用，其中@Component注释注释于Class HelloImpl3上
 * 本例里，Class HelloImpl3被配置为Bean，由容器管理
 */
@ComponentScan(basePackages = "cn.javass.spring.chapter2")
public class HelloTest3 {
    @Test
    public void testHelloWorld() {
        //读取配置文件实例化一个IoC容器，此方式产生的IoC容器不理解@ComponentScan注释！！！它只认xml文件里的<context:component-scan ...>标签
        //当helloworld3.xml里不包含上述标签时，spring就不会自动扫描@Component注释，就不会自动初始化bean--Class HelloImpl2
//        ApplicationContext context = new ClassPathXmlApplicationContext("helloworld3.xml");

        //1、实例化一个IoC容器，不用读配置文件。与@ComponentScan配套使用
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("cn.javass.spring.chapter2");
        //2、从容器中获取Bean，注意此处完全“面向接口编程，而不是面向实现”
        HelloApi helloApi = context.getBean("hello3", HelloApi.class);
        //3、执行业务逻辑
        helloApi.sayHello();



    }
}