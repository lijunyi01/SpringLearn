package com.meijm.demo.vo;

//import lombok.Data;

import java.util.Date;
import java.util.Random;

//@Data
public class User {
    private String username;

    private Date joinTime;
    // 头像
    private String avatar;

    public User(String username){
        this.username = username;
        this.joinTime = new Date();
        this.avatar = "/image/avatar/avatar" + new Random().nextInt(10) + ".jpg";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return username.equalsIgnoreCase(user.getUsername().trim());
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
