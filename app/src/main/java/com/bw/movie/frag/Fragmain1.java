package com.bw.movie.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.adapter.BannerAdapter;
import com.bw.movie.adapter.PopularAdapter_Rv;
import com.bw.movie.adapter.SoonAdapter_Rv;
import com.bw.movie.adapter.WellAdapter_Rv;
import com.bw.movie.bean.MovieBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.PopularPresenter;
import com.bw.movie.presenter.SoonPresenter;
import com.bw.movie.presenter.WellPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * 功能: 影片页面
 * Popular 热门电影
 * Well 正在热映
 * Soon 即将上映
 */
public class Fragmain1 extends Fragment implements CustomAdapt {


    Unbinder unbinder;

    @BindView(R.id.mBanner)
    RecyclerCoverFlow mList;
    @BindView(R.id.mRv_Popular)
    RecyclerView mRvPopular;
    @BindView(R.id.mRv_Well)
    RecyclerView mRvWell;
    @BindView(R.id.mRv_Soon)
    RecyclerView mRvSoon;
    private PopularPresenter popularPresenter;
    private WellPresenter wellPresenter;
    private SoonPresenter soonPresenter;
    private PopularAdapter_Rv popularAdapter_rv;
    private WellAdapter_Rv wellAdapter_rv;
    private SoonAdapter_Rv soonAdapter_rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain1, container, false);

        unbinder = ButterKnife.bind(this, view);

        //设置RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        //热门电影
        popularPresenter = new PopularPresenter(new PopularCall());
        popularPresenter.reqeust(0,null,1,10);
        mRvPopular.setLayoutManager(layoutManager);
        popularAdapter_rv = new PopularAdapter_Rv(getContext());
        mRvPopular.setAdapter(popularAdapter_rv);
        //正在上映
        wellPresenter = new WellPresenter(new WellCall());
        wellPresenter.reqeust(0,null,1,10);
        mRvWell.setLayoutManager(layoutManager1);
        wellAdapter_rv = new WellAdapter_Rv(getContext());
        mRvWell.setAdapter(wellAdapter_rv);

        //即将上映
        soonPresenter = new SoonPresenter(new SoonCall());
        soonPresenter.reqeust(0,null,1,10);
        mRvSoon.setLayoutManager(layoutManager2);
        soonAdapter_rv = new SoonAdapter_Rv(getContext());
        mRvSoon.setAdapter(soonAdapter_rv);
        //Banner


        BannerAdapter bannerAdapter = new BannerAdapter(getContext());
        bannerAdapter.setOnItemClick(new BannerAdapter.onItemClick() {
            @Override
            public void clickItem(int position) {
                mList.smoothScrollToPosition(position);
            }
        });

        mList.setAdapter(bannerAdapter);
        onDestroyView();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    /**
     * 热门电影请求接口返回值
     */
    private class PopularCall implements DataCall<Result<List<MovieBean>>> {
        @Override
        public void success(Result<List<MovieBean>> data) {

            if (data.getStatus().equals("0000")){
                popularAdapter_rv.addData(data.getResult());

            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
    /**
     * 正在热映请求接口返回值
     */
    private class WellCall implements DataCall<Result<List<MovieBean>>> {
        @Override
        public void success(Result<List<MovieBean>> data) {
            if (data.getStatus().equals("0000")) {
                wellAdapter_rv.addData(data.getResult());
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 即将上映请求接口返回值
     */
    private class SoonCall implements DataCall<Result<List<MovieBean>>> {
        @Override
        public void success(Result<List<MovieBean>> data) {
            if (data.getStatus().equals("0000")) {
                soonAdapter_rv.addData(data.getResult());
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
