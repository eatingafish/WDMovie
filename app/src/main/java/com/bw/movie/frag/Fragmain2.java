package com.bw.movie.frag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.bw.movie.adapter.frag2adapter.CinemaAdapter1;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.cinema.Cinemabean;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.cinema.CinemaPresenter;
import com.bw.movie.presenter.cinema.CinemaPresenter2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能: 影院页面
 */
public class Fragmain2 extends Fragment implements CustomAdapt {

    @BindView(R.id.cinema_location)
    ImageView cinemaLocation;
    @BindView(R.id.cimema_text)
    TextView cimemaText;
    @BindView(R.id.cinema_relative)
    RelativeLayout cinemaRelative;
    @BindView(R.id.cinema_tuijian)
    Button cinemaTuijian;
    @BindView(R.id.cinema_near)
    Button cinemaNear;
    @BindView(R.id.cinema_recycler)
    RecyclerView cinemaRecycler;
    Unbinder unbinder;
    private CinemaAdapter1 cinemaAdapter1;
    private CinemaPresenter cinemaPresenter;
    private CinemaPresenter2 cinemaPresenter2;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain2, container, false);
        unbinder = ButterKnife.bind(this, view);

        //默认布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cinemaRecycler.setLayoutManager(linearLayoutManager);

        //适配器
        cinemaAdapter1 = new CinemaAdapter1(getContext());
        cinemaRecycler.setAdapter(cinemaAdapter1);

        //默认推荐影院
        cinemaPresenter = new CinemaPresenter(new getData());
        cinemaPresenter.reqeust(1, 10);

        cinemaPresenter2 = new CinemaPresenter2(new getData());

        initData();
        return view;
    }

    private void initData() {
        mLocationClient = new LocationClient(getActivity());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

    }

    @OnClick({R.id.cinema_location, R.id.cinema_tuijian, R.id.cinema_near})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cinema_location:


                break;
            case R.id.cinema_tuijian:
                cinemaTuijian.setBackgroundResource(R.drawable.myattention_bg);
                cinemaTuijian.setTextColor(Color.WHITE);
                cinemaNear.setBackgroundResource(R.drawable.myattention_bg2);
                cinemaNear.setTextColor(Color.DKGRAY);
                cinemaAdapter1.remove();
                cinemaPresenter.reqeust(1, 10);

                break;
            case R.id.cinema_near:
                cinemaNear.setBackgroundResource(R.drawable.myattention_bg);
                cinemaNear.setTextColor(Color.WHITE);
                cinemaTuijian.setBackgroundResource(R.drawable.myattention_bg2);
                cinemaTuijian.setTextColor(Color.DKGRAY);
                cinemaAdapter1.remove();
                cinemaPresenter2.reqeust(1, 10, "116.30551391385724", "40.04571807462411");

                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            cimemaText.setText(locationDescribe + addr);

        }
    }


    private class getData implements DataCall<Result<List<Cinemabean>>> {
        @Override
        public void success(Result<List<Cinemabean>> data) {

            if (data.getStatus().equals("0000")) {
                List<Cinemabean> result = data.getResult();
                cinemaAdapter1.addItem(result);
                cinemaAdapter1.notifyDataSetChanged();
            }

        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getContext(), "失败" + e.getCode(), Toast.LENGTH_SHORT).show();
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
