package allcom.conf;

//import allcom.filter.LoginUsernamePasswordAuthenticationFilter;
import allcom.App;
import allcom.security.LoginSuccessHandler;
import allcom.security.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.DispatcherServlet;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity is used to enable Spring Security’s web security support and provide the Spring MVC integration
@EnableWebSecurity
//@ComponentScan(basePackageClasses = App.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyAuthenticationProvider myAuthenticationProvider;
    private final LoginSuccessHandler loginSuccessHandler;

    @Autowired
    public WebSecurityConfig(MyAuthenticationProvider myAuthenticationProvider,LoginSuccessHandler loginSuccessHandler) {
        this.myAuthenticationProvider = myAuthenticationProvider;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    //   The configure(HttpSecurity) method defines which URL paths should be secured and which should not.
//   Specifically, the "/" and "/home" paths are configured to not require any authentication. All other paths must be authenticated.
//   When a user successfully logs in, they will be redirected to the previously requested page that requires authentication.
//   There is a custom "/login" page specified by loginPage(), and everyone is allowed to view it.
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/","/home").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .successForwardUrl("/hello2")
                    .successHandler(loginSuccessHandler)
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
        httpSecurity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .invalidSessionUrl("/login")
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true);   //让后登的登不进
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
//        auth
//                .inMemoryAuthentication()
//                .withUser("ljy").password("111111").roles("USER");
    }

}
