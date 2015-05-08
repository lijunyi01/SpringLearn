package cn.javass.spring.chapter2.helloworld;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ljy on 15/5/8.
 * 测试三种Bean配置方式之：基于xml的配置。xml文件里直接定义Bean
 * 本例里，Class HelloImpl被配置为Bean，由容器管理
 */

public class HelloTest {
    @Test
    public void testHelloWorld() {
        //1、读取配置文件实例化一个IoC容器
        ApplicationContext context = new ClassPathXmlApplicationContext("helloworld.xml");
        //2、从容器中获取Bean，注意此处完全“面向接口编程，而不是面向实现”
        HelloApi helloApi = context.getBean("hello", HelloApi.class);
        //3、执行业务逻辑
        helloApi.sayHello();
    }
}
