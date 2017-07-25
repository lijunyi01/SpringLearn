package allcom.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
//import java.util.Date;

public class SSUser implements UserDetails {
    private final int id;
    private final String username;
    private final String password;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Timestamp lastPasswordResetDate;

    public SSUser(
            int id,
            String username,
            String password,
            String email,
            Collection<? extends GrantedAuthority> authorities,
            Timestamp lastPasswordResetDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    //返回分配给用户的角色列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    // 账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 账户是否未锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 账户是否激活
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
    // 这个是自定义的，返回上次密码重置日期
    @JsonIgnore
    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public String getEmail() {
        return email;
    }


    //以下两项对sessionManagement很重要，maximumSessions(1)就取决于SSUser对象的比较，这里如果不重载，
    //相同用户名密码的用户从不同的地方登录，系统会认为两个SSUser相同，从而哪怕设置了maximumSessions(1)，也会都允许登录！
    @Override
    public int hashCode(){
        //hashCode()的返回值可以理解为username这个对象的内存地址；上述情况下username值相同，但内存地址不同，属于两个不同的对象
        return username.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SSUser) {
            return username.equals(((SSUser) o).username);
        }
        return false;
    }

    /*
    *它是一个本地方法，它的实现与本地机器有关，这里我们暂且认为他返回的是对象存储的物理位置（实际上不是，这里写是便于理解）。
    * 当我们向一个集合中添加某个元素，集合会首先调用hashCode方法，这样就可以直接定位它所存储的位置，若该处没有其他元素，则直接保存。
    * 若该处已经有元素存在，就调用equals方法来匹配这两个元素是否相同，相同则不存，不同则散列到其他位置（具体情况请参考（Java提高篇（）—–HashMap））。
    * 这样处理，当我们存入大量元素时就可以大大减少调用equals()方法的次数，极大地提高了效率
     */
}
