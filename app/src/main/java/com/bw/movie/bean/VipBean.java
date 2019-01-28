package com.bw.movie.bean;

import java.util.List;

public class VipBean {

    private String headPic;
    private int integral;
    private String nickName;
    private String phone;
    private int userSignStatus;
    private List<CinemasListBean> cinemasList;
    private List<MoviesListBean> movieList;


    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
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

    public int getUserSignStatus() {
        return userSignStatus;
    }

    public void setUserSignStatus(int userSignStatus) {
        this.userSignStatus = userSignStatus;
    }

    public List<CinemasListBean> getCinemasList() {
        return cinemasList;
    }

    public void setCinemasList(List<CinemasListBean> cinemasList) {
        this.cinemasList = cinemasList;
    }

    public List<MoviesListBean> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MoviesListBean> movieList) {
        this.movieList = movieList;
    }



}
