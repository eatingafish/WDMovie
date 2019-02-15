package com.bw.movie.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.adapter.frag2adapter.CinemaPaiqiAdapter;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.bean.cinema.Cinemayingp;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.cinema.CinemaMoviePaiqi;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 影片信息
 */
public class BuyMovieActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.cinema_pay_name)
    TextView cinemaPayName;
    @BindView(R.id.cinema_pay_address)
    TextView cinemaPayAddress;
    @BindView(R.id.l1)
    LinearLayout l1;
    @BindView(R.id.cinema_dingwei)
    ImageView cinemaDingwei;
    @BindView(R.id.movie_logo)
    SimpleDraweeView movieLogo;
    @BindView(R.id.cinema_buy_name)
    TextView cinemaBuyName;
    @BindView(R.id.cinema_buy_type)
    TextView cinemaBuyType;
    @BindView(R.id.cinema_buy_people)
    TextView cinemaBuyPeople;
    @BindView(R.id.cinema_buy_time)
    TextView cinemaBuyTime;
    @BindView(R.id.cinema_buy_guojia)
    TextView cinemaBuyGuojia;
    @BindView(R.id.cinema_rcf)
    RelativeLayout cinemaRcf;
    @BindView(R.id.cinema_pay_recycler)
    RecyclerView cinemaPayRecycler;
    @BindView(R.id.payback)
    ImageView payback;
    private CinemaPaiqiAdapter cinemaPaiqiAdapter;
    private CinemaMoviePaiqi cinemaMoviePaiqi;
    private List<User> student;
    private int userId;
    private String sessionId;


    @Override
    protected void onResume() {
        super.onResume();
        try {
            UserDao userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size()!=0){
                userId = student.get(0).getUserId();
                sessionId = student.get(0).getSessionId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        MobclickAgent.onPageStart(String.valueOf(this)); // 统计页面
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buymovie);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        try {
            UserDao userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size()!=0){
                userId = student.get(0).getUserId();
                sessionId = student.get(0).getSessionId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String cinemaname = intent.getStringExtra("cinemaname");
        String cinemaaddress = intent.getStringExtra("cinemaaddress");
        int movieid = intent.getIntExtra("movieid", 1);
        String moviename = intent.getStringExtra("moviename");
        String type = intent.getStringExtra("type");
        String timelong = intent.getStringExtra("timelong");
        String chandi = intent.getStringExtra("chandi");
        String daoyan = intent.getStringExtra("daoyan");
        String movieimage = intent.getStringExtra("movieimage");
        int cinemaid = intent.getIntExtra("cinemaid", 1);

        cinemaPayName.setText(cinemaname);
        cinemaPayAddress.setText(cinemaaddress);
        movieLogo.setImageURI(Uri.parse(movieimage));
        cinemaBuyName.setText(moviename);
        cinemaBuyType.setText(type);
        cinemaBuyGuojia.setText(chandi);
        cinemaBuyTime.setText(timelong);
        cinemaBuyPeople.setText(daoyan);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cinemaPayRecycler.setLayoutManager(linearLayoutManager);

        cinemaMoviePaiqi = new CinemaMoviePaiqi(new getData());
        cinemaMoviePaiqi.reqeust(userId,sessionId,cinemaid, movieid);

        cinemaPaiqiAdapter = new CinemaPaiqiAdapter(this);
        cinemaPayRecycler.setAdapter(cinemaPaiqiAdapter);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    @OnClick(R.id.payback)
    public void onViewClicked() {
        finish();
    }

    private class getData implements DataCall<Result<List<Cinemayingp>>> {
        @Override
        public void success(Result<List<Cinemayingp>> data) {
            List<Cinemayingp> result = data.getResult();
            cinemaPaiqiAdapter.addItem(result);
            cinemaPaiqiAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //if (!MTStringUtils.isEmpty(getPageName()))
        MobclickAgent.onPageEnd(String.valueOf(this));// 统计页面
    }

}
