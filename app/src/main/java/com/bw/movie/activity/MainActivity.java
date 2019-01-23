package com.example.movie.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.movie.R;

import me.jessyan.autosize.internal.CustomAdapt;

public class MainActivity extends AppCompatActivity implements CustomAdapt {

    int sum = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.sendEmptyMessage(100);
        // SharedPreferences sharedPreferences =
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 100) {
                if (sum <= 0) {
                    startActivity(new Intent(MainActivity.this, GuideActivity.class));
                    finish();
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
