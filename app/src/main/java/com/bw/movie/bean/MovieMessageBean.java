package com.bw.movie.bean;

import java.util.List;

public class MovieMessageBean {

    private String director;
    private String duration;


    private boolean followMovie;
    private int id;
    private String imageUrl;
    private String movieTypes;
    private String name;
    private String placeOrigin;
    private int rank;
    private String starring;
    private String summary;
    private List<String> posterList;
    private List<ShortFilmListBean> shortFilmList;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isFollowMovie() {
        return followMovie;
    }

    public void setFollowMovie(boolean followMovie) {
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

    public String getMovieTypes() {
        return movieTypes;
    }

    public void setMovieTypes(String movieTypes) {
        this.movieTypes = movieTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceOrigin() {
        return placeOrigin;
    }

    public void setPlaceOrigin(String placeOrigin) {
        this.placeOrigin = placeOrigin;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getPosterList() {
        return posterList;
    }

    public void setPosterList(List<String> posterList) {
        this.posterList = posterList;
    }

    public List<ShortFilmListBean> getShortFilmList() {
        return shortFilmList;
    }

    public void setShortFilmList(List<ShortFilmListBean> shortFilmList) {
        this.shortFilmList = shortFilmList;
    }

    public static class ShortFilmListBean {
        /**
         * imageUrl : http://mobile.bwstudent.com/images/movie/stills/ws/ws2.jpg
         * videoUrl : http://mobile.bwstudent.com/video/movie/ws/ws1.mp4
         */

        private String imageUrl;
        private String videoUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
    }
}
