package allcom.dao;

import allcom.toolkit.GlobalTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ljy on 17/7/14.
 * ok
 */
@Repository
public class MysqlDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MysqlDao(@Qualifier("jdbctemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String,Object>> findByUsername(String username){
        return jdbcTemplate.queryForList("select * from t_admin_user where username=?",username);
    }

    public List<String> getRolesByUserid(int userid){
        List<String> ret = new ArrayList<>();
        List<Map<String,Object>> mapList = jdbcTemplate.queryForList("select b.role from t_user_role a,t_role b where a.userid=? and a.roleid=b.id",userid);
        if(mapList.size()>0){
            for(Map map: mapList){
                if(map.get("role")!=null){
                    ret.add(map.get("role").toString());
                }
            }
        }
        return ret;
    }

    //返回自增长字段
    public int saveUser(final String username, final String passwd, final Timestamp passResetDate){
        int ret = -1;
        KeyHolder key=new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator(){

            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preState=con.prepareStatement("insert into t_admin_user(username,passwd,lastPasswordResetDate) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
                preState.setString(1,username);
                preState.setString(2,passwd);
                preState.setString(3,passResetDate.toString());
                return preState;
            }
        },key);
        ret = key.getKey().intValue();
        return ret;
    }

    public int getRoleIdByRolename(String role){
        int ret = -1;
        List<Map<String,Object>> mapList = jdbcTemplate.queryForList("select id from t_role where role=?",role);
        if(mapList.size()==1){
            Map<String,Object> map = mapList.get(0);
            if(map.get("id")!=null){
                ret = GlobalTools.convertStringToInt(map.get("id").toString());
            }
        }
        return ret;
    }

    public void addRoleidToUser(int userid,int roleid){
        jdbcTemplate.update("insert into t_user_role(userid,roleid) values(?,?)",userid,roleid);
    }
}
