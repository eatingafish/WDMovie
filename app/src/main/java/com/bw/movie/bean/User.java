package com.bw.movie.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

public class User {


    /**
     * sessionId : 15320592619803
     * userId : 3
     * userInfo : {"birthday":320256000000,"id":3,"lastLoginTime":1532059192000,"nickName":"你的益达","phone":"18600151568","sex":1,"headPic":"http://172.17.8.100/images/head_pic/bwjy.jpg"}
     */



    private long userId;
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    private UserInfo userInfo;



}
