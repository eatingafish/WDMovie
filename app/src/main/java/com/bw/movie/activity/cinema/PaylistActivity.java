package com.bw.movie.activity.cinema;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.frag2adapter.CinemaPaiqiAdapter;
import com.bw.movie.adapter.frag2adapter.CinemabannerAdapter;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.cinema.Cinemamovie;
import com.bw.movie.bean.cinema.Cinemayingp;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.cinema.CinemaMoviePaiqi;
import com.bw.movie.presenter.cinema.CinemaMoviePresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * 点击影院获取影院详情
 */
public class PaylistActivity extends AppCompatActivity implements CustomAdapt, CinemabannerAdapter.onItemClick, CinemabannerAdapter.checkedMovieId {

    @BindView(R.id.cinema_logo)
    SimpleDraweeView cinemaLogo;
    @BindView(R.id.cinema_pay_name)
    TextView cinemaPayName;
    @BindView(R.id.cinema_pay_address)
    TextView cinemaPayAddress;
    @BindView(R.id.cinema_dingwei)
    ImageView cinemaDingwei;
    @BindView(R.id.cinema_rcf)
    RecyclerCoverFlow cinemaRcf;
    @BindView(R.id.cinema_time)
    TextView cinemaTime;
    @BindView(R.id.cinema_pay_recycler)
    RecyclerView cinemaPayRecycler;
    @BindView(R.id.payback)
    ImageView payback;
    private CinemabannerAdapter cinemabannerAdapter;
    private CinemaMoviePresenter cinemaMoviePresenter;
    private CinemaPaiqiAdapter cinemaPaiqiAdapter;
    private CinemaMoviePaiqi cinemaMoviePaiqi;
    private int movieid;
    private int cinemaid;
    private int movieid1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paylist);
        ButterKnife.bind(this);

        //接收传过来的值
        Intent intent = getIntent();
        cinemaid = intent.getIntExtra("id", 1);
        String logo = intent.getStringExtra("logo");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        cinemaLogo.setImageURI(logo);
        cinemaPayAddress.setText(address);
        cinemaPayName.setText(name);

        //banner 列表
        cinemaMoviePresenter = new CinemaMoviePresenter(new getData());
        cinemaMoviePresenter.reqeust(cinemaid);
        cinemabannerAdapter = new CinemabannerAdapter((Context) this, this);
        cinemabannerAdapter.setCheckedMovieId(this);
        cinemaRcf.setAdapter(cinemabannerAdapter);

        //影片排期

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cinemaPayRecycler.setLayoutManager(linearLayoutManager);

        cinemaMoviePaiqi = new CinemaMoviePaiqi(new getResult());

        //banner滑动 选中影片的ID
        cinemaRcf.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {

                cinemabannerAdapter.setCheckedMovieId(new CinemabannerAdapter.checkedMovieId() {
                    @Override
                    public void checkedMovieId(int id) {
                        cinemaMoviePaiqi.reqeust(cinemaid, id);
                    }
                });
                cinemaPaiqiAdapter.notifyDataSetChanged();
            }
        });
        cinemaPaiqiAdapter = new CinemaPaiqiAdapter(this);
        cinemaPayRecycler.setAdapter(cinemaPaiqiAdapter);
    }

    @Override
    public void checkedMovieId(int id) {
        cinemaMoviePaiqi.reqeust(cinemaid, id);
    }

    @OnClick(R.id.payback)
    public void onViewClicked() {
        finish();
    }



    //Banner
    private class getData implements DataCall<Result<List<Cinemamovie>>> {
        @Override
        public void success(Result<List<Cinemamovie>> data) {
            List<Cinemamovie> movieList = data.getMovieList();
            cinemabannerAdapter.addItem(movieList);
            cinemabannerAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //排期列表展示成功
    private class getResult implements DataCall<Result<List<Cinemayingp>>> {
        @Override
        public void success(Result<List<Cinemayingp>> data) {
            List<Cinemayingp> result = data.getResult();
            cinemaPaiqiAdapter.remove();
            cinemaPaiqiAdapter.addItem(result);
            cinemaPaiqiAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void clickItem(int position) {
        cinemaRcf.smoothScrollToPosition(position);
    }

    /**
     * 屏幕适配
     *
     * @return
     */
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }


}
