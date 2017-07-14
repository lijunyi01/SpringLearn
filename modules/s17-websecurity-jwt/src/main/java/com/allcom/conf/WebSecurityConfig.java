package com.allcom.conf;

import com.allcom.filter.JwtAuthenticationTokenFilter;
//import com.allcom.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity is used to enable Spring Security’s web security support and provide the Spring MVC integration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    // Spring会自动寻找同样类型的具体类注入，这里就是JwtUserDetailsServiceImpl了
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //   The configure(HttpSecurity) method defines which URL paths should be secured and which should not.
//   Specifically, the "/" and "/home" paths are configured to not require any authentication. All other paths must be authenticated.
//   When a user successfully logs in, they will be redirected to the previously requested page that requires authentication.
//   There is a custom "/login" page specified by loginPage(), and everyone is allowed to view it.
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                    .antMatchers("/", "/home").permitAll()
//                    .antMatchers("/admin/**").hasRole("ADMIN")
//                    .anyRequest().authenticated()
//                    .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                    .and()
//                .logout()
//                    .permitAll();
//
//
//    }

//  the configure(AuthenticationManagerBuilder) method, it sets up an in-memory user store with a single user.
//  That user is given a username of "user", a password of "password", and a role of "USER".
//  login 页面的form提交的内容会自动与此处创建的用户信息比对，以判断登录是否成功。这是注释@EnableWebSecurity的功能
//  成功则将客户端重定向至http://...:8080/;不成功则重定向至http://...:8080/login?error
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("ljy").password("111111").roles("USER").and()
//                .withUser("qinjie").password("111111").roles("USER").and()
//                .withUser("admin").password("111111").roles("ADMIN");
//    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                // 设置UserDetailsService
                .userDetailsService(this.userDetailsService)
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
    }

    // 装载BCrypt密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()

                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                    //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                    // 允许对于网站静态资源的无授权访问
                    .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                    ).permitAll()
                    // 对于获取token的rest api要允许匿名访问
                    .antMatchers("/auth/**").permitAll()
                    // 除上面外的所有请求全部需要鉴权认证
                    .anyRequest().authenticated().and()

                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }

}
