package cn.allcom.ljy.springlean;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * Created by ljy on 15/5/10.
 * @Value 注释实现自动注入配置文件的值，前提条件是@Value作用于Spring管理的Bean；
 * 如果不是Bean，@Value注释是无效的
 */
@Component("rt")
public class ReadTest {
    @Value("${dataSource.driverClassName}")
    private String driver;
    @Value("${dataSource.username}")
    private String username;
    @Value("${dataSource.password}")
    private String password;
    @Value("${dataSource.servername}")
    private String servername;
    @Value("${dataSource.port}")
    private String port;
    @Value("${dataSource.databasename}")
    private String databasename;

    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    private String strtest;
    public String strtestpub;

    public void printName(){
        System.out.print("username is:"+this.username);
    }

    public String getName(){
        return this.username;
    }

    public void setStrtest(){
        this.strtest = "haha";
    }

    public void setStrtestPub(){
        this.strtestpub = "hahapub";
    }

    @Test
    public void testReadPropertyFile() {

//         // 1、实例化一个IoC容器
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("cn.allcom.ljy");
//        //2、从容器中获取Bean
        ReadTest rt = context.getBean("rt", ReadTest.class);
        rt.printName();

//        ReadTest rt = new ReadTest();
        //下行打印出null，可理解为打印了当前实例的username值，由于当前实例未对username赋值，因此为null;当前实例与"rt"是class ReadTest 的两个不同实例
        System.out.println("username is:" + this.username);
        System.out.println("username is:" + rt.getName());
        System.out.println("username is:" + rt.username);


        setStrtest();
        System.out.println("strtest is:" + strtest);
        System.out.println("strtest is:" + rt.strtest);

        setStrtestPub();
        System.out.println("strtestpub is:" + strtestpub);
        System.out.println("strtestpub is:" + rt.strtestpub);
        rt.setStrtestPub();
        System.out.println("strtestpub is:" + rt.strtestpub);

    }


}
