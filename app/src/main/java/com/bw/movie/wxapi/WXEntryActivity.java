package com.bw.movie.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.activity.FragActivity;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.http.WeiXinUtil;
import com.bw.movie.presenter.WeixinPresenter;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.sql.SQLException;


public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private WeixinPresenter wxLoginPresenter;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(final BaseResp baseResp) {
    switch (baseResp.errCode){
        case BaseResp.ErrCode.ERR_OK:
            code = ((SendAuth.Resp) baseResp).code;
            wxLoginPresenter = new WeixinPresenter(new WxCall());
            wxLoginPresenter.reqeust(code);
            break;
            default:
                break;
    }
    }
    class WxCall implements DataCall<Result<User>> {

        @Override
        public void success(Result<User> data) {
            if (data.getStatus().equals("0000")){
                //登录成功
                Toast.makeText(WXEntryActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                //将登录信息存入数据库
                try {
                    UserDao userDao = new UserDao(WXEntryActivity.this);
                    userDao.insertStudent(data.getResult());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //跳转登录成功后的页面
                startActivity(new Intent(WXEntryActivity.this, FragActivity.class));
                finish();
                //下面只是一个跳转页面的动画，可不加
               // overridePendingTransition(R.anim.ac_in, R.anim.ac_out);

            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }
}
