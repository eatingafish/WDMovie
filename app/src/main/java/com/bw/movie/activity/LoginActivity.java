package com.example.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class LoginActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.mEt_Phone_login)
    EditText mEtPhoneLogin;
    @BindView(R.id.mEt_Pwd_Login)
    EditText mEtPwdLogin;
    @BindView(R.id.mIv_eye)
    ImageView mIvEye;
    @BindView(R.id.mCb_Pwd_Login)
    CheckBox mCbPwdLogin;
    @BindView(R.id.mCb_Auto_Login)
    CheckBox mCbAutoLogin;
    @BindView(R.id.mBt_ToReg_Login)
    Button mBtToRegLogin;
    @BindView(R.id.mBt_Login)
    Button mBtLogin;
    @BindView(R.id.mIv_WeChat)
    ImageView mIvWeChat;
    boolean canSee = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.mIv_eye, R.id.mBt_ToReg_Login, R.id.mBt_Login, R.id.mIv_WeChat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mIv_eye:
                if (canSee == false) {
                    //如果是不能看到密码的情况下，
                    mEtPwdLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    canSee = true;
                } else {
                    //如果是能看到密码的状态下
                    mEtPwdLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    canSee = false;
                }

                break;
            case R.id.mBt_ToReg_Login:

                startActivity(new Intent(this, RegActivity.class));

                break;
            case R.id.mBt_Login:

                startActivity(new Intent(this, FragActivity.class));
                finish();


                break;
            case R.id.mIv_WeChat:
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
