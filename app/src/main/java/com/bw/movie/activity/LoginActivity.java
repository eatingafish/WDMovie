package com.bw.movie.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.exception.EncryptUtil;
import com.bw.movie.http.WeiXinUtil;
import com.bw.movie.presenter.LoginPresenter;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.sql.SQLException;
import java.util.List;

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
    private LoginPresenter loginPresenter;
    private List<User> student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        /*try {
            UserDao userDao = new UserDao(this);
            student = userDao.getStudent();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        loginPresenter = new LoginPresenter(new LoginCall());
        try {
            UserDao userDao = new UserDao(this);
            student = userDao.getStudent();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (student.size()>0){
            if (student.size()!=0) {
                if (student.get(0).getIsPwd() == 1) {

                    mCbPwdLogin.setChecked(true);

                    mEtPhoneLogin.setText(student.get(0).getPhone());

                    mEtPwdLogin.setText(student.get(0).getPwd());

                }

            }
            }



    }

    @OnClick({R.id.mIv_eye, R.id.mBt_ToReg_Login, R.id.mBt_Login, R.id.mIv_WeChat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //密码可见或者不可见
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
            //注册
            case R.id.mBt_ToReg_Login:

                startActivity(new Intent(LoginActivity.this, RegActivity.class));

                break;

            //登录
            case R.id.mBt_Login:
                String phone = mEtPhoneLogin.getText().toString().trim();
                String pwd = mEtPwdLogin.getText().toString().trim();
                String encrypt = EncryptUtil.encrypt(pwd);
                loginPresenter.reqeust(phone, encrypt);

                break;
            //微信登录
            case R.id.mIv_WeChat:
                //微信登录
                if (!WeiXinUtil.success(this)) {
                    Toast.makeText(this, "请先安装应用", Toast.LENGTH_SHORT).show();
                } else {
                    //  验证
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    WeiXinUtil.reg(LoginActivity.this).sendReq(req);
                    finish();
                }
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

    private class LoginCall implements DataCall<Result<User>> {
        @Override
        public void success(Result<User> data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(LoginActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                try {
                    UserDao userDao = new UserDao(LoginActivity.this);
                    data.getResult().setIsAuto(mCbAutoLogin.isChecked()?1:0);
                    data.getResult().setIsPwd(mCbPwdLogin.isChecked()?1:0);
                    data.getResult().setIsFirst(0);
                    String s = mEtPhoneLogin.getText().toString();
                    String s1 = mEtPwdLogin.getText().toString();
                    data.getResult().setPhone(s);
                    data.getResult().setPwd(s1);
                    student = userDao.getStudent();
                    student.clear();
                    userDao.deleteStudent(data.getResult());
                    userDao.insertStudent(data.getResult());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                finish();

            }else {
                Toast.makeText(LoginActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(LoginActivity.this, e.getCode() + "原因:" + e.getDisplayMessage(), Toast.LENGTH_SHORT).show();

        }
    }

}
