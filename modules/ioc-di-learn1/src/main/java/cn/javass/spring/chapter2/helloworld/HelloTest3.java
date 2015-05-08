package cn.javass.spring.chapter2.helloworld;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ljy on 15/5/8.
 * 测试@Compent注解方式定义bean,@Compent位于Class HelloImpl2
 */
@Configuration
@ComponentScan(basePackages = "cn.javass.spring.chapter2")
public class HelloTest3 {
    @Test
    public void testHelloWorld() {
        //1、读取配置文件实例化一个IoC容器
//        ApplicationContext context = new ClassPathXmlApplicationContext("helloworld3.xml");
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("cn.javass.spring.chapter2");


        //2、从容器中获取Bean，注意此处完全“面向接口编程，而不是面向实现”
        HelloApi helloApi = context.getBean("hello2", HelloApi.class);
        //3、执行业务逻辑
        helloApi.sayHello();



    }
}
