package com.bw.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.adapter.MoviebuyAdapter;
import com.bw.movie.bean.Moviecinema;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.MoviebuyPresenter;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 选择合适影院
 */
public class Movie_buy extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.movie_name)
    TextView movieName;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.movie_cinema)
    RecyclerView movieCinema;
    @BindView(R.id.movie_back)
    ImageView movieBack;
    private MoviebuyPresenter moviebuyPresenter;
    private MoviebuyAdapter moviebuyAdapter;

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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviebuy);
        ButterKnife.bind(this);
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
        final Intent intent = getIntent();
        final int movieid = intent.getIntExtra ("movieid", 1);//影片id
        final String moviename = intent.getStringExtra("moviename"); //接受传过来的 电影姓名
        final String movieimage = intent.getStringExtra("movieimage"); //电影封面
        final String type = intent.getStringExtra("type"); //电影类型
        final String director = intent.getStringExtra("director"); //电影导演
        final String duration = intent.getStringExtra("duration"); //电影时长
        final String placeOrigin = intent.getStringExtra("PlaceOrigin"); //电影产地
        movieName.setText(moviename);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        movieCinema.setLayoutManager(linearLayoutManager);

        moviebuyPresenter = new MoviebuyPresenter(new getData());
        moviebuyPresenter.reqeust(userId,sessionId,movieid);
        moviebuyAdapter = new MoviebuyAdapter(this);
        movieCinema.setAdapter(moviebuyAdapter);

        moviebuyAdapter.setOnListCinemaLister(new MoviebuyAdapter.OnListCinemaLister() {
            @Override
            public void onList(int cinemaid, String cinemaname, String address) {
                Intent intent1 = new Intent(Movie_buy.this,BuyMovieActivity.class);
                intent1.putExtra("movieid",movieid);
                intent1.putExtra("moviename",moviename);
                intent1.putExtra("cinemaid",cinemaid);
                intent1.putExtra("cinemaname",cinemaname);
                intent1.putExtra("cinemaaddress",address);
                intent1.putExtra("movieimage",movieimage);
                intent1.putExtra("type",type);
                intent1.putExtra("timelong",duration);
                intent1.putExtra("chandi",director);
                intent1.putExtra("daoyan",placeOrigin);
                startActivity(intent1);

            }
        });
    }



    @OnClick(R.id.movie_back)
    public void onViewClicked() {
        finish();
    }


    private class getData implements DataCall<Result<List<Moviecinema>>> {
        @Override
        public void success(Result<List<Moviecinema>> data) {
            List<Moviecinema> result = data.getResult();
            moviebuyAdapter.addItem(result);
            moviebuyAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }


}
