package com.bw.movie.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.bean.User;

import java.sql.SQLException;
import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class MainActivity extends AppCompatActivity implements CustomAdapt {

    int sum = 2;
    private List<User> student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            UserDao userDao = new UserDao(MainActivity.this);
            student = userDao.getStudent();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(100);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 100) {

                if (sum <= 0) {
                        if (student.size()==0){
                            startActivity(new Intent(MainActivity.this, GuideActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(MainActivity.this, FragActivity.class));
                            finish();
                        }
                }
                sum--;
                handler.sendEmptyMessageDelayed(100, 1000);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(100);

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
