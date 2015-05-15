package allcom.hello;

import allcom.App;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication 相当于 @Configuration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
@EnableScheduling
//虽然@SpringBootApplication 已包含了@ComponentScan，但没有指明扫描范围，默认扫描范围是本包；以下注释相当于重载
//@ComponentScan tells Spring to look for other components, configurations, and services.本注释是向spring容器
//提出说明，而不是对本class作说明，因此个人理解在程序入口处或者初始化spring容器的地方用@ComponentScan说明即可，同一容器相关
//的其它类文件处就不用重复说明了
@ComponentScan(basePackageClasses = App.class)
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
    }
}