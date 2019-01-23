package com.example.movie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movie.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.mBt_Reg)
    public void onViewClicked() {


        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
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
}
