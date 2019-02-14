package com.bw.movie.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.adapter.SysMsgAdapter;
import com.bw.movie.bean.FindAllSysMsgList;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.FindAlllistPresenter;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MyAlllistActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.alllist_recycler)
    RecyclerView alllistRecycler;
    @BindView(R.id.findall_back)
    ImageView findallBack;
    private SysMsgAdapter sysMsgAdapter;
    private String sessionId;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myalllist);
        ButterKnife.bind(this);

        try {
            UserDao userDao = new UserDao(this);
            List<User> student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        alllistRecycler.setLayoutManager(linearLayoutManager);

        sysMsgAdapter = new SysMsgAdapter(this);
        alllistRecycler.setAdapter(sysMsgAdapter);

        FindAlllistPresenter findAlllistPresenter = new FindAlllistPresenter(new getData());
        findAlllistPresenter.reqeust(userId,sessionId,1,999);

    }

    @OnClick(R.id.findall_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class getData implements DataCall<Result<List<FindAllSysMsgList>>> {
        @Override
        public void success(Result<List<FindAllSysMsgList>> data) {
            if(data.getStatus().equals("0000"))
            {
                List<FindAllSysMsgList> result = data.getResult();
                sysMsgAdapter.addItem(result);
                sysMsgAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
