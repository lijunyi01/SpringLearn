package allcom.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

//import com.allcom.service.UserLoginService;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    @Autowired
    public MyAuthenticationProvider(UserDetailsService userDetailsService,PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        logger.info("authenticate -- username:{},password:{}", username, password);
        
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //不能直接加密比对，同样的密码加密结果一般不会一样！
//        password = encoder.encode(password);
        SSUser ssUser = (SSUser)userDetailsService.loadUserByUsername(username);
        if(ssUser == null){
            throw new BadCredentialsException("Username not found.");
        }

        //加密过程在这里体现
        if (!encoder.matches(password,ssUser.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        Collection<? extends GrantedAuthority> authorities = ssUser.getAuthorities();
        return new UsernamePasswordAuthenticationToken(ssUser, password, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
