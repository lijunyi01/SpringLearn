package com.allcom.service;

import com.allcom.bean.User;
import com.allcom.dao.MysqlDao;
import com.allcom.security.JwtTokenUtil;
import com.allcom.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by ljy on 17/7/7.
 * ok
 */
@Service
public class AuthService {
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
//    private UserTRepository userTRepository;
    private MysqlDao mysqlDao;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            MysqlDao mysqlDao) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.mysqlDao = mysqlDao;
    }

    public User register(User userToAdd) {
        final String username = userToAdd.getUsername();
        if(mysqlDao.findByUsername(username).size()>0) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(new Date());
        userToAdd.setRoles(asList("ROLE_USER"));
        saveUser(username,userToAdd.getPassword(),userToAdd.getLastPasswordResetDate(),userToAdd.getRoles(),userToAdd.getEmail());
        if(mysqlDao.findByUsername(username).size()>0){
            return userToAdd;
        }else{
            return null;
        }
    }

    @Transactional
    private void saveUser(String userName,String pass, Date lastPasswordResetDate, List<String> roles,String email){
        mysqlDao.saveToUser(userName,pass,lastPasswordResetDate,email);
        for(String role:roles){
            mysqlDao.saveToUserRole(userName,role);
        }
    }


    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
