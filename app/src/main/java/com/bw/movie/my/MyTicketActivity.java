package com.bw.movie.my;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.adapter.MyAdapter;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.TicketBean;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.TicketPresenter;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MyTicketActivity extends AppCompatActivity implements CustomAdapt {
    @BindView(R.id.ticket_wait_money)
    Button ticketWaitMoney;
    @BindView(R.id.ticket_finish)
    Button ticketFinish;
    @BindView(R.id.ticket_recy)
    RecyclerView ticketRecy;
    @BindView(R.id.ticket_back)
    ImageView ticketback;
    private TicketPresenter ticketPresenter;
    private MyAdapter myAdapter;
    private int id= 1;
    private String sessionId;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        //id = Integer.parseInt(code);
        ticketWaitMoney.setBackgroundResource(R.drawable.myattention_bg);
        ticketWaitMoney.setTextColor(Color.WHITE);
        ticketFinish.setBackgroundResource(R.drawable.myattention_bg2);
        ticketFinish.setTextColor(Color.BLACK);
        ticketPresenter = new TicketPresenter(new MyCall());
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        ticketRecy.setLayoutManager(manager);
        myAdapter = new MyAdapter(this);
        ticketRecy.setAdapter(myAdapter);

        try {
            UserDao userDao = new UserDao(this);
            List<User> student = userDao.getStudent();
            if (student.size() != 0)
            {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
                Log.e("wj","MovieMessageActivity======"+ sessionId);
                Log.e("wj","MovieMessageActivity======"+ userId);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        ticketPresenter.reqeust(userId,sessionId,1,5,id);
       // ticketRecy.addItemDecoration(new SpaceItemDecoration(20));
}

    @OnClick({R.id.ticket_wait_money, R.id.ticket_finish,R.id.ticket_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ticket_wait_money:
                id = 1;
                myAdapter.clearIteam();
                ticketWaitMoney.setBackgroundResource(R.drawable.myattention_bg);
                ticketWaitMoney.setTextColor(Color.WHITE);
                ticketFinish.setBackgroundResource(R.drawable.myattention_bg2);
                ticketFinish.setTextColor(Color.BLACK);
                ticketPresenter.reqeust(userId,sessionId,1,5,id);
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.ticket_finish:
                id = 2;
                myAdapter.clearIteam();
                ticketPresenter.reqeust(userId,sessionId,1,5,id);
                ticketFinish.setBackgroundResource(R.drawable.myattention_bg);
                ticketFinish.setTextColor(Color.WHITE);
                ticketWaitMoney.setBackgroundResource(R.drawable.myattention_bg2);
                ticketWaitMoney.setTextColor(Color.BLACK);
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.ticket_back:
                finish();
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
    class MyCall implements DataCall<Result<List<TicketBean>>> {

        @Override
        public void success(Result<List<TicketBean>> result) {
            if(result.getStatus().equals("0000")){
                Log.d("qqq", "success: "+result.toString());
                Toast.makeText(MyTicketActivity.this, result.getMessage()+"成功", Toast.LENGTH_SHORT).show();
                List<TicketBean> result1 = result.getResult();
                myAdapter.addList(result1);
                myAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(MyTicketActivity.this, e.getMessage()+"失败", Toast.LENGTH_SHORT).show();
        }
    }
}
