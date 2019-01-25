package com.bw.movie.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.MovieMessage;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.MovieMessagePresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MovieMessageActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.mIv_Love)
    ImageView mIvLove;
    @BindView(R.id.mTv_Name)
    TextView mTvName;
    @BindView(R.id.mSDv_Movie)
    SimpleDraweeView mSDvMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_message);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int movieid = extras.getInt("movieid");
        MovieMessagePresenter movieMessagePresenter = new MovieMessagePresenter(new MovieCall());
        movieMessagePresenter.reqeust(0,"",movieid);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.mBt_Message, R.id.mBt_Advance, R.id.mBt_Photo, R.id.mBt_Talk, R.id.mIv_Back, R.id.mBt_Buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mBt_Message:

                break;
            case R.id.mBt_Advance:

                break;
            case R.id.mBt_Photo:

                break;
            case R.id.mBt_Talk:

                break;
            case R.id.mIv_Back:

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

            if (data.getStatus().equals("0000")){
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
}
