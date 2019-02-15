package com.bw.movie.core;

import com.bw.movie.bean.CommentData;
import com.bw.movie.bean.DetailsData;
import com.bw.movie.bean.FindAllSysMsgList;
import com.bw.movie.bean.MovieBean;
import com.bw.movie.bean.MovieMessage;
import com.bw.movie.bean.MovieMessageBean;
import com.bw.movie.bean.Moviecinema;
import com.bw.movie.bean.Movietalkbean;
import com.bw.movie.bean.MyIsFollowListBean;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.TicketBean;
import com.bw.movie.bean.User;
import com.bw.movie.bean.UserBuyList;
import com.bw.movie.bean.UserMessage;
import com.bw.movie.bean.VipBean;
import com.bw.movie.bean.cinema.Cinemabean;
import com.bw.movie.bean.cinema.Cinemamovie;
import com.bw.movie.bean.cinema.Cinemayingp;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
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
    Observable<Result<List<Cinemabean>>> yingpian(@Header("userId") int userId,
                                                  @Header("sessionId") String sessionId,
                                                  @Query("page") int page,
                                                  @Query("count") int count);
    /**
     * 查询附近影院
     */
    @GET("movieApi/cinema/v1/findNearbyCinemas")
    Observable<Result<List<Cinemabean>>> near(@Header("userId") int userId,
                                              @Header("sessionId") String sessionId,
                                              @Query("longitude") String longitude,
                                              @Query("latitude") String latitude,
                                              @Query("page") int page,
                                              @Query("count") int count
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
    Observable<Result<List<Cinemamovie>>> moviel(@Header("userId") int userId,
                                                 @Header("sessionId") String sessionId,@Query("cinemaId") int cinemaId);

    /**
     * 根据电影ID和影院ID查询电影排期列表
     */
    @GET("movieApi/movie/v1/findMovieScheduleList")
    Observable<Result<List<Cinemayingp>>> dypaiqi(@Header("userId") int userId,
                                                  @Header("sessionId") String sessionId,@Query("cinemasId") int cinemasId,@Query("movieId") int movieId);
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
    Observable<Result<VipBean>> selectVip(@Header("userId") int userId,
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
     * 关注电影  未完善bean类
     *
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movieApi/movie/v1/verify/followMovie")
    Observable<Result> followMovie(@Header("userId") int userId,
                                   @Header("sessionId") String sessionId,
                                   @Query("movieId") int movieId);

    /**
     * 取消关注电影
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movieApi/movie/v1/verify/cancelFollowMovie")
    Observable<Result> cancelFollowMovie(@Header("userId") int userId,
                                         @Header("sessionId") String sessionId,
                                         @Query("movieId") int movieId);


    /**
     * 关注影院
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movieApi/cinema/v1/verify/followCinema")
    Observable<Result> followCinama(@Header("userId") int userId,
                                         @Header("sessionId") String sessionId,
                                         @Query("movieId") int movieId);

    /**
     * 取消关注影院
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movieApi/cinema/v1/verify/cancelFollowCinema")
    Observable<Result> cancelFollowCinama(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId,
                                          @Query("movieId") int movieId);

    /**
     * 创建订单
     *
     * @param userId
     * @param sessionId
     * @param scheduleId
     * @param amount
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("movieApi/movie/v1/verify/buyMovieTicket")
    Observable<Result> buyMovieTicket(@Header("userId") int userId,
                                      @Header("sessionId") String sessionId,
                                      @Field("scheduleId") int scheduleId,
                                      @Field("amount") int amount,
                                      @Field("sign") String sign);


    /**
     * 支付
     *
     * @param userId
     * @param sessionId
     * @param payType
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("movieApi/movie/v1/verify/pay")
    Observable<Result> pay(@Header("userId") int userId,
                            @Header("sessionId") String sessionId,
                            @Field("payType") int payType,
                            @Field("orderId") String orderId);

    /**
     * 根据电影ID查询当前排片该电影的影院列表
     */
    @GET("movieApi/movie/v1/findCinemasListByMovieId")
    Observable<Result<List<Moviecinema>>> yingyuan(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId,@Query("movieId") int movieId);

    /**
     * 查询影片评论
     */
    @GET("movieApi/movie/v1/findAllMovieComment")
    Observable<Result<List<Movietalkbean>>> pinglun(@Header("userId") int userId,
                                                    @Header("sessionId") String sessionId,@Query("movieId") int movieId,
                                                    @Query("page") int page,
                                                    @Query("count") int count);

    /**
     * 添加用户对评论的回复
     */
     @POST("movieApi/movie/v1/verify/movieComment")
    @FormUrlEncoded
    Observable<Result> write(@Header("userId") int userId,
                             @Header("sessionId") String sessionId,
                             @Field("movieId") int movieId,
                             @Field("commentContent") String commentContent
                             );



    @POST("movieApi/tool/v1/verify/recordFeedBack")
    @FormUrlEncoded
    Observable<Result<User>> feedBack(@Field("phone") String phone,
                                   @Field("pwd") String pwd);


    /**
     * 微信登录
     */
    @POST("movieApi/user/v1/weChatBindingLogin")
    @FormUrlEncoded
    Observable<Result<User>> weixin(@Field("code") String code);

    /**
     * 查询用户关注的影片关注
     */
    @GET("movieApi/movie/v1/verify/findMoviePageList")
    Observable<Result<List<MyIsFollowListBean>>> guanzhu(@Header("userId") int userId,
                                                         @Header("sessionId") String sessionId,
                                                         @Query("page") int page,
                                                         @Query("count") int count
                                                         );
    /**
     * 查询用户关注的影院
     */
    @GET("movieApi/cinema/v1/verify/findCinemaPageList")
    Observable<Result<List<MyIsFollowListBean>>> GZyy(@Header("userId") int userId,
                                                     @Header("sessionId") String sessionId,
                                                     @Query("page") int page,
                                                     @Query("count") int count);

    /**
     * 反馈意见
     */
    @POST("movieApi/tool/v1/verify/recordFeedBack")
    @FormUrlEncoded
    Observable<Result> fankui(@Header("userId") int userId,
                              @Header("sessionId") String sessionId,
                              @Field("content") String content
                              );

    /**
     * 购票记录
     */
    @GET("movieApi/user/v1/verify/findUserBuyTicketRecordList")
    Observable<Result<List<TicketBean>>> findTicket(@Header("userId") int userId,
                                                    @Header("sessionId") String sessionId,
                                                    @Query("page") int page,
                                                    @Query("count")int count,
                                                    @Query("status")int status);

    //修改用户信息
    @POST("movieApi/user/v1/verify/modifyUserInfo")
    @FormUrlEncoded
    Observable<Result> Update(@Header("userId")int userId,
                              @Header("sessionId")String sessionId,
                              @Field("nickName")String nickName,
                              @Field("sex") int sex,
                              @Field("email")String email);


    /**
     * 修改用户头像
     */
    @POST("movieApi/user/v1/verify/uploadHeadPic")
    Observable<Result> uploadHeadPic(@Header("userId") int userId, @Header("sessionId") String sessionId, @Body MultipartBody body);
    /**
     * 修改密码
     */
    @POST("movieApi/user/v1/verify/modifyUserPwd")
    @FormUrlEncoded
    Observable<Result> updatepwd(@Header("userId") int userId,
                                 @Header("sessionId") String sessionId,
                                 @Field("oldPwd") String oldPwd,
                                 @Field("newPwd") String newPwd,
                                 @Field("newPwd2") String newPwd2
                                 );

    /**
     * 查看系统消息
     */
    @GET("movieApi/tool/v1/verify/findAllSysMsgList")
    Observable<Result<List<FindAllSysMsgList>>> findall(@Header("userId") int userId,
                                                        @Header("sessionId") String sessionId,
                                                        @Query("page") int page,
                                                        @Query("count") int count
                                                        );

    //影院详情明细
    @GET("movieApi/cinema/v1/findCinemaInfo")
    Observable<Result<DetailsData>> getDetails(@Header("userId") int userId,
                                               @Header("sessionId") String sessionId,
                                               @Query("cinemaId") int cinemaId);

    //影院评论明细
    @GET("movieApi/cinema/v1/findAllCinemaComment")
    Observable<Result<List<CommentData>>> getComment(@Header("userId") int userId,
                                                     @Header("sessionId") String sessionId,
                                                     @Query("cinemaId") int cinemaId,
                                                     @Query("page") int page,
                                                     @Query("count") int count);

}
