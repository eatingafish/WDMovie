package com.bw.movie.frag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能: 影院页面
 */
public class Fragmain2 extends Fragment implements CustomAdapt {

    @BindView(R.id.cinemasdv)
    ImageView cinemasdv;
    @BindView(R.id.cimema_text)
    TextView cimemaText;
    @BindView(R.id.cinema_relative)
    RelativeLayout cinemaRelative;
    @BindView(R.id.cimema_tuijian)
    Button cimemaTuijian;
    @BindView(R.id.cimema_near)
    Button cimemaNear;
    @BindView(R.id.cinema_recycleview)
    RecyclerView cinemaRecycleview;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain2, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.cimema_tuijian, R.id.cimema_near})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cimema_tuijian:
                cimemaTuijian.setBackgroundResource(R.drawable.myattention_bg);
                cimemaTuijian.setTextColor(Color.WHITE);
                cimemaNear.setBackgroundResource(R.drawable.myattention_bg2);
                cimemaNear.setTextColor(Color.DKGRAY);

                break;
            case R.id.cimema_near:
                cimemaNear.setBackgroundResource(R.drawable.myattention_bg);
                cimemaNear.setTextColor(Color.WHITE);
                cimemaTuijian.setBackgroundResource(R.drawable.myattention_bg2);
                cimemaTuijian.setTextColor(Color.DKGRAY);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
