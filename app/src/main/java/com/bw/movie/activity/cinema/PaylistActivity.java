package com.bw.movie.activity.cinema;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.adapter.frag2adapter.CinemaPaiqiAdapter;
import com.bw.movie.adapter.frag2adapter.CinemabannerAdapter;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.bean.UserInfo;
import com.bw.movie.bean.cinema.Cinemamovie;
import com.bw.movie.bean.cinema.Cinemayingp;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.cinema.CinemaMoviePaiqi;
import com.bw.movie.presenter.cinema.CinemaMoviePresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.sql.SQLException;
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
    @BindView(R.id.cinema_time)
    TextView cinemaTime;
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
        View inflate = LayoutInflater.from(this).inflate(R.layout.cinema_plxq, null);
        plxq_down = inflate.findViewById(R.id.plxq_down);
        xiangqing = inflate.findViewById(R.id.xiangqing);
        pinglun = inflate.findViewById(R.id.pinglun);

        radiogroup = inflate.findViewById(R.id.cinema_ragroup);

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        final Cinemafrag1 cinemafrag1 = new Cinemafrag1();
//        final Cinemafrag2 cinemafrag2 = new Cinemafrag2();
//        transaction.add(R.id.cinema_frag, cinemafrag1);
//        transaction.add(R.id.cinema_frag,cinemafrag2);
//        transaction.show(cinemafrag1).hide(cinemafrag2);
//        transaction.commit();

//        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId)
//                {
//                    case  R.id.xiangqing:
//                        transaction1.show(cinemafrag1).hide(cinemafrag2);
//                        break;
//                    case R.id.pinglun:
//                        transaction1.show(cinemafrag2).hide(cinemafrag1);
//                        break;
//                }
//                transaction1.commit();
//            }
//        });
        //点击popwindow 落下
        plxq_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        int height = getWindowManager().getDefaultDisplay().getHeight();
        popupWindow = new PopupWindow(inflate, RelativeLayout.LayoutParams.MATCH_PARENT, height / 100 * 80);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置位置
        popupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
        //设置PopupWindow的View点击事件
    }


    //Banner
    private class getData implements DataCall<Result<List<Cinemamovie>>> {
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
