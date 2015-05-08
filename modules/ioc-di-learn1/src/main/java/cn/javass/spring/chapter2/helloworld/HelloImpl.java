package cn.javass.spring.chapter2.helloworld;

/**
 * Created by ljy on 15/5/8.
 */
public class HelloImpl implements HelloApi {
    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }
}
