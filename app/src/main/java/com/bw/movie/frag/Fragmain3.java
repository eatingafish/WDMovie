package com.bw.movie.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.my.MyMessageActivity;
import com.bw.movie.my.Myattention;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能: 我的页面
 */
public class Fragmain3 extends Fragment implements CustomAdapt {

    @BindView(R.id.my_head)
    ImageView myHead;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_qiandao)
    Button myQiandao;
    @BindView(R.id.my_iv_message)
    ImageView myIvMessage;
    @BindView(R.id.my_tv_message)
    TextView myTvMessage;
    @BindView(R.id.my_iv_attention)
    ImageView myIvAttention;
    @BindView(R.id.my_tv_attention)
    TextView myTvAttention;
    @BindView(R.id.my_iv_rccord)
    ImageView myIvRccord;
    @BindView(R.id.my_tv_rccord)
    TextView myTvRccord;
    @BindView(R.id.my_iv_feedback)
    ImageView myIvFeedback;
    @BindView(R.id.my_tv_feedback)
    TextView myTvFeedback;
    @BindView(R.id.my_iv_version)
    ImageView myIvVersion;
    @BindView(R.id.my_tv_version)
    TextView myTvVersion;
    @BindView(R.id.my_iv_loginout)
    ImageView myIvLoginout;
    @BindView(R.id.my_tv_loginout)
    TextView myTvLoginout;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain3, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.my_iv_message, R.id.my_iv_attention, R.id.my_iv_rccord, R.id.my_iv_feedback, R.id.my_iv_version, R.id.my_iv_loginout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_iv_message:
                //跳转到我的信息
                startActivity(new Intent(getContext(), MyMessageActivity.class));
                break;
            case R.id.my_iv_attention:
                //跳转到我的关注
                startActivity(new Intent(getContext(), Myattention.class));
                break;
            case R.id.my_iv_rccord:
                break;
            case R.id.my_iv_feedback:
                break;
            case R.id.my_iv_version:
                break;
            case R.id.my_iv_loginout:
                break;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
