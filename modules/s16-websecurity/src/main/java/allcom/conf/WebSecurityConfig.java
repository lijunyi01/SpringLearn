package allcom.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
//@EnableWebMvcSecurity is used to enable Spring Security’s web security support and provide the Spring MVC integration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//   The configure(HttpSecurity) method defines which URL paths should be secured and which should not.
//   Specifically, the "/" and "/home" paths are configured to not require any authentication. All other paths must be authenticated.
//   When a user successfully logs in, they will be redirected to the previously requested page that requires authentication.
//   There is a custom "/login" page specified by loginPage(), and everyone is allowed to view it.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/home").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();

//        参考
//        http
//                .authorizeRequests()
    //                .antMatchers("/", "/favicon.ico", "/resources/**", "/signup").permitAll()
    //                .anyRequest().authenticated()
    //                .and()
//                .formLogin()
    //                .loginPage("/signin")
    //                .permitAll()
    //                .failureUrl("/signin?error=1")
    //                .loginProcessingUrl("/authenticate")
    //                .and()
//                .logout()
    //                .logoutUrl("/logout")
    //                .permitAll()
    //                .logoutSuccessUrl("/signin?logout")
    //                .and()
//                .rememberMe()
    //                .rememberMeServices(rememberMeServices())
    //                .key("remember-me-key");

    }

//  the configure(AuthenticationManagerBuilder) method, it sets up an in-memory user store with a single user.
//  That user is given a username of "user", a password of "password", and a role of "USER".
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
}
