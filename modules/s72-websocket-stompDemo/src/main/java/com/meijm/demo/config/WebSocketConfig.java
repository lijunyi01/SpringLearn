package com.meijm.demo.config;

import com.meijm.demo.vo.User;
import com.meijm.demo.vo.UserPrincipal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Log4j2
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private MyHandshakeInterceptor myHandshakeInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // 以下两段差异是有没有.withSockJS()，取决于前端是否使用SockJS，前后需要匹配；本例子自带的前端代码是使用SockJS的；
        // 但chrome 插件browser websocket client 是不用SockJS的。

        stompEndpointRegistry.addEndpoint("/any-socket").setAllowedOrigins("*").addInterceptors(myHandshakeInterceptor).setHandshakeHandler(new DefaultHandshakeHandler() {
            /**
             * 指定握手主体生成规则，后续接收消息时会使用，默认频道为Principal.getName
             * @param request
             * @param wsHandler
             * @param attributes
             * @return
             */
            @Nullable
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                return new UserPrincipal((User) attributes.get("user"));
            }
        }).withSockJS();
//        stompEndpointRegistry.addEndpoint("/any-socket").setAllowedOrigins("*").addInterceptors(myHandshakeInterceptor).setHandshakeHandler(new DefaultHandshakeHandler() {
//            /**
//             * 指定握手主体生成规则，后续接收消息时会使用，默认频道为Principal.getName
//             * @param request
//             * @param wsHandler
//             * @param attributes
//             * @return
//             */
//            @Nullable
//            @Override
//            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//                return new UserPrincipal((User) attributes.get("user"));
//            }
//        });
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {

        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
        messageBrokerRegistry.enableSimpleBroker("/queue", "/topic");
        //指定用户频道前缀
        messageBrokerRegistry.setUserDestinationPrefix("/queue");
    }

    @Override
    public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                        String username = session.getPrincipal().getName();
                        //log.info("online:" + username);
                        super.afterConnectionEstablished(session);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
                            throws Exception {
                        String username = session.getPrincipal().getName();
                        //log.info("offline:" + username);
                        super.afterConnectionClosed(session, closeStatus);
                    }
                };
            }
        });
    }
}
