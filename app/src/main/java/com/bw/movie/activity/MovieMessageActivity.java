package com.bw.movie.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.JuZhaoAdapter;
import com.bw.movie.bean.MovieMessage;
import com.bw.movie.bean.MovieMessageBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.MovieMessagePresenter;
import com.bw.movie.presenter.MoviesDPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import me.jessyan.autosize.internal.CustomAdapt;

public class MovieMessageActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.mIv_Love)
    ImageView mIvLove;
    @BindView(R.id.mTv_Name)
    TextView mTvName;
    @BindView(R.id.mSDv_Movie)
    SimpleDraweeView mSDvMovie;
    @BindView(R.id.lll)
    LinearLayout lll;
    private PopupWindow popupWindow;
    MovieMessageBean MovieMessageBean = new MovieMessageBean();
    private JZVideoPlayerStandard jzVideoPlayerStandard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_message);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int movieid = extras.getInt("movieid");
        MovieMessagePresenter movieMessagePresenter = new MovieMessagePresenter(new MovieCall());
        movieMessagePresenter.reqeust(0, "", movieid);
        MoviesDPresenter moviesDPresenter = new MoviesDPresenter(new DianYing());
        moviesDPresenter.reqeust(0,"",movieid);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.mBt_Message, R.id.mBt_Advance, R.id.mBt_Photo, R.id.mBt_Talk, R.id.mIv_Back, R.id.mBt_Buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mBt_Message:

                View inflate = View.inflate(this, R.layout.popu_movie, null);
                popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll, 0, 0, 0);
                SimpleDraweeView iv2 = inflate.findViewById(R.id.iv2);
                iv2.setImageURI(MovieMessageBean.getImageUrl());
                TextView leixing = inflate.findViewById(R.id.leixing);
                leixing.setText("类型：" + MovieMessageBean.getMovieTypes());
                TextView daoyan = inflate.findViewById(R.id.daoyan);
                daoyan.setText("导演：" + MovieMessageBean.getDirector());
                ImageView dowm = inflate.findViewById(R.id.down);
                TextView shichang = inflate.findViewById(R.id.shichang);
                shichang.setText("时长：" + MovieMessageBean.getDuration());
                TextView chandi = inflate.findViewById(R.id.chandi);
                TextView juqing = inflate.findViewById(R.id.juqing);
                chandi.setText("产地：" + MovieMessageBean.getPlaceOrigin());
                juqing.setText(MovieMessageBean.getSummary());
                dowm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();

                    }
                });

                break;
            case R.id.mBt_Advance:
                View inflate2 = View.inflate(this, R.layout.popu_pian, null);
                popupWindow = new PopupWindow(inflate2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll, 0, 0, 0);
                ImageView back1 = inflate2.findViewById(R.id.back);
                jzVideoPlayerStandard = inflate2.findViewById(R.id.mJC);
                List<MovieMessageBean.ShortFilmListBean> shortFilmList = MovieMessageBean.getShortFilmList();
                MovieMessageBean.ShortFilmListBean shortFilmListBean = shortFilmList.get(0);
                jzVideoPlayerStandard.setUp(shortFilmListBean.getVideoUrl()
                        , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子闭眼睛");
                jzVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(shortFilmListBean.getImageUrl()));
                back1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.mBt_Photo:
                View inflate1 = View.inflate(this, R.layout.popu_photo, null);
                popupWindow = new PopupWindow(inflate1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll, 0, 0, 0);
                ImageView back = inflate1.findViewById(R.id.back);
                RecyclerView recyclerView = inflate1.findViewById(R.id.list3);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                List<String> posterList = MovieMessageBean.getPosterList();
                JuZhaoAdapter juZhaoAdapter = new JuZhaoAdapter(this, posterList);
                recyclerView.setAdapter(juZhaoAdapter);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.mBt_Talk:

                break;
            case R.id.mIv_Back:

                finish();
                break;
            case R.id.mBt_Buy:

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

    private class MovieCall implements DataCall<Result<MovieMessage>> {
        @Override
        public void success(Result<MovieMessage> data) {

            if (data.getStatus().equals("0000")) {
                MovieMessage result = data.getResult();
                String imageUrl = data.getResult().getImageUrl();
                mSDvMovie.setImageURI(Uri.parse(imageUrl));
                mTvName.setText(result.getDirector());
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
    class DianYing implements DataCall<Result<MovieMessageBean>> {

        @Override
        public void success(Result<MovieMessageBean> data) {
            Toast.makeText(MovieMessageActivity.this,  "11", Toast.LENGTH_SHORT).show();
            MovieMessageBean = data.getResult();
            String director = data.getResult().getDirector();
            int followMovie = MovieMessageBean.getFollowMovie();
            //dLove.setChecked(followMovie==1?true:false);

        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(MovieMessageActivity.this,  "11"+e.getCode(), Toast.LENGTH_SHORT).show();

        }
    }





}
