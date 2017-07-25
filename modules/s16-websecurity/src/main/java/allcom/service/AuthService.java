package allcom.service;

import allcom.bean.User;
import allcom.dao.MysqlDao;
import allcom.toolkit.GlobalTools;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by ljy on 17/7/7.
 * ok
 */
@Service
public class AuthService {

    private final MysqlDao mysqlDao;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AuthService(MysqlDao mysqlDao,BCryptPasswordEncoder encoder) {
        this.mysqlDao = mysqlDao;
        this.encoder = encoder;
    }

    public User register(User userToAdd) {
        final String username = userToAdd.getUsername();
        //用户已存在的情况
        if(mysqlDao.findByUsername(username).size()>0) {
            return null;
        }

        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(GlobalTools.getTimeBefore(0L));
        userToAdd.setRoles(asList("ROLE_UPDATE_FILM","ROLE_DELETE_FILM"));
        addUser(username,userToAdd.getPassword(),userToAdd.getLastPasswordResetDate(),userToAdd.getRoles());
        return userToAdd;
    }

    @Transactional
    private void addUser(String username, String passwd, Timestamp passResetDate, List<String> roles){
        int userid = mysqlDao.saveUser(username,passwd,passResetDate);
        for(String role: roles){
            int roleid = mysqlDao.getRoleIdByRolename(role);
            if(roleid > -1){
                mysqlDao.addRoleidToUser(userid,roleid);
            }
        }
    }


//
//
//    public String login(String username, String password) {
//        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
//        final Authentication authentication = authenticationManager.authenticate(upToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        final String token = jwtTokenUtil.generateToken(userDetails);
//        return token;
//    }
//
//    public String refresh(String oldToken) {
//        final String token = oldToken.substring(tokenHead.length());
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
//        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
//            return jwtTokenUtil.refreshToken(token);
//        }
//        return null;
//    }
}
