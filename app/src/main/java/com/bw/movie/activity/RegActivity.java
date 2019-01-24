package com.bw.movie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bw.movie.R;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.exception.EncryptUtil;
import com.bw.movie.presenter.RegPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    int SEX = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        regPresenter = new RegPresenter(new RegCall());

    }

    /**
     * 日期转换
     *
     * @param date
     * @return
     */
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /*@OnClick({R.id.mBt_Reg,R.id.mEt_Data_Reg})
    public void onViewClicked() {


    }*/

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

    @OnClick({R.id.mEt_Data_Reg, R.id.mBt_Reg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mEt_Data_Reg:

                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        mEtDataReg.setText(getTime(date));
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                //.setTitleText("Title")//标题文字
//                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
////                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
////                .setRangDate(startDate,endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        //.isDialog(true)//是否显示为对话框样式
                        .build();

                pvTime.show();

                break;
            case R.id.mBt_Reg:
                String name = mEtNameReg.getText().toString().trim();
                String phone = mEtPhoneReg.getText().toString().trim();
                String pwd = mEtPwdReg.getText().toString().trim();
                String sex = mEtSexReg.getText().toString().trim();
                if (sex.equals("男")) {
                    SEX = 1;
                } else {
                    SEX = 2;
                }
                String encrypt = EncryptUtil.encrypt(pwd);

                String date = mEtDataReg.getText().toString().trim();
                String email = mEtMailReg.getText().toString().trim();
                regPresenter.reqeust(name, phone, encrypt, encrypt, SEX, date, "123456", "小米", " 5.0", "android", email);
                Toast.makeText(this, "注册", Toast.LENGTH_SHORT).show();

                break;
        }
    }


    private class RegCall implements DataCall<Result> {
        @Override
        public void success(Result data) {

            if (data.getStatus().equals("0000")) {
                Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            }

        }

        @Override
        public void fail(ApiException e) {

            Toast.makeText(RegActivity.this, e.getCode() + "原因：" + e.getDisplayMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}
