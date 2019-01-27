package com.bw.movie.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.bean.UserBuyList;
import com.bw.movie.bean.UserMessage;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.my.MyFeedBackActivity;
import com.bw.movie.my.MyMessageActivity;
import com.bw.movie.my.Myattention;
import com.bw.movie.presenter.SelectUserPresenter;
import com.bw.movie.presenter.SelectVipPresenter;
import com.bw.movie.presenter.SignPresenter;
import com.bw.movie.presenter.TicketRecordPresenter;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能: 我的页面
 */
public class Fragmain3 extends Fragment implements CustomAdapt {

    Unbinder unbinder;
    private SelectUserPresenter selectUserPresenter;
    private SignPresenter signPresenter;
    private TicketRecordPresenter ticketRecordPresenter;
    private List<User> student;
    private String sessionId;
    private long userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain3, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            UserDao userDao = new UserDao(getContext());
            student = userDao.getStudent();
            if (student.size()!=0){
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        selectUserPresenter = new SelectUserPresenter(new UserCall());
        signPresenter = new SignPresenter(new SignCall());

        ticketRecordPresenter = new TicketRecordPresenter(new TicketCall());
        return view;
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

    @OnClick({R.id.mSdv_Sound, R.id.mSdv_Head, R.id.mSdv_Message, R.id.mSdv_Like, R.id.mSdv_Ticket, R.id.mSdv_FeedBack, R.id.mSdv_New, R.id.mSdv_LoginOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mSdv_Sound:
                break;
            case R.id.mSdv_Head:
                break;
            case R.id.mSdv_Message:
                selectUserPresenter.reqeust((int)userId,sessionId);

                break;
            case R.id.mSdv_Like:
                startActivity(new Intent(getContext(), Myattention.class));
                break;
            case R.id.mSdv_Ticket:
                ticketRecordPresenter.reqeust((int)userId,sessionId);
                break;
            case R.id.mSdv_FeedBack:
                //
                break;
            case R.id.mSdv_New:
                break;
            case R.id.mSdv_LoginOut:
                break;
        }
    }

    @OnClick(R.id.mBt_QIanDao)
    public void onViewClicked() {
        signPresenter.reqeust((int)userId,sessionId);

    }

    private class UserCall implements DataCall<Result<UserMessage>> {
        @Override
        public void success(Result<UserMessage> data) {

            if (data.getStatus().equals("9999")){
                startActivity(new Intent(getContext(), LoginActivity.class));
            }else /*if (data.getStatus().equals("0000"))*/{
                startActivity(new Intent(getContext(), MyMessageActivity.class));
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class SignCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("9999")){
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class TicketCall implements DataCall<Result<List<UserBuyList>>> {
        @Override
        public void success(Result<List<UserBuyList>> data) {
            if (data.getStatus().equals("9999")){
                startActivity(new Intent(getContext(), LoginActivity.class));
            }else if (data.getStatus().equals("0000")){
                startActivity(new Intent(getContext(), MyFeedBackActivity.class));
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
