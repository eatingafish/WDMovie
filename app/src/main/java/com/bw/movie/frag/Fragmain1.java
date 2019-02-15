package com.bw.movie.frag;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.activity.ListActivity;
import com.bw.movie.activity.MovieMessageActivity;
import com.bw.movie.adapter.BannerAdapter;
import com.bw.movie.adapter.PopularAdapter_Rv;
import com.bw.movie.adapter.SoonAdapter_Rv;
import com.bw.movie.adapter.WellAdapter_Rv;
import com.bw.movie.bean.MovieBean;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.PopularPresenter;
import com.bw.movie.presenter.SoonPresenter;
import com.bw.movie.presenter.WellPresenter;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * 功能: 影片页面
 * Popular 热门电影
 * Well 正在热映
 * Soon 即将上映
 */
public class Fragmain1 extends Fragment implements CustomAdapt, BannerAdapter.onItemClick {


    Unbinder unbinder;


    @BindView(R.id.mRv_Popular)
    RecyclerView mRvPopular;
    @BindView(R.id.mRv_Well)
    RecyclerView mRvWell;
    @BindView(R.id.mRv_Soon)
    RecyclerView mRvSoon;
    @BindView(R.id.location)
    ImageView location;
    @BindView(R.id.location_text)
    TextView locationText;
    @BindView(R.id.rmdy1)
    TextView rmdy1;
    @BindView(R.id.mRl_Well)
    RelativeLayout mRlWell;
    @BindView(R.id.rmdy2)
    TextView rmdy2;
    @BindView(R.id.mRl_Soon)
    RelativeLayout mRlSoon;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.seacrch_editext)
    TextView seacrchEditext;
    @BindView(R.id.seacrch_text)
    TextView seacrchText;
    @BindView(R.id.seacrch_linear2)
    LinearLayout seacrchLinear2;
    @BindView(R.id.mBanner)
    RecyclerCoverFlow mBanner;
    @BindView(R.id.rmdy)
    TextView rmdy;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.mRl_Popular)
    RelativeLayout mRlPopular;
    private PopularPresenter popularPresenter;
    private WellPresenter wellPresenter;
    private SoonPresenter soonPresenter;
    private PopularAdapter_Rv popularAdapter_rv;
    private WellAdapter_Rv wellAdapter_rv;
    private SoonAdapter_Rv soonAdapter_rv;
    private RecyclerCoverFlow mList;
    private boolean animatort = false;
    private boolean animatorf = false;
    private BannerAdapter bannerAdapter;
    private LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain1, container, false);
        unbinder = ButterKnife.bind(this, view);

        try {
            UserDao userDao = new UserDao(getContext());
            List<User> student = userDao.getStudent();
            Toast.makeText(getContext(), student.size() + "", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //定位
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
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

        //这是刚进页面设置的动画状态
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrchLinear2, "translationX", 30f, 510f);
        animator.setDuration(0);
        animator.start();


        //设置RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        //热门电影
        popularPresenter = new PopularPresenter(new PopularCall());
        popularPresenter.reqeust(0, null, 1, 10);
        mRvPopular.setLayoutManager(layoutManager);
        popularAdapter_rv = new PopularAdapter_Rv(getContext());
        mRvPopular.setAdapter(popularAdapter_rv);

        //正在上映
        wellPresenter = new WellPresenter(new WellCall());
        wellPresenter.reqeust(0, null, 1, 10);
        mRvWell.setLayoutManager(layoutManager1);
        wellAdapter_rv = new WellAdapter_Rv(getContext());
        mRvWell.setAdapter(wellAdapter_rv);

        //即将上映
        soonPresenter = new SoonPresenter(new SoonCall());
        soonPresenter.reqeust(0, null, 1, 10);
        mRvSoon.setLayoutManager(layoutManager2);
        soonAdapter_rv = new SoonAdapter_Rv(getContext());
        mRvSoon.setAdapter(soonAdapter_rv);

        //Banner
        mList = view.findViewById(R.id.mBanner);
        bannerAdapter = new BannerAdapter(this, getContext());
        mList.setAdapter(bannerAdapter);

        return view;
    }

    @Override
    public void clickItem(int position) {
        mList.smoothScrollToPosition(position);
    }

    //定位
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getAddrStr();    //获取详细地址信息
            location.getAddress();
            double weidu = location.getLongitude();
            double jingdu = location.getLatitude();
            if (!location.equals("")) {
                locationText.setText(addr);
                mLocationClient.stop();
            }
        }
    }

    @OnClick(R.id.imageView)
    public void seacrch_linear2() {
        if (animatort) {
            return;
        }
        animatort = true;
        animatorf = false;
        //这是显示出现的动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrchLinear2, "translationX", 510f, 30f);
        animator.setDuration(1500);
        animator.start();
    }

    @OnClick(R.id.seacrch_text)
    public void seacrch_text() {
        if (animatorf) {
            return;
        }
        animatorf = true;
        animatort = false;
        //这是隐藏进去的动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrchLinear2, "translationX", 30f, 510f);
        animator.setDuration(1500);
        animator.start();
    }

    @OnClick({R.id.mRl_Popular, R.id.mRl_Well, R.id.mRl_Soon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mRl_Popular:
                startActivity(new Intent(getContext(), ListActivity.class));
                break;
            case R.id.mRl_Well:
                startActivity(new Intent(getContext(), ListActivity.class));
                break;
            case R.id.mRl_Soon:
                startActivity(new Intent(getContext(), ListActivity.class));
                break;
        }
    }

    /**
     * 热门电影请求接口返回值
     */
    private class PopularCall implements DataCall<Result<List<MovieBean>>> {
        @Override
        public void success(Result<List<MovieBean>> data) {

            if (data.getStatus().equals("0000")) {
                popularAdapter_rv.addData(data.getResult());
                popularAdapter_rv.setSendId(new PopularAdapter_Rv.sendId() {
                    @Override
                    public void sendId(int movieid) {
                        Intent intent = new Intent(getContext(), MovieMessageActivity.class);
                        intent.putExtra("movieid", movieid);
                        startActivity(intent);
                    }
                });
                popularAdapter_rv.notifyDataSetChanged();

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
                wellAdapter_rv.setSendId(new WellAdapter_Rv.sendId() {
                    @Override
                    public void sendId(int movieid) {
                        Intent intent = new Intent(getContext(), MovieMessageActivity.class);
                        intent.putExtra("movieid", movieid);
                        startActivity(intent);
                    }
                });
                wellAdapter_rv.notifyDataSetChanged();
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
                soonAdapter_rv.notifyDataSetChanged();
                soonAdapter_rv.setSendId(new SoonAdapter_Rv.sendId() {
                    @Override
                    public void sendId(int movieid) {
                        Intent intent = new Intent(getContext(), MovieMessageActivity.class);
                        intent.putExtra("movieid", movieid);
                        startActivity(intent);
                    }
                });
                bannerAdapter.addData(data.getResult());
                bannerAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
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
}
