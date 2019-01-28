package com.bw.movie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.exception.EncryptUtil;
import com.bw.movie.presenter.RegPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class RegActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.mEt_Name_Reg)
    EditText mEtNameReg;
    @BindView(R.id.mEt_Sex_Reg)
    EditText mEtSexReg;
    @BindView(R.id.mEt_Data_Reg)
    EditText mEtDataReg;
    @BindView(R.id.mEt_Phone_Reg)
    EditText mEtPhoneReg;
    @BindView(R.id.mEt_Mail_Reg)
    EditText mEtMailReg;
    @BindView(R.id.mEt_Pwd_Reg)
    EditText mEtPwdReg;
    @BindView(R.id.mBt_Reg)
    Button mBtReg;
    private RegPresenter regPresenter;
    private int SEX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);

        regPresenter = new RegPresenter(new RegCall());
    }

    @OnClick(R.id.mBt_Reg)
    public void onViewClicked() {

        String name = mEtNameReg.getText().toString().trim();
        String sex = mEtSexReg.getText().toString().trim();
        String data = mEtDataReg.getText().toString().trim();
        String phone = mEtPhoneReg.getText().toString().trim();
        String main = mEtMailReg.getText().toString().trim();
        String pwd = mEtPwdReg.getText().toString().trim();
        if (sex.equals("男")){
            SEX = 1;
        }else {
            SEX = 2;
        }
        String encrypt = EncryptUtil.encrypt(pwd);
        regPresenter.reqeust(name,phone,encrypt,encrypt,SEX,data,"123456","小米","5.0","android",main);
        finish();

    }

    /**
     * 屏幕适配
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

    private class RegCall implements DataCall<Result> {
        @Override
        public void success(Result data) {

            if (data.getStatus().equals("0000")){
                Toast.makeText(RegActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(RegActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
