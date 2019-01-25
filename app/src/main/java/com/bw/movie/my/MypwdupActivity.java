package com.bw.movie.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MypwdupActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.my_pwd_back)
    ImageView myPwdBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypwdup);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.my_pwd_back)
    public void onViewClicked() {
        finish();
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
