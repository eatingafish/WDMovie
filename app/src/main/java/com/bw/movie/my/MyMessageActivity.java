package com.bw.movie.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.bean.UserMessage;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.SelectUserPresenter;

import java.sql.SQLException;
import java.util.List;

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
    @BindView(R.id.mTv_UserName)
    TextView mTvUserName;
    @BindView(R.id.mTv_UserSex)
    TextView mTvUserSex;
    @BindView(R.id.mTv_UserBirthday)
    TextView mTvUserBirthday;
    @BindView(R.id.mTv_UserPhone)
    TextView mTvUserPhone;
    @BindView(R.id.mTv_UserMail)
    TextView mTvUserMail;
    private List<User> student;
    private String sessionId;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);

        try {
            UserDao userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
            }

            SelectUserPresenter selectUserPresenter = new SelectUserPresenter(new UserCall());
            selectUserPresenter.reqeust((int)userId,sessionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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


    @OnClick({R.id.my_back, R.id.my_pwd_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_back:
                finish();
                break;
            case R.id.my_pwd_update:
                startActivity(new Intent(this, MypwdupActivity.class));
                break;
        }
    }

    private class UserCall implements DataCall<Result<UserMessage>> {
        @Override
        public void success(Result<UserMessage> data) {

            if (data.getStatus().equals("0000")){

                mTvUserName.setText(data.getResult().getNickName());
                myMessageHead.setImageURI(Uri.parse(data.getResult().getHeadPic()));
               // mTvUserMail.setText(data.getResult().get);
                //mTvUserBirthday.setText(data.getResult().getBirthday());
                mTvUserPhone.setText(data.getResult().getPhone());
                mTvUserSex.setText(data.getResult().getSex());

            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
