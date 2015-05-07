
package com.jike.spring.chapter03.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class ReflectTest {
  

	public static Car  initByDefaultConst() throws Throwable
	{
		//1.通过类装载器获取Car类对象
		ClassLoader loader = Thread.currentThread().getContextClassLoader();		
		Class clazz = loader.loadClass("com.jike.spring.chapter03.reflect.Car");
	
		//2.获取类的默认构造器对象并实例化Car
		Constructor cons = clazz.getDeclaredConstructor((Class[])null);
		Car car = (Car)cons.newInstance();
		
		//3.通过反射方法设置属性
		Method setBrand = clazz.getMethod("setBrand",String.class);		
		setBrand.invoke(car,"奔驰");		
		Method setColor = clazz.getMethod("setColor",String.class);
		setColor.invoke(car,"黑色");		
		Method setMaxSpeed = clazz.getMethod("setMaxSpeed",int.class);
		setMaxSpeed.invoke(car,200);		
		return car;
	}
	
	public static Car initByParamConst()  throws Throwable{
		//1.通过类装载器获取Car类对象
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = loader.loadClass("com.jike.spring.chapter03.reflect.Car");
		
		//2.获取类的带有参数的构造器对象
		Constructor cons = clazz.getDeclaredConstructor(new Class[]{String.class,String.class,int.class});
		
		//3.使参数的构造器对象实例化Car
		Car car = (Car)cons.newInstance(new Object[]{"宝马","红色",180});
		return car;	
	}
	
	public static void main(String[] args) throws Throwable {
		Car car1 = initByDefaultConst();
		Car car2 = initByParamConst();
		car1.introduce();
		car2.introduce();
	}
}
