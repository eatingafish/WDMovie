package com.bw.movie.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.exception.ApiException;
import com.bw.movie.exception.EncryptUtil;
import com.bw.movie.presenter.UpdataPwdPresenter;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MypwdupActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.my_pwd_back)
    ImageView myPwdBack;
    @BindView(R.id.dq_pwd)
    EditText dqPwd;
    @BindView(R.id.cz_pwd)
    EditText czPwd;
    @BindView(R.id.zc_pwd)
    EditText zcPwd;
    private String dppwd;
    private String czpwd;
    private String zcpwd;
    private UpdataPwdPresenter updataPwdPresenter;
    private UserDao userDao;
    private List<User> student;
    private String sessionId;
    private int userId;
    private String oldencrypt;
    private String newencryptone;
    private String newencrypttwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypwdup);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.my_pwd_back)
    public void onViewClicked() {
        dppwd = dqPwd.getText().toString().trim();
        czpwd = czPwd.getText().toString().trim();
        zcpwd = zcPwd.getText().toString().trim();

        boolean password = isPassword(czpwd);
        if (password == false)
        {
            Toast.makeText(this, "密码格式不正确,必须要有数字和英文，符号中的任意一种", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean passwordInfo = isPasswordInfo(czpwd);
        if (passwordInfo == false)
        {
            Toast.makeText(this, "密码必须大于8位", Toast.LENGTH_SHORT).show();
            return;
        }
        oldencrypt = EncryptUtil.encrypt(dppwd);
        newencryptone = EncryptUtil.encrypt(czpwd);
        newencrypttwo = EncryptUtil.encrypt(zcpwd);

        //数据库
        try {
            userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        updataPwdPresenter = new UpdataPwdPresenter(new DataCall());

        updataPwdPresenter.reqeust(userId,sessionId,oldencrypt,newencryptone,newencrypttwo);
    }

    /**
     * 验证密码，必须要6位字符以上。且必要要有数字和英文，符号中的任意两种
     * @param password
     * @return
     */
    @SuppressWarnings("unused")
    private static boolean isPasswordInfo(String password){
        Pattern pa=Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)(?!([^(0-9a-zA-Z)]|[\\(\\)])+$)([^(0-9a-zA-Z)]|[\\(\\)]|[a-zA-Z]|[0-9]){8,20}$");
        return pa.matcher(password).matches();
    }


    /**
     * 验证密码，且必要要有数字和英文，符号中的任意一种
     * @param password
     * @return
     */
    private static boolean isPassword(String password){
        Pattern pa=Pattern.compile("^[0-9a-zA-Z]{8,20}$");
        return pa.matcher(password).matches();
    }


    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class DataCall implements com.bw.movie.core.DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000"))
            {
                Toast.makeText(MypwdupActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(MypwdupActivity.this, "修改失败"+e.getCode(), Toast.LENGTH_SHORT).show();
        }
    }
}
