package com.bw.movie.my;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.adapter.MyIsFollowAdapter;
import com.bw.movie.bean.MyIsFollowListBean;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.CinemaAttentionPresenter;
import com.bw.movie.presenter.MyattentcinemaPresenter;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能:我的关注
 */
public class Myattention extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.myattention_bt_movie)
    Button myattentionBtMovie;
    @BindView(R.id.myattention_bt_cinema)
    Button myattentionBtCinema;
    @BindView(R.id.myattention_recycler)
    RecyclerView myattentionRecycler;
    @BindView(R.id.myattention_iv_back)
    ImageView myattentionIvBack;
    private String sessionId;
    private int userId;
    private MyIsFollowAdapter myIsFollowAdapter;
    private CinemaAttentionPresenter cinemaAttentionPresenter;
    private MyattentcinemaPresenter myattentcinemaPresenter;
    private List<User> student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myattention);
        ButterKnife.bind(this);
        try {
            UserDao userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size() != 0)
            {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
                Log.e("wj","MovieMessageActivity======"+ sessionId);
                Log.e("wj","MovieMessageActivity======"+ userId);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //设置默认布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        myattentionRecycler.setLayoutManager(linearLayoutManager);

        cinemaAttentionPresenter = new CinemaAttentionPresenter(new getData());
        myattentcinemaPresenter = new MyattentcinemaPresenter(new getData());
        cinemaAttentionPresenter.reqeust(userId,sessionId,1,10);

        myIsFollowAdapter = new MyIsFollowAdapter(this);
        myattentionRecycler.setAdapter(myIsFollowAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            UserDao userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size() != 0)
            {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
                Log.e("wj","MovieMessageActivity======"+ sessionId);
                Log.e("wj","MovieMessageActivity======"+ userId);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.myattention_bt_movie, R.id.myattention_bt_cinema, R.id.myattention_iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myattention_bt_movie:
                myattentionBtMovie.setBackgroundResource(R.drawable.myattention_bg);
                myattentionBtMovie.setTextColor(Color.WHITE);
                myattentionBtCinema.setBackgroundResource(R.drawable.myattention_bg2);
                myattentionBtCinema.setTextColor(Color.DKGRAY);

                myIsFollowAdapter = new MyIsFollowAdapter(this);
                myattentionRecycler.setAdapter(myIsFollowAdapter);
                cinemaAttentionPresenter.reqeust(userId,sessionId,1,10);

                break;
            case R.id.myattention_bt_cinema:
                myattentionBtCinema.setBackgroundResource(R.drawable.myattention_bg);
                myattentionBtCinema.setTextColor(Color.WHITE);
                myattentionBtMovie.setBackgroundResource(R.drawable.myattention_bg2);
                myattentionBtMovie.setTextColor(Color.DKGRAY);
                myIsFollowAdapter = new MyIsFollowAdapter(this);
                myattentionRecycler.setAdapter(myIsFollowAdapter);
                myattentcinemaPresenter.reqeust(userId,sessionId,1,10);
                break;
            case R.id.myattention_iv_back:
                finish();
                break;
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

    /**
     * 我关注的影片
     */
    private class getData implements DataCall<Result<List<MyIsFollowListBean>>> {
        @Override
        public void success(Result<List<MyIsFollowListBean>> data) {
            if (data.getStatus().equals("0000")) {
                List<MyIsFollowListBean> result = data.getResult();
                myIsFollowAdapter.addItem(result);
                myIsFollowAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
