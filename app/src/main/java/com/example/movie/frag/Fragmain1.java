package com.example.movie.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movie.R;
import com.example.movie.adapter.BannerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * 功能: 影片页面
 */
public class Fragmain1 extends Fragment {

    @BindView(R.id.mList)
    RecyclerView mList;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain1, container, false);
        unbinder = ButterKnife.bind(this, view);
        initList();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(new ListAdapter());
    }
}
