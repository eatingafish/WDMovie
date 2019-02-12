package com.bw.movie.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MyFankuiSuccess extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.fksuccess_finsh)
    ImageView fksuccessFinsh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fankuisuccess);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.fksuccess_finsh)
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
