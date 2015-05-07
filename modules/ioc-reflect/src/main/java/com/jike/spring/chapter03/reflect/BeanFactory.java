package com.jike.spring.chapter03.reflect;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {

	private Map<String, Object> beanMap = new HashMap<String, Object>();

	/**
	 * bean工厂的初始化.
	 * 
	 * @param xml xml配置文件
	 */
	public void init(String xml) {
		try {
			//1.创建读取配置文件的reader对象
			SAXReader reader = new SAXReader();
            //测试xml文件是否正常,xml可以是绝对路径
//            File xmlfile1 = new File(xml);
//            Document doc1 = reader.read(xmlfile1);
//            Element root1 = doc1.getRootElement();
//            String attr = root1.attributeValue("ref");
			
			//2.获取当前线程中的类装载器对象
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			
			//3.从class目录下获取指定的xml文件
            //Class.getClassLoader.getResourceAsStream(String path) ：默认则是从ClassPath根下获取，path不能以’/'开头，最终是由ClassLoader获取资源。
            //ClassPath根是指：模块目录／target/classes ; 因此调试时conf/config.xml 要拷贝至前述目录
			InputStream ins = classLoader.getResourceAsStream(xml);
			Document doc = reader.read(ins);
			Element root = doc.getRootElement();
			Element foo;
			
			//4.遍历xml文件当中的Bean实例
			for (Iterator i = root.elementIterator("bean"); i.hasNext();) {
				foo = (Element) i.next();
				
				//5.针对每个一个Bean实例，获取bean的属性id和class
				Attribute id = foo.attribute("id");
				Attribute cls = foo.attribute("class");
				
				//6.利用Java反射机制，通过class的名称获取Class对象
                //bean可理解为目标类元数据的实例，而非目标类的实例，目标类实例通过bean.newInstance()创建
				Class bean = Class.forName(cls.getText());
				//7.获取对应class的信息
				java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(bean);
				//8.获取其属性描述
				java.beans.PropertyDescriptor pd[] = info.getPropertyDescriptors();

				//9.创建一个对象，并在接下来的代码中为对象的属性赋值
				Object obj = bean.newInstance();
				
				//10.遍历该bean的property属性
				for (Iterator ite = foo.elementIterator("property"); ite.hasNext();) {
					Element foo2 = (Element) ite.next();
					
					//11.获取该property的name属性
					Attribute name = foo2.attribute("name");
					String value = null;
					
					//12.获取该property的子元素value的值
					for (Iterator ite1 = foo2.elementIterator("value"); ite1.hasNext();) 
					{
						Element node = (Element) ite1.next();
						value = node.getText();
						break;
					}
					
					//13.利用Java的反射机制调用对象的某个set方法，并将值设置进去 
					for (int k = 0; k < pd.length; k++) {
						if (pd[k].getName().equalsIgnoreCase(name.getText())) 
						{
							Method mSet = null;
							mSet = pd[k].getWriteMethod();
							mSet.invoke(obj, value);
						}
					}
				}

				//14.将对象放入beanMap中，其中key为id值，value为对象
				beanMap.put(id.getText(), obj);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * 通过bean的id获取bean的对象.
	 * 
	 * @param beanName
	 *            bean的id
	 * @return 返回对应对象
	 */
	public Object getBean(String beanName) {
		Object obj = beanMap.get(beanName);
		return obj;
	}

	/**
	 * 测试方法.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BeanFactory factory = new BeanFactory();
//		factory.init("/Users/ljy/IdeaProjects/SpringLearn/modules/ioc-reflect/src/main/java/conf/config.xml");
        factory.init("conf/config.xml");
//        factory.init("/src/main/java/conf/config.xml");
		JavaBean javaBean = (JavaBean) factory.getBean("javaBean");
		System.out.println("userName=" + javaBean.getUserName());
		System.out.println("password=" + javaBean.getPassword());
	}
}
