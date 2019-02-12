package com.bw.movie.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.MyFankuiPresenter;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能:意见反馈
 */
public class MyFeedBackActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.myfeedback_edit_ganshou)
    EditText myfeedbackEditGanshou;
    @BindView(R.id.myfeedbace_button_tijiao)
    Button myfeedbaceButtonTijiao;
    @BindView(R.id.myfeedback_back)
    ImageView myfeedbackBack;
    private String fankui;
    private MyFankuiPresenter myFankuiPresenter;
    private String sessionId;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feedback);
        ButterKnife.bind(this);

        fankui = myfeedbackEditGanshou.getText().toString().trim();
        myFankuiPresenter = new MyFankuiPresenter(new getData());

        try {
            UserDao userDao = new UserDao(this);
            List<User> student = userDao.getStudent();
            if (student.size() != 0)
            {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
                Log.e("wj","MovieMessageActivity======"+ sessionId);
                Log.e("wj","MovieMessageActivity======"+ userId);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.myfeedbace_button_tijiao, R.id.myfeedback_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myfeedbace_button_tijiao:

                myFankuiPresenter.reqeust(userId,sessionId,fankui);
                startActivity(new Intent(this, MyFankuiSuccess.class));
                break;
            case R.id.myfeedback_back:
                finish();
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


    private class getData implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000"))
            {
                Toast.makeText(MyFeedBackActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
