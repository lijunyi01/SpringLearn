package allcom.service;

import allcom.dao.MysqlDao;
import allcom.bean.User;
import allcom.security.SSUserFactory;
import allcom.toolkit.GlobalTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ljy on 17/7/7.
 * ok
 */

@Service
public class SSUserDetailsServiceImpl implements UserDetailsService {

    private final MysqlDao mysqlDao;
    @Autowired
    public SSUserDetailsServiceImpl(MysqlDao mysqlDao) {
        this.mysqlDao = mysqlDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User();
        List<Map<String,Object>> mapList = mysqlDao.findByUsername(username);
        int admin_user_id;
        if(mapList.size()==1){
            Map<String,Object> map = mapList.get(0);
            if(map.get("id")!=null){
                admin_user_id = GlobalTools.convertStringToInt(map.get("id").toString());
                if(admin_user_id > -1) {
                    user.setId(admin_user_id);
                    user.setUsername(username);

                    if(map.get("passwd")!=null){
                        user.setPassword(map.get("passwd").toString());
                    }
                    if(map.get("email")!=null){
                        user.setEmail(map.get("email").toString());
                    }
                    if(map.get("lastPasswordResetDate")!=null){
                        String dateString = map.get("lastPasswordResetDate").toString();
                        Timestamp resetDate = Timestamp.valueOf(dateString);
                        if(resetDate !=null) {
                            user.setLastPasswordResetDate(resetDate);
                        }
                    }

                    List<String> roles = mysqlDao.getRolesByUserid(admin_user_id);
                    user.setRoles(roles);
                }
            }

        }

        if (user.getId() < 0) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return SSUserFactory.create(user);
        }
    }
}
