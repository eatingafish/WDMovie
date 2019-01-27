package com.bw.movie.core;

import com.bw.movie.bean.MovieBean;
import com.bw.movie.bean.MovieMessage;
import com.bw.movie.bean.MovieMessageBean;
import com.bw.movie.bean.Moviecinema;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.bean.UserBuyList;
import com.bw.movie.bean.UserMessage;
import com.bw.movie.bean.cinema.Cinemabean;
import com.bw.movie.bean.cinema.Cinemamovie;
import com.bw.movie.bean.cinema.Cinemayingp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Observable<Result<User>> login(@Field("phone") String phone,
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
    Observable<Result<List<MovieBean>>> polular(@Header("userId") int userId,
                                                      @Header("sessionId") String sessionId,
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
    Observable<Result<List<MovieBean>>> well(@Header("userId") int userId,
                                                      @Header("sessionId") String sessionId,
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
    Observable<Result<List<MovieBean>>> soon(@Header("userId") int userId,
                                                      @Header("sessionId") String sessionId,
                                                      @Query("page") int page,
                                                      @Query("count") int count);

    /**
     *查询推荐影院
     * @param page
     * @param count
     * @return
     */
    @GET("movieApi/cinema/v1/findRecommendCinemas")
    Observable<Result<List<Cinemabean>>> yingpian(@Query("page") int page,
                                                  @Query("count") int count);
    /**
     * 查询附近影院
     */
    @GET("movieApi/cinema/v1/findNearbyCinemas")
    Observable<Result<List<Cinemabean>>> near(@Query("page") int page,
                                              @Query("count") int count,
                                              @Query("longitude") String longitude,
                                              @Query("latitude") String latitude
                                              );


    /**
     * movieMessage
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movieApi/movie/v1/findMoviesById")
    Observable<Result<MovieMessage>> movieMessage(@Header("userId") int userId,
                                                  @Header("sessionId") String sessionId,
                                                  @Query("movieId") int movieId);

    /**
     * 根据影院ID查询该影院下即将上映的电影列表
     */
    @GET("movieApi/movie/v1/findSoonMovieByCinemaId")
    Observable<Result<List<Cinemamovie>>> moviel(@Query("cinemaId") int cinemaId);

    /**
     * 根据电影ID和影院ID查询电影排期列表
     */
    @GET("movieApi/movie/v1/findMovieScheduleList")
    Observable<Result<List<Cinemayingp>>> dypaiqi(@Query("cinemasId") int cinemasId,@Query("movieId") int movieId);
    /**
     * 电影详情
     *
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movieApi/movie/v1/findMoviesDetail")
    Observable<Result<MovieMessageBean>> findMoviesDetail(@Header("userId") int userId,
                                                          @Header("sessionId") String sessionId,
                                                          @Query("movieId") int movieId);

    /**
     * 查询会员首页信息
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("movieApi/user/v1/verify/findUserHomeInfo")
    Observable<Result<MovieMessageBean>> selectVip(@Header("userId") int userId,
                                                    @Header("sessionId") String sessionId);

    /**
     * 上传用户头像
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("movieApi/user/v1/verify/uploadHeadPic")
    Observable<Result<MovieMessageBean>> upImage(@Header("userId") int userId,
                                                    @Header("sessionId") String sessionId);

    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("movieApi/user/v1/verify/getUserInfoByUserId")
    Observable<Result<UserMessage>> selectUser(@Header("userId") int userId,
                                               @Header("sessionId") String sessionId);

    /**
     * 签到
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("movieApi/user/v1/verify/userSignIn")
    Observable<Result> Sign(@Header("userId") int userId,
                                                    @Header("sessionId") String sessionId);

    /**
     * 用户购票记录查询列表
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("movieApi/user/v1/verify/findUserBuyTicketRecordList")
    Observable<Result<List<UserBuyList>>> TicketRecord(@Header("userId") int userId,
                                                       @Header("sessionId") String sessionId);

    /**
     * 根据电影ID查询当前排片该电影的影院列表
     */
    @GET("movieApi/movie/v1/findCinemasListByMovieId")
    Observable<Result<List<Moviecinema>>> yingyuan(@Query("movieId") int movieId);

}
