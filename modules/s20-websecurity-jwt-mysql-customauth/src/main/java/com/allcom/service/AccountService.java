package com.allcom.service;

import com.allcom.bean.User;
import com.allcom.dao.MysqlDao;
//import com.allcom.entity.Account;
//import com.allcom.toolkit.GlobalTools;
//import com.allcom.toolkitexception.ToolLibException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ljy on 2018/5/2.
 * ok
 */
@Service
public class AccountService {

    private static Logger log = LoggerFactory.getLogger(AccountService.class);

    private final MysqlDao mysqlDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(MysqlDao mysqlDao, PasswordEncoder passwordEncoder) {
        this.mysqlDao = mysqlDao;
        this.passwordEncoder = passwordEncoder;
    }

    public User authUser(String userName, String password) {
        User u = new User();
        List<Map<String,Object>> mapList = mysqlDao.findByUsername(userName);
        Date dt = null;

        if(mapList.size()==1){
            Map<String,Object> map = mapList.get(0);
//            if(map.get("username")!=null){
//                u.setUsername(map.get("username").toString());
//            }
            u.setUsername(userName);
            if(map.get("password")!=null){
                u.setPassword(map.get("password").toString());
            }
            if(map.get("lastpasswordresetdate")!=null){

                String lastPasswordResetDate = map.get("lastpasswordresetdate").toString();
                SimpleDateFormat sdf= new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");
                try {
                    dt = sdf.parse(lastPasswordResetDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                u.setLastPasswordResetDate(dt);
            }
            if(map.get("email")!=null){
                u.setEmail(map.get("email").toString());
            }
//            if(map.get("id")!=null){
//                try {
//                    int id = GlobalTools.convertStringToInt(map.get("id").toString());
//                    u.setId(id);
//                } catch (ToolLibException e) {
//                    log.error("ToolLibException:{}",e.toString());
//                }
//            }
            u.setRoles(mysqlDao.getRolesByUsername(userName));

        }

//        if (u == null) {
        if(u.getPassword() == null) {
            return null;
        }

        if(!passwordEncoder.matches(password,u.getPassword())) {
            log.info("pass match failed");
            return null;
        }

        return u;
    }

//    public boolean regist(String userName,String password){
//        boolean ret = false;
//        mysqlDao.addUser(userName,passwordEncoder.encode(password));
//
//        List<Map<String,Object>> mapList = mysqlDao.getAccountInfo(userName);
//        if(mapList.size()>0){
//            ret = true;
//        }
//
//        return ret;
//    }
}
