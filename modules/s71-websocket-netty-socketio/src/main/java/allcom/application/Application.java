package allcom.application;

import allcom.App;
import allcom.listener.MyAuthorizationListener;
import allcom.listener.MyConncetListener;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
@ComponentScan(basePackageClasses = App.class)
public class Application {

    @Bean
    MyAuthorizationListener myAuthorizationListener(){
        MyAuthorizationListener myAuthorizationListener = new MyAuthorizationListener();
        return myAuthorizationListener;
    }

    @Bean
    Configuration configuration(){
        Configuration configuration = new Configuration();
        configuration.setHostname("192.168.8.100");
        configuration.setPort(8090);
        //configuration.setPingInterval(30000);
        //configuration.setPingTimeout(20000);
        //configuration.setTransports(Transport.WEBSOCKET);
        configuration.setAuthorizationListener(myAuthorizationListener());
        return configuration;
    }

    @Bean(initMethod = "start")
    @Lazy(false)
    SocketIOServer socketIOServer(){
        SocketIOServer socketIOServer = new SocketIOServer(configuration());
        socketIOServer.addConnectListener(myConncetListener());
        return socketIOServer;
    }

    @Bean
    MyConncetListener myConncetListener(){
        return new MyConncetListener();
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}