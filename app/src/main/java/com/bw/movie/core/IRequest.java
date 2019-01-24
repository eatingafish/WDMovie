package com.bw.movie.core;

import com.bw.movie.bean.MovieBean;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.bean.UserInfo;
import com.bw.movie.bean.cinema.Cinemabean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRequest {

    /**
     * 注册
     * @param nickName
     * @param phone
     * @param pwd
     * @param pwd2
     * @param sex
     * @param birthday
     * @param imei
     * @param ua
     * @param screenSize
     * @param os
     * @param email
     * @return
     */
    @POST("movieApi/user/v1/registerUser")
    @FormUrlEncoded
    Observable<Result> regiest(@Field("nickName") String nickName,
                               @Field("phone") String phone,
                               @Field("pwd") String pwd,
                               @Field("pwd2") String pwd2,
                               @Field("sex") int sex,
                               @Field("birthday") String birthday,
                               @Field("imei") String imei,
                               @Field("ua") String ua,
                               @Field("screenSize") String screenSize,
                               @Field("os") String os,
                               @Field("email") String email);


    /**
     * 登录
     * @param phone
     * @param pwd
     * @return
     */
    @POST("movieApi/user/v1/login")
    @FormUrlEncoded
    Observable<Result<User<UserInfo>>> login(@Field("phone") String phone,
                                             @Field("pwd") String pwd);

    /**
     * 查询热门电影列表
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movieApi/movie/v1/findHotMovieList")
    Observable<Result<User<List<MovieBean>>>> polular(@Query("userId") int userId,
                                                      @Query("sessionId") String sessionId,
                                                      @Query("page") int page,
                                                      @Query("count") int count);

    /**
     * 查询正在上映电影列表
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movieApi/movie/v1/findReleaseMovieList")
    Observable<Result<User<List<MovieBean>>>> well(@Query("userId") int userId,
                                                      @Query("sessionId") String sessionId,
                                                      @Query("page") int page,
                                                      @Query("count") int count);


    /**
     * 查询即将上映电影列表
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movieApi/movie/v1/findComingSoonMovieList")
    Observable<Result<User<List<MovieBean>>>> soon(@Query("userId") int userId,
                                                      @Query("sessionId") String sessionId,
                                                      @Query("page") int page,
                                                      @Query("count") int count);

    /**
     *查询附近影院
     * @param page
     * @param count
     * @return
     */
    @GET("movieApi/cinema/v1/findRecommendCinemas")
    Observable<Result<List<Cinemabean>>> yingpian(@Query("page") int page,
                                                  @Query("count") int count);
}
