package com.jike.spring.chapter03.reflect;

import java.awt.Button;
import java.lang.reflect.Method;
import java.util.Hashtable;

/**
 *初探Java的反射机制.   
 */
public class ReflectionTest {

    /**
     *@paramargs
     */
    public static void main(String[] args) throws Exception {
        ReflectionTest reflection = new ReflectionTest();
        reflection.getNameTest();
        System.out.println("");
        reflection.getMethodTest();
    }

    /**
     *Class的getName()方法测试.
     *@throwsException
     */
    public void getNameTest() throws Exception {
        System.out.println("===========begin getNameTest============");
        String name = "极客学院";
        Class cls = name.getClass();
        System.out.println("String类名: " + cls.getName());
        Button btn = new Button();
        Class btnClass = btn.getClass();
        System.out.println("Button类名: " + btnClass.getName());
        Class superBtnClass = btnClass.getSuperclass();
        System.out.println("Button的父类名: " + superBtnClass.getName());
        Class clsTest = Class.forName("java.awt.Button");
        System.out.println("clsTest name: " + clsTest.getName());
        System.out.println("===========end getNameTest============");
    }
    
    /**
     *Class的getMethod()方法测试.
     *@throwsException
     */
    public void getMethodTest() throws Exception {
        System.out.println("===========begin getMethodTest==========");
        Class cls = Class.forName("com.jike.spring.chapter03.reflect.ReflectionTest");
        Class ptypes[] = new Class[2];
        ptypes[0] = Class.forName("java.lang.String");
        ptypes[1] = Class.forName("java.util.Hashtable");
        Method method = cls.getMethod("testMethod", ptypes);
        Object args[] = new Object[2];
        args[0] = "hello, my dear!";
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("name", "极客学院");
        args[1] = ht;

        String returnStr = (String) method.invoke(new ReflectionTest(), args);
        System.out.println("returnStr= " + returnStr);
        System.out.println("===========end getMethodTest==========");
    }

    public String testMethod(String str, Hashtable ht) throws Exception {
        String returnStr = "返回值";
        System.out.println("测试testMethod()方法调用");
        System.out.println("str= " + str);
        System.out.println("名字= " + (String) ht.get("name"));
        System.out.println("结束testMethod()方法调用");
        return returnStr;
}
    
    
}