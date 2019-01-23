package com.example.movie;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
                    //startActivity(new Intent(MainActivity.this, GuideActivity.class));
                    //finish();
                    Toast.makeText(MainActivity.this, "hhh", Toast.LENGTH_SHORT).show();
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
}
