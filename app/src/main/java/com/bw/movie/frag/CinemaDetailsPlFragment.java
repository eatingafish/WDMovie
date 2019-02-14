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
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.adapter.CommentAdapter;
import com.bw.movie.bean.CommentData;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.CommentPresenters;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 * Created by 申雨轩
 */

public class CinemaDetailsPlFragment extends Fragment {

    @BindView(R.id.details_pl_recy)
    RecyclerView detailsPlRecy;
    Unbinder unbinder;
    private CommentAdapter commentAdapter;
    private CommentPresenters commentPresenters;
    private int userId;
    private String sessionId;
    private int cinemaId;
    private List<User> student;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema_details_pl, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
        initData();
    }


    private void initData() {
        try {
            UserDao userDao = new UserDao(getContext());
            student = userDao.getStudent();
            if (student.size()!=0){
                userId = student.get(0).getUserId();
                sessionId = student.get(0).getSessionId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        commentAdapter = new CommentAdapter(getActivity());
        detailsPlRecy.setAdapter(commentAdapter);
        detailsPlRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentPresenters = new CommentPresenters(new CommentCall());
        commentPresenters.reqeust(userId, sessionId, 19, 1, 20);

    }


    class CommentCall implements DataCall<Result<List<CommentData>>> {

        @Override
        public void success(Result<List<CommentData>> data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                commentAdapter.addAll(data.getResult());
                commentAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

       // EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }
}

