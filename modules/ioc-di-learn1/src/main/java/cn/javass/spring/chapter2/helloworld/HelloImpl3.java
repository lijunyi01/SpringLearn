package cn.javass.spring.chapter2.helloworld;

/**
 * Created by ljy on 15/5/8.
 */

import org.springframework.stereotype.Component;

@Component("hello3")
public class HelloImpl3 implements HelloApi {
    @Override
    public void sayHello() {
        System.out.println("Hello World3!");
    }
}
