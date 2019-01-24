package com.bw.movie.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.my.MyMessageActivity;
import com.bw.movie.my.Myattention;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能: 我的页面
 */
public class Fragmain3 extends Fragment implements CustomAdapt {

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain3, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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

    @OnClick({R.id.mSdv_Sound, R.id.mSdv_Head, R.id.mSdv_Message, R.id.mSdv_Like, R.id.mSdv_Ticket, R.id.mSdv_FeedBack, R.id.mSdv_New, R.id.mSdv_LoginOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mSdv_Sound:
                break;
            case R.id.mSdv_Head:
                break;
            case R.id.mSdv_Message:

                startActivity(new Intent(getContext(), MyMessageActivity.class));

                break;
            case R.id.mSdv_Like:
                startActivity(new Intent(getContext(), Myattention.class));
                break;
            case R.id.mSdv_Ticket:
                break;
            case R.id.mSdv_FeedBack:
                break;
            case R.id.mSdv_New:
                break;
            case R.id.mSdv_LoginOut:
                break;
        }
    }
}
