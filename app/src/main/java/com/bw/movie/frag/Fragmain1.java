package com.bw.movie.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;

import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能: 影片页面
 */
public class Fragmain1 extends Fragment implements CustomAdapt {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain1, container, false);
        
        return view;
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
