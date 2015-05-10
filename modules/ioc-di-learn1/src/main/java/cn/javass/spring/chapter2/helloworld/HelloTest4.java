package cn.javass.spring.chapter2.helloworld;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ljy on 15/5/8.
 * 测试三种Bean配置方式之：基于java配置类的配置。@Configuration与@Bean注释配合使用，其中@Bean注释注释于方法上
 * 本例里，Class HelloImpl4被配置为Bean，由容器管理.方法helloImpl4()提供该Bean的实例化逻辑（本例为直接用new方式创建）。
 */

@Configuration
public class HelloTest4 {

    @Bean(name = "hello4")
    public HelloImpl4 helloImpl4(){
        return new HelloImpl4();
    }

    @Test
    public void testHelloWorld() {

        //1、实例化一个IoC容器
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("cn.javass.spring.chapter2");
        //2、从容器中获取Bean，注意此处完全“面向接口编程，而不是面向实现”
        HelloApi helloApi = context.getBean("hello4", HelloApi.class);
        //3、执行业务逻辑
        helloApi.sayHello();



    }
}