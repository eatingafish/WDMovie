package com.bw.movie.bean;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "user")
public class User  {
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * sessionId : 15320592619803
     * userId : 3
     * userInfo : {"birthday":320256000000,"id":3,"lastLoginTime":1532059192000,"nickName":"你的益达","phone":"18600151568","sex":1,"headPic":"http://172.17.8.100/images/head_pic/bwjy.jpg"}
     */

    @DatabaseField(generatedId = true)
    private int uid;
    @DatabaseField
    private long userId;
    @DatabaseField
    private String sessionId;
    @DatabaseField
    private int isPwd;
    @DatabaseField
    private int isAuto;



    @DatabaseField
    private int isFirst;
    @DatabaseField
    private String phone;

    public int getIsPwd() {
        return isPwd;
    }

    public void setIsPwd(int isPwd) {
        this.isPwd = isPwd;
    }

    public int getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(int isAuto) {
        this.isAuto = isAuto;
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @DatabaseField
    private String pwd;



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
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private UserInfo userInfo;



}
