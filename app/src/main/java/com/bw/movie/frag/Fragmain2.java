package com.bw.movie.frag;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.adapter.frag2adapter.CinemaAdapter1;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.bean.cinema.Cinemabean;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.MyCancelCinemaPresenter;
import com.bw.movie.presenter.MyLoveCinemaPresenter;
import com.bw.movie.presenter.cinema.CinemaPresenter;
import com.bw.movie.presenter.cinema.CinemaPresenter2;

import java.sql.SQLException;
import java.util.ArrayList;
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
    RadioButton cinemaTuijian;
    @BindView(R.id.cinema_near)
    RadioButton cinemaNear;
    @BindView(R.id.cinema_recycler)
    RecyclerView cinemaRecycler;
    Unbinder unbinder;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.seacrch_editext)
    EditText seacrchEditext;
    @BindView(R.id.seacrch_text)
    TextView seacrchText;
    @BindView(R.id.seacrch_linear2)
    LinearLayout seacrchLinear2;
    @BindView(R.id.mGp)
    RadioGroup mGp;

    private CinemaAdapter1 cinemaAdapter1;
    private CinemaPresenter cinemaPresenter;
    private CinemaPresenter2 cinemaPresenter2;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private boolean animatort = false;
    private boolean animatorf = false;
    private MyLoveCinemaPresenter myLoveCinemaPresenter;
    private MyCancelCinemaPresenter myCancelCinemaPresenter;
    private List<User> student;
    private String sessionId;
    List<Cinemabean> cinemabeans = new ArrayList<>();
    private int userId;
    private CheckBox xins;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain2, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        try {
            UserDao userDao = new UserDao(getContext());
            student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        cinemaAdapter1 = new CinemaAdapter1(getContext());

        //请求关注/取消关注影院接口
        myLoveCinemaPresenter = new MyLoveCinemaPresenter(new LoveCinemaCall());
        myCancelCinemaPresenter = new MyCancelCinemaPresenter(new CancelCinemaCall());


        //默认布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cinemaRecycler.setLayoutManager(linearLayoutManager);


        //默认推荐影院
        cinemaPresenter = new CinemaPresenter(new getData2());
        cinemaPresenter2 = new CinemaPresenter2(new getData());
        cinemaPresenter.reqeust(userId, sessionId, 1, 10);

        //这是刚进页面设置的动画状态
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrchLinear2, "translationX", 30f, 510f);
        animator.setDuration(0);
        animator.start();
        cinemaRecycler.setAdapter(cinemaAdapter1);

        //动态权限
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {
            initData();//开始定位
            Toast.makeText(getActivity(), "已开启定位权限", Toast.LENGTH_LONG).show();
        }
        mGp.check(mGp.getChildAt(0).getId());
        mGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.cinema_tuijian:
                        cinemaTuijian.setBackgroundResource(R.drawable.myattention_bg);
                        cinemaTuijian.setTextColor(Color.WHITE);
                        cinemaNear.setBackgroundResource(R.drawable.myattention_bg2);
                        cinemaNear.setTextColor(Color.DKGRAY);
                        cinemaPresenter.reqeust(userId, sessionId, 1, 10);
                        break;
                    case R.id.cinema_near:
                        cinemaNear.setBackgroundResource(R.drawable.myattention_bg);
                        cinemaNear.setTextColor(Color.WHITE);
                        cinemaTuijian.setBackgroundResource(R.drawable.myattention_bg2);
                        cinemaTuijian.setTextColor(Color.DKGRAY);
                        cinemaPresenter2.reqeust(userId, sessionId, "116.306744", "40.047403", 1, 10);
                        break;
                }
            }
        });
        return view;
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

    @Override
    public void onResume() {
        super.onResume();

        try {
            UserDao userDao = new UserDao(getContext());
            student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        initData();
    }

    private void initData() {
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

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200://刚才的识别码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    initData();//开始定位
                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(getContext(), "未开启定位权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
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

            if (!location.equals("")) {
                mLocationClient.stop();
            }

            double weidu = location.getLongitude();
            double jingdu = location.getLatitude();
            cimemaText.setText(addr);
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

    /**
     * 关注影院
     */
    private class LoveCinemaCall implements DataCall<Result> {
        @Override
        public void success(Result data) {

            if (data.getStatus().equals("0000")) {
                xins.setBackgroundResource(R.drawable.xin2);
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            } else if (data.getStatus().equals("9999")) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class CancelCinemaCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                xins.setBackgroundResource(R.drawable.xin3);
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            } else if (data.getStatus().equals("9999")) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class getData implements DataCall<Result<List<Cinemabean>>> {
        @Override
        public void success(Result<List<Cinemabean>> data) {

            if (data.getStatus().equals("0000")) {
                //适配器


                List<Cinemabean> result = data.getResult();
                cinemaAdapter1.addItem(result);
                cinemaAdapter1.notifyDataSetChanged();
                cinemaAdapter1.setOnItemClick(new CinemaAdapter1.onItemClick() {
                    @Override
                    public void onClick(boolean isChecked, int id, CheckBox xin,int s) {
                        xins = xin;
                        if (s==2) {
                            myLoveCinemaPresenter.reqeust(userId, sessionId, id);
                            Log.e("sss", "onClick: "+userId+"灌灌灌灌"+sessionId+id);
                        } else {
                            myCancelCinemaPresenter.reqeust(userId, sessionId, id);
                            Log.e("sss", "onClick: "+userId+"灌灌灌灌"+sessionId);
                        }
                    }
                });
            }

        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getContext(), "失败" + e.getCode(), Toast.LENGTH_SHORT).show();
        }
    }


    private class getData2 implements DataCall<Result<List<Cinemabean>>> {
        @Override
        public void success(Result<List<Cinemabean>> data) {
            if (data.getStatus().equals("0000")) {
                //适配器


                List<Cinemabean> result = data.getResult();


                cinemaAdapter1.addItem(result);
                cinemaAdapter1.notifyDataSetChanged();
                cinemaAdapter1.setOnItemClick(new CinemaAdapter1.onItemClick() {
                    @Override
                    public void onClick(boolean isChecked, int id, CheckBox xin,int s) {
                        xins = xin;
                        if (s==2) {
                            myLoveCinemaPresenter.reqeust(userId, sessionId, id);
                        } else {
                            myCancelCinemaPresenter.reqeust(userId, sessionId, id);
                        }
                    }
                });
                cinemaAdapter1.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
