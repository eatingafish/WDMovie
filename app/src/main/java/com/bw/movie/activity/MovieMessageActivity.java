package com.bw.movie.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.adapter.JuZhaoAdapter;
import com.bw.movie.adapter.MovietalkAdapter;
import com.bw.movie.bean.MovieMessage;
import com.bw.movie.bean.MovieMessageBean;
import com.bw.movie.bean.Movietalkbean;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.MovieMessagePresenter;
import com.bw.movie.presenter.MoviesDPresenter;
import com.bw.movie.presenter.MovietalkPresenter;
import com.bw.movie.presenter.MyCanclePresenter;
import com.bw.movie.presenter.MyLovePresenter;
import com.bw.movie.presenter.WritePresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import me.jessyan.autosize.internal.CustomAdapt;

/***
 * 影片详情
 */
public class MovieMessageActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.mIv_Love)
    CheckBox mIvLove;
    @BindView(R.id.mTv_Name)
    TextView mTvName;
    @BindView(R.id.mSDv_Movie)
    SimpleDraweeView mSDvMovie;
    @BindView(R.id.lll)
    LinearLayout lll;
    private PopupWindow popupWindow;
    MovieMessageBean MovieMessageBean = new MovieMessageBean();
    private JZVideoPlayerStandard jzVideoPlayerStandard;
    ArrayList<MovieMessage> list = new ArrayList<>();
    private MovietalkAdapter movietalkAdapter;
    int page = 1;
    private MovietalkPresenter movietalkPresenter;
    private int userId;
    private String sessionId;
    private View inflate4;
    private Dialog bottomDialog;
    private MyLovePresenter myLovePresenter;
    private MyCanclePresenter myCanclePresenter;
    private int movieid;
    private MoviesDPresenter moviesDPresenter;
    private MovieMessagePresenter movieMessagePresenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_message);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        int p[] = {5};

        //Dialog弹框
        bottomDialog = new Dialog(MovieMessageActivity.this, R.style.BottomDialog);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        movieid = extras.getInt("movieid");
        movieMessagePresenter = new MovieMessagePresenter(new MovieCall());
         moviesDPresenter = new MoviesDPresenter(new DianYing());
        moviesDPresenter.reqeust(userId, sessionId, movieid);
        ButterKnife.bind(this);

        try {
            UserDao userDao = new UserDao(this);
            List<User> student = userDao.getStudent();
            if (student.size() != 0)
            {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
                Log.e("wj","MovieMessageActivity======"+sessionId);
                Log.e("wj","MovieMessageActivity======"+userId);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        movieMessagePresenter.reqeust(userId,sessionId, movieid);
        moviesDPresenter = new MoviesDPresenter(new DianYing());
        moviesDPresenter.reqeust(userId,sessionId, movieid);
        ButterKnife.bind(this);
        myLovePresenter = new MyLovePresenter(new LoveCall());
        myCanclePresenter = new MyCanclePresenter(new CancleCall());
        mIvLove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myLovePresenter.reqeust(userId,sessionId, movieid);
                }else {
                    myCanclePresenter.reqeust(userId,sessionId, movieid);
                }
            }
        });



    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        jzVideoPlayerStandard.releaseAllVideos();
        return super.onKeyDown(keyCode, event);
    }
    @OnClick({R.id.mBt_Message, R.id.mBt_Advance, R.id.mBt_Photo, R.id.mBt_Talk, R.id.mIv_Back, R.id.mBt_Buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mBt_Message:

                View inflate = View.inflate(this, R.layout.popu_movie, null);
                popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll, 0, 0, 0);
                SimpleDraweeView iv2 = inflate.findViewById(R.id.iv2);
                iv2.setImageURI(MovieMessageBean.getImageUrl());
                TextView leixing = inflate.findViewById(R.id.leixing);
                leixing.setText("类型：" + MovieMessageBean.getMovieTypes());
                TextView daoyan = inflate.findViewById(R.id.daoyan);
                daoyan.setText("导演：" + MovieMessageBean.getDirector());
                ImageView dowm = inflate.findViewById(R.id.down);
                TextView shichang = inflate.findViewById(R.id.shichang);
                shichang.setText("时长：" + MovieMessageBean.getDuration());
                TextView chandi = inflate.findViewById(R.id.chandi);
                TextView juqing = inflate.findViewById(R.id.juqing);
                chandi.setText("产地：" + MovieMessageBean.getPlaceOrigin());
                juqing.setText(MovieMessageBean.getSummary());
                 /*MovieMessageBean.get*/
                dowm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();

                    }
                });

                break;
            case R.id.mBt_Advance:
                View inflate2 = View.inflate(this, R.layout.popu_pian, null);
                popupWindow = new PopupWindow(inflate2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        jzVideoPlayerStandard.releaseAllVideos();
                    }
                });
                popupWindow.showAtLocation(lll, 0, 0, 0);
                ImageView back1 = inflate2.findViewById(R.id.back);
                jzVideoPlayerStandard = inflate2.findViewById(R.id.mJC);
                List<MovieMessageBean.ShortFilmListBean> shortFilmList = MovieMessageBean.getShortFilmList();
                MovieMessageBean.ShortFilmListBean shortFilmListBean = shortFilmList.get(0);
                jzVideoPlayerStandard.setUp(shortFilmListBean.getVideoUrl()
                        , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                jzVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(shortFilmListBean.getImageUrl()));
                back1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.mBt_Photo:
                View inflate1 = View.inflate(this, R.layout.popu_photo, null);
                popupWindow = new PopupWindow(inflate1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll, 0, 0, 0);
                ImageView back = inflate1.findViewById(R.id.back);
                RecyclerView recyclerView = inflate1.findViewById(R.id.list3);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                List<String> posterList = MovieMessageBean.getPosterList();
                JuZhaoAdapter juZhaoAdapter = new JuZhaoAdapter(this, posterList);
                recyclerView.setAdapter(juZhaoAdapter);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.mBt_Talk:
                View inflate3 = View.inflate(this, R.layout.movietalk, null);
                popupWindow = new PopupWindow(inflate3, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll, 0, 0, 0);
                ImageView movieback = inflate3.findViewById(R.id.movietalk_back);
                final XRecyclerView talklist = inflate3.findViewById(R.id.movietalk_list);
                ImageView writetalk = inflate3.findViewById(R.id.writetalk);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                talklist.setLayoutManager(linearLayoutManager);
                final int movieid = list.get(0).getId();
                movietalkPresenter = new MovietalkPresenter(new getData());
                movietalkPresenter.reqeust(userId,sessionId,movieid, page, 5);
                talklist.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        page = 1;
                        movietalkAdapter.remove();
                        movietalkPresenter.reqeust(userId,sessionId,movieid, page, 10);
                        movietalkAdapter.notifyDataSetChanged();
                        talklist.refreshComplete();
                    }

                    @Override
                    public void onLoadMore() {
                        page++;
                        movietalkPresenter.reqeust(userId,sessionId,movieid, page, 10);
                        movietalkAdapter.remove();
                        movietalkPresenter.reqeust(userId,sessionId,movieid, page, 10);
                        movietalkAdapter.notifyDataSetChanged();
                        talklist.loadMoreComplete();
                    }
                });
                movietalkAdapter = new MovietalkAdapter(this);
                talklist.setAdapter(movietalkAdapter);

                movieback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                inflate4 = View.inflate(MovieMessageActivity.this, R.layout.writetalk, null);
                Button fabiao = inflate4.findViewById(R.id.but_fabiao);
                final EditText edittalk = inflate4.findViewById(R.id.edit_talk);
                final int movieId = list.get(0).getId();
                //点击填写评论
                writetalk.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //   Dialog 弹框
                        bottomDialog.setContentView(inflate4);
                        ViewGroup.LayoutParams layoutParamsthreefilmreview = inflate4.getLayoutParams();
                        layoutParamsthreefilmreview.width = getResources().getDisplayMetrics().widthPixels;
                        inflate4.setLayoutParams(layoutParamsthreefilmreview);
                        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                        bottomDialog.setCanceledOnTouchOutside(true);
                        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                        bottomDialog.show();
                    }
                });
                fabiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String commentContent = edittalk.getText().toString().trim();
                        WritePresenter writePresenter = new WritePresenter(new getWrite());
                        writePresenter.reqeust(userId,sessionId,movieId,commentContent);
                        bottomDialog.dismiss();

                    }
                });

                break;
            case R.id.mIv_Back:
                finish();
                break;
            case R.id.mBt_Buy:
                Intent intent = new Intent(this, Movie_buy.class);
                intent.putExtra("moviename", list.get(0).getName());
                intent.putExtra("movieid", list.get(0).getId());
                intent.putExtra("movieimage", list.get(0).getImageUrl());
                intent.putExtra("type", MovieMessageBean.getMovieTypes());
                intent.putExtra("director", MovieMessageBean.getDirector());
                intent.putExtra("duration", MovieMessageBean.getDuration());
                intent.putExtra("PlaceOrigin", MovieMessageBean.getPlaceOrigin());
                startActivity(intent);
                break;
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

    private class MovieCall implements DataCall<Result<MovieMessage>> {
        @Override
        public void success(Result<MovieMessage> data) {

            if (data.getStatus().equals("0000")) {
                MovieMessage result = data.getResult();
                list.add(data.getResult());
                String imageUrl = data.getResult().getImageUrl();
                mSDvMovie.setImageURI(Uri.parse(imageUrl));
                mTvName.setText(result.getName());
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class DianYing implements DataCall<Result<MovieMessageBean>> {

        @Override
        public void success(Result<MovieMessageBean> data) {
            MovieMessageBean = data.getResult();
            String director = data.getResult().getDirector();
            int followMovie = data.getResult().isFollowMovie();
            MovieMessageBean.isFollowMovie();
            if (followMovie==1){
                mIvLove.setBackgroundResource(R.drawable.xin2);

            }else if (followMovie==2){
                mIvLove.setBackgroundResource(R.drawable.xin1);
            }
            //dLove.setChecked(followMovie==1?true:false);

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 影评成功*/

    private class getData implements DataCall<Result<List<Movietalkbean>>> {
        @Override
        public void success(Result<List<Movietalkbean>> data) {
            List<Movietalkbean> result = data.getResult();
            movietalkAdapter.addItem(result);
            movietalkAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 评论成功*/

    private class getWrite implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("9999"))
            {
                startActivity(new Intent(MovieMessageActivity.this, LoginActivity.class));
            }
            if (data.getStatus().equals("0000")) {
                Toast.makeText(MovieMessageActivity.this, "评论成功"+data.getStatus(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            UserDao userDao = new UserDao(this);
            List<User> student = userDao.getStudent();
            if (student.size() != 0)
            {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class LoveCall implements DataCall<Result> {
        @Override
        public void success(Result data) {

            if (data.getStatus().equals("0000")){
                mIvLove.setBackgroundResource(R.drawable.xin2);
                Toast.makeText(MovieMessageActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class CancleCall implements DataCall<Result> {
        @Override
        public void success(Result data) {


            if (data.getStatus().equals("0000")){
                mIvLove.setBackgroundResource(R.drawable.xin1);
                Toast.makeText(MovieMessageActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }else if (data.getStatus().equals("9999")){
                startActivity(new Intent(MovieMessageActivity.this, LoginActivity.class));
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}


