package com.bw.movie.activity.cinema;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.adapter.frag2adapter.CinemaPaiqiAdapter;
import com.bw.movie.adapter.frag2adapter.CinemabannerAdapter;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.bean.cinema.Cinemamovie;
import com.bw.movie.bean.cinema.Cinemayingp;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.frag.CinemaDetailsPlFragment;
import com.bw.movie.frag.CinemaDetailsXqFragment;
import com.bw.movie.presenter.cinema.CinemaMoviePaiqi;
import com.bw.movie.presenter.cinema.CinemaMoviePresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * 点击影院获取影院详情
 */
public class PaylistActivity extends AppCompatActivity implements CustomAdapt, CinemabannerAdapter.onItemClick, CinemabannerAdapter.checkedMovieId {

    @BindView(R.id.cinema_logo)
    SimpleDraweeView cinemaLogo;
    @BindView(R.id.cinema_pay_name)
    TextView cinemaPayName;
    @BindView(R.id.cinema_pay_address)
    TextView cinemaPayAddress;
    @BindView(R.id.cinema_dingwei)
    ImageView cinemaDingwei;
    @BindView(R.id.cinema_rcf)
    RecyclerCoverFlow cinemaRcf;
    @BindView(R.id.cinema_pay_recycler)
    RecyclerView cinemaPayRecycler;
    @BindView(R.id.payback)
    ImageView payback;
    @BindView(R.id.l1)
    LinearLayout l1;
    private CinemabannerAdapter cinemabannerAdapter;
    private CinemaMoviePresenter cinemaMoviePresenter;
    private CinemaPaiqiAdapter cinemaPaiqiAdapter;
    private CinemaMoviePaiqi cinemaMoviePaiqi;
    private int movieid;
    private int cinemaid;
    private int movieid1;
    private PopupWindow popupWindow;
    private ImageView plxq_down;
    private RadioButton xiangqing;
    private RadioButton pinglun;
    private RadioGroup radiogroup;
    private List<User> student;
    private int userId;
    private String sessionId;
    private ImageView activityReviewPopwindowDown;
    private ViewPager vp;
    private View xqV;
    private View plV;
    private TextView pl;
    private TextView xq;
    private List<Fragment> fragments;

    @Override
    protected void onResume() {
        super.onResume();
        try {
            UserDao userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size()!=0){
                userId = student.get(0).getUserId();
                sessionId = student.get(0).getSessionId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paylist);
        ButterKnife.bind(this);

        try {
            UserDao userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size()!=0){
                userId = student.get(0).getUserId();
                sessionId = student.get(0).getSessionId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //接收传过来的值
        Intent intent = getIntent();
        cinemaid = intent.getIntExtra("id", 1);
        String logo = intent.getStringExtra("logo");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        cinemaLogo.setImageURI(logo);
        cinemaPayAddress.setText(address);
        cinemaPayName.setText(name);

        //banner 列表
        cinemaMoviePresenter = new CinemaMoviePresenter(new getData());
        cinemaMoviePresenter.reqeust(userId,sessionId,cinemaid);
        cinemabannerAdapter = new CinemabannerAdapter((Context) this, this);
        cinemabannerAdapter.setCheckedMovieId(this);
        cinemaRcf.setAdapter(cinemabannerAdapter);

        //影片排期

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cinemaPayRecycler.setLayoutManager(linearLayoutManager);

        cinemaMoviePaiqi = new CinemaMoviePaiqi(new getResult());

        //banner滑动 选中影片的ID
        cinemaRcf.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {

                cinemabannerAdapter.setCheckedMovieId(new CinemabannerAdapter.checkedMovieId() {
                    @Override
                    public void checkedMovieId(int id) {
                        cinemaMoviePaiqi.reqeust(userId,sessionId,cinemaid, id);
                    }
                });
                cinemaPaiqiAdapter.notifyDataSetChanged();
            }
        });
        cinemaPaiqiAdapter = new CinemaPaiqiAdapter(this);
        cinemaPayRecycler.setAdapter(cinemaPaiqiAdapter);
    }

    @Override
    public void checkedMovieId(int id) {
        cinemaMoviePaiqi.reqeust(userId,sessionId,cinemaid, id);
    }

    @OnClick(R.id.payback)
    public void onViewClicked() {
        finish();
    }

    //点击弹出影院详情和评论
    @OnClick(R.id.l1)
    public void yingy() {

        View rootview4 = LayoutInflater.from(PaylistActivity.this).inflate(R.layout.fragment_cinema_pop_, null);
        pop = rootview4;
        final PopupWindow popupWindow4 = new PopupWindow(rootview4);

        //设置充满父窗体
        popupWindow4.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow4.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//                popupWindow4.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
        //设置布局
        activityReviewPopwindowDown = pop.findViewById(R.id.fragment_review_popwindow_down);
        vp = pop.findViewById(R.id.fragment_details_vp);
        xqV = pop.findViewById(R.id.fragment_details_v);
        plV = pop.findViewById(R.id.fragment_details_vv);
        pl = pop.findViewById(R.id.fragment_details_pinglun);
        xq = pop.findViewById(R.id.fragment_details_xiangqing);
        popupWindow4.showAtLocation(rootview4, Gravity.BOTTOM, 0, 0);

        fragments = new ArrayList<>();
        fragments.add(new CinemaDetailsXqFragment());
        fragments.add(new CinemaDetailsPlFragment());

        xq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp.setCurrentItem(0);
                ChangeBackGround(0);
                Intent intent = new Intent(PaylistActivity.this,CinemaDetailsXqFragment.class);
                intent.putExtra("yingid",cinemaid);
                startActivity(intent);
            }
        });
        pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp.setCurrentItem(1);
                ChangeBackGround(1);
//                        EventBus.getDefault().postSticky(new EvBean(cinemaId));
//                       startActivity(new Intent(CinemaDetailActivity.this,CinemaDetailsPlFragment.class));
            }
        });

        vp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                ChangeBackGround(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //设置关闭popupWindow的点击事件
        activityReviewPopwindowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow4.dismiss();
            }
        });

}
    private View pop;
    @Override
    public <T extends View> T findViewById(int id) {
        if (id == R.id.fragment_details_vp && pop !=null){
            return pop.findViewById(id);
        }
        return super.findViewById(id);
    }

    private void ChangeBackGround(int index) {
        //
        xqV.setVisibility(index == 0 ? View.VISIBLE : View.GONE);
        //
        plV.setVisibility(index == 1 ? View.VISIBLE : View.GONE);
    }

    //Banner
    class getData implements DataCall<Result<List<Cinemamovie>>> {
        @Override
        public void success(Result<List<Cinemamovie>> data) {
            List<Cinemamovie> movieList = data.getMovieList();
            cinemabannerAdapter.addItem(movieList);
            cinemabannerAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //排期列表展示成功
    private class getResult implements DataCall<Result<List<Cinemayingp>>> {
        @Override
        public void success(Result<List<Cinemayingp>> data) {
            List<Cinemayingp> result = data.getResult();
            cinemaPaiqiAdapter.remove();
            cinemaPaiqiAdapter.addItem(result);
            cinemaPaiqiAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }

    }

    @Override
    public void clickItem(int position) {
        cinemaRcf.smoothScrollToPosition(position);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}




