package com.allcom.service;

import com.allcom.bean.User;
import com.allcom.dao.MysqlDao;
import com.allcom.security.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ljy on 17/7/7.
 * ok
 */

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

//    private final UserRepository userRepository;
    private final MysqlDao mysqlDao;

    @Autowired
    public JwtUserDetailsServiceImpl(MysqlDao mysqlDao) {
        this.mysqlDao = mysqlDao;
    }
//    public JwtUserDetailsServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Map<String,Object>> mapList = mysqlDao.findByUsername(username);
        String pass = null;
        String email = null;
        String lastPasswordResetDate = null;
        Date dt = null;
        List<String> roles = new ArrayList<>();
        if(mapList.size() == 1){
            Map<String,Object> map = mapList.get(0);
            if(map.get("password")!=null){
                pass = map.get("password").toString();
            }
            if(map.get("email")!=null){
                email = map.get("email").toString();
            }
            if(map.get("lastpasswordresetdate")!=null){
                lastPasswordResetDate = map.get("lastpasswordresetdate").toString();
                SimpleDateFormat sdf= new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");
                try {
                    dt = sdf.parse(lastPasswordResetDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            roles = mysqlDao.getRolesByUsername(username);
        }

        User user = null;
        if(pass !=null){
            user = new User();
            user.setUsername(username);
            user.setPassword(pass);
            user.setEmail(email);
            user.setLastPasswordResetDate(dt);
            user.setRoles(roles);
        }

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
