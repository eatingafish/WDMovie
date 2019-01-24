package com.bw.movie.my;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bw.movie.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myattention);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.myattention_bt_movie, R.id.myattention_bt_cinema, R.id.myattention_iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myattention_bt_movie:
                myattentionBtMovie.setBackgroundResource(R.drawable.myattention_bg);
                myattentionBtMovie.setTextColor(Color.WHITE);
                myattentionBtCinema.setBackgroundResource(R.drawable.myattention_bg2);
                myattentionBtCinema.setTextColor(Color.DKGRAY);
                break;
            case R.id.myattention_bt_cinema:
                myattentionBtCinema.setBackgroundResource(R.drawable.myattention_bg);
                myattentionBtCinema.setTextColor(Color.WHITE);
                myattentionBtMovie.setBackgroundResource(R.drawable.myattention_bg2);
                myattentionBtMovie.setTextColor(Color.DKGRAY);
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
}
