package com.bw.movie.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.bw.movie.adapter.ThreeListAdapter;
import com.bw.movie.bean.MovieBean;
import com.bw.movie.bean.MovieBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.MyCanclePresenter;
import com.bw.movie.presenter.MyLovePresenter;
import com.bw.movie.presenter.PopularPresenter;
import com.bw.movie.presenter.SoonPresenter;
import com.bw.movie.presenter.WellPresenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class ListActivity extends AppCompatActivity implements DataCall<Result<List<MovieBean>>>, CustomAdapt {

    @BindView(R.id.seacrch_editext)
    EditText seacrchEditext;
    @BindView(R.id.seacrch_text)
    TextView seacrchText;
    @BindView(R.id.seacrch_linear2)
    LinearLayout seacrchLinear2;
    private EditText et_sou;
    private TextView tv_sou;
    private RadioButton rb_one;
    private RadioButton rb_two;
    private RadioButton rb_three;
    int page=1;
    int count=10;
    List<MovieBean> remendianyinglist = new ArrayList<>();
    private ThreeListAdapter threeListAdapter;
    private PopularPresenter releaseMoviePresenter;
    private WellPresenter hotMoviePresenter;
    private SoonPresenter comingSoonMoviePresenter;
    private LocationClient mLocationClient = null;
    private TextView addre;
    private MyLocationListener myListener = new MyLocationListener();
    private List<User> student;
    private String sessionId;
    private int userId;
    CheckBox xins;
    private boolean animatort = false;
    private boolean animatorf = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        try {
            UserDao userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
            }
        ButterKnife.bind(this);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        hotMoviePresenter = new WellPresenter(this);
        hotMoviePresenter.reqeust(userId, sessionId, page, count);
        releaseMoviePresenter = new PopularPresenter(new Zhengzai());
        comingSoonMoviePresenter = new SoonPresenter(new Jjiang());

        //定位
        addre = findViewById(R.id.addre);
        mLocationClient = new LocationClient(getApplicationContext());
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

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        rb_one = findViewById(R.id.rb_one);
        rb_two = findViewById(R.id.rb_two);
        rb_three = findViewById(R.id.rb_three);
        RadioGroup radioGroup = findViewById(R.id.rg_aa);
        radioGroup.check(radioGroup.getChildAt(0).getId());
        rb_one.setTextColor(Color.WHITE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_one:
                        rb_one.setTextColor(Color.WHITE);
                        rb_two.setTextColor(Color.BLACK);
                        rb_three.setTextColor(Color.BLACK);
                        hotMoviePresenter.reqeust(userId, sessionId, page, count);
                        break;
                    case R.id.rb_two:
                        rb_two.setTextColor(Color.WHITE);
                        rb_one.setTextColor(Color.BLACK);
                        rb_three.setTextColor(Color.BLACK);
                        releaseMoviePresenter.reqeust(userId, sessionId, page, count);
                        break;
                    case R.id.rb_three:
                        rb_three.setTextColor(Color.WHITE);
                        rb_two.setTextColor(Color.BLACK);
                        rb_one.setTextColor(Color.BLACK);
                        comingSoonMoviePresenter.reqeust(userId, sessionId, page, count);
                        break;
                }
            }
        });


        RecyclerView recyclerView = findViewById(R.id.list);
        threeListAdapter = new ThreeListAdapter(this, remendianyinglist);
        threeListAdapter.xihuan(new ThreeListAdapter.MyLove() {
            @Override
            public void onLove(int possion,CheckBox xin) {
                xins = xin;
                MovieBean MovieBean = remendianyinglist.get(possion);
                int id = MovieBean.getId();
                MyLovePresenter myLovePresenter = new MyLovePresenter(new Xihuan());
                myLovePresenter.reqeust(userId, sessionId, id);
            }

            @Override
            public void onCancle(int possion, CheckBox xin) {
                xins = xin;
                MovieBean MovieBean = remendianyinglist.get(possion);
                int id = MovieBean.getId();
                MyCanclePresenter myCanclePresenter = new MyCanclePresenter(new Cancle());
                myCanclePresenter.reqeust(userId, sessionId, id);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(threeListAdapter);

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
    public void success(Result<List<MovieBean>> data) {
        remendianyinglist.clear();
        remendianyinglist.addAll(data.getResult());
        threeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void fail(ApiException e) {

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    /**
     * 正在热映
     */
    class Zhengzai implements DataCall<Result<List<MovieBean>>> {

        @Override
        public void success(Result<List<MovieBean>> data) {
            remendianyinglist.clear();
            remendianyinglist.addAll(data.getResult());
            threeListAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 即将上映
     */
    class Jjiang implements DataCall<Result<List<MovieBean>>> {

        @Override
        public void success(Result<List<MovieBean>> data) {
            remendianyinglist.clear();
            remendianyinglist.addAll(data.getResult());
            threeListAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

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
            addre.setText(addr);
        }
    }

    class Xihuan implements DataCall<Result> {

        @Override
        public void success(Result data) {
            xins.setBackgroundResource(R.drawable.xin2);
            Toast.makeText(ListActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Cancle implements DataCall<Result> {


        @Override
        public void success(Result data) {
            xins.setBackgroundResource(R.drawable.xin1);
            Toast.makeText( ListActivity.this,  ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    @Override
    public void onResume() {
        super.onResume();

        try {
            UserDao userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
