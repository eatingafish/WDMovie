package com.bw.movie.bean;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class UserInfo implements Serializable {
    @DatabaseField
    private long birthday;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private long lastLoginTime;
    @DatabaseField
    private String nickName;
    @DatabaseField
    private String phone;
    @DatabaseField
    private int sex;


    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    @DatabaseField
    private String headPic;

}
