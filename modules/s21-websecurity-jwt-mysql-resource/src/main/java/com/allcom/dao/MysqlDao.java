package com.allcom.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ljy on 2017/11/5.
 * ok
 */
@Repository
public class  MysqlDao {

    final JdbcTemplate jdbcTemplate;

    @Autowired
    public MysqlDao(@Qualifier("jdbctemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //返回自增长id的值
//    public int addProject(final int tableindex,final int umid,final String projectname,final String projectdes, final String createtime){
//        int ret = -1;
//        KeyHolder key=new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator(){
//
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement preState=con.prepareStatement("insert into myproject"+ tableindex +"(umid,projectname,createtime,projectdes,lasttime) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//                preState.setInt(1,umid);
//                preState.setString(2,projectname);
//                preState.setString(3,createtime);
//                preState.setString(4,projectdes);
//                preState.setString(5,createtime);
//                return preState;
//            }
//        },key);
//        ret = key.getKey().intValue();
//        return ret;
//    }

//    public List<Map<String,Object>> getRegPhoneNum(String umid){
//        return jdbcTemplate.queryForList("select a.phone from cust_info a,cust_account_reletion b where b.service_account=? and a.cust_id=b.cust_id order by a.cust_id desc limit 1",umid);
//    }

    public List<Map<String,Object>> findByUsername(String userName){
        return jdbcTemplate.queryForList("select * from users where username=?",userName);
    }

//    public void saveToUser(String userName, String pass, Date lastPasswordResetDate,String email){
//        jdbcTemplate.update("insert into users(username,password,lastpasswordresetdate,email) values(?,?,?,?)",userName,pass,lastPasswordResetDate,email);
//    }
//
//    public void saveToUserRole(String userName,String role){
//        jdbcTemplate.update("insert into user_roles(username,role) values(?,?)",userName,role);
//    }

    public List<String> getRolesByUsername(String userName){
        List<String> ret = new ArrayList<>();
        List<Map<String,Object>> mapList = jdbcTemplate.queryForList("select role from user_roles where username=?",userName);
        for(Map<String,Object> map: mapList){
            if(map.get("role")!=null){
                ret.add(map.get("role").toString());
            }
        }
        return ret;
    }
}
