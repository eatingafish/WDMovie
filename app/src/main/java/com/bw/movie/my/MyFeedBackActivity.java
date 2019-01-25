package com.bw.movie.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bw.movie.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feedback);
        ButterKnife.bind(this);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    @OnClick({R.id.myfeedbace_button_tijiao, R.id.myfeedback_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myfeedbace_button_tijiao:
                break;
            case R.id.myfeedback_back:
                finish();
                break;
        }
    }
}
