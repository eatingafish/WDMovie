package com.bw.movie.bean;

public class MovieBean {
    private int id;
    private int followMovie;
    private String imageUrl;
    private String name;
    private int rank;

    public int isFollowMovie() {
        return followMovie;
    }

    public void setFollowMovie(int followMovie) {
        this.followMovie = followMovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    private String summary;

    public String getReleaseTimeShow() {
        return releaseTimeShow;
    }

    public void setReleaseTimeShow(String releaseTimeShow) {
        this.releaseTimeShow = releaseTimeShow;
    }

    private String releaseTimeShow;


}
