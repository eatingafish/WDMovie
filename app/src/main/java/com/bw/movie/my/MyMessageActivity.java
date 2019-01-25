package com.bw.movie.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能:个人信息
 */
public class MyMessageActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.myMessage_head)
    ImageView myMessageHead;
    @BindView(R.id.my_back)
    ImageView myBack;
    @BindView(R.id.my_pwd_update)
    ImageView myPwdUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);

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


    @OnClick({R.id.my_back,R.id.my_pwd_update})
    public void onViewClicked(View view) {
        switch (view.getId())
        {
            case R.id.my_back:
                finish();
                break;
            case R.id.my_pwd_update:
                startActivity(new Intent(this,MypwdupActivity.class));
                break;
        }

    }

}
