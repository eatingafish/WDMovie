package com.bw.movie.frag;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.bean.UserBuyList;
import com.bw.movie.bean.UserMessage;
import com.bw.movie.bean.VipBean;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.my.MyFeedBackActivity;
import com.bw.movie.my.MyMessageActivity;
import com.bw.movie.my.Myattention;
import com.bw.movie.presenter.SelectUserPresenter;
import com.bw.movie.presenter.SignPresenter;
import com.bw.movie.presenter.TicketRecordPresenter;
import com.bw.movie.presenter.VipPresenter;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能: 我的页面
 */
public class Fragmain3 extends Fragment implements CustomAdapt {

    Unbinder unbinder;
    @BindView(R.id.mSdv_Head)
    ImageView mSdvHead;
    @BindView(R.id.mTv_Name)
    TextView mTvName;
    @BindView(R.id.mBt_QIanDao)
    Button mBtQIanDao;
    private SelectUserPresenter selectUserPresenter;
    private SignPresenter signPresenter;
    private TicketRecordPresenter ticketRecordPresenter;
    private List<User> student;
    private String sessionId;
    private UserDao userDao;
    private int userId;
    private VipPresenter vipPresenter;
    User user = new User();
    private List<User> student1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain3, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            userDao = new UserDao(getContext());
            student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        selectUserPresenter = new SelectUserPresenter(new UserCall());
        signPresenter = new SignPresenter(new SignCall());
        vipPresenter = new VipPresenter(new VipCall());

        ticketRecordPresenter = new TicketRecordPresenter(new TicketCall());
        mBtQIanDao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               vipPresenter.reqeust(userId,sessionId);



            }
        });
        return view;
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
                vipPresenter.reqeust(userId,sessionId);
            }
            Toast.makeText(getContext(), student.size() + "", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       //
        /*selectUserPresenter = new SelectUserPresenter(new UserCall());
        signPresenter = new SignPresenter(new SignCall());

        ticketRecordPresenter = new TicketRecordPresenter(new TicketCall());*/
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
                selectUserPresenter.reqeust((int) userId, sessionId);

                break;
            case R.id.mSdv_Like:
                startActivity(new Intent(getContext(), Myattention.class));
                break;
            case R.id.mSdv_Ticket:
                ticketRecordPresenter.reqeust((int) userId, sessionId);
                break;
            case R.id.mSdv_FeedBack:
                //
                break;
            case R.id.mSdv_New:
                break;
            case R.id.mSdv_LoginOut:
                //退出登录
                if (student.size()==0){
                    Toast.makeText(getContext(), "您未登录,请先登录!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(),LoginActivity.class));
                    return;
                }
                // 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //    设置Title的内容
                builder.setTitle("温馨提示");
                //    设置Content来显示一个信息
                builder.setMessage("确定退出登录吗？");
                //    设置一个PositiveButton
                builder.setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  有用户就删除
                        try {
                            userDao.deleteStudent(student.get(0));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        // 清空当前栈 ，并且创建新的栈
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        // 跳转
                        startActivity(intent);
                    }
                });
                //    设置一个NegativeButton
                builder.setNegativeButton("暂不退出", null);
                //    显示出该对话框
                builder.show();

                /*student.clear();
                startActivity(new Intent(getContext(), LoginActivity.class));*/
                break;
        }
    }


    private class UserCall implements DataCall<Result<UserMessage>> {
        @Override
        public void success(Result<UserMessage> data) {
            if (data.getStatus().equals("9999")) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else /*if (data.getStatus().equals("0000"))*/ {
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
            if (data.getStatus().equals("9999")) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else if (data.getStatus().equals("0000")){

                mBtQIanDao.setText("已签到");

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class TicketCall implements DataCall<Result<List<UserBuyList>>> {
        @Override
        public void success(Result<List<UserBuyList>> data) {
            if (data.getStatus().equals("9999")) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else if (data.getStatus().equals("0000")) {
                //startActivity(new Intent(getContext(), MyFeedBackActivity.class));
                Toast.makeText(getContext(), "购票记录", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class VipCall implements DataCall<Result<VipBean>> {
        @Override
        public void success(final Result<VipBean> data) {

            if (data.getStatus().equals("9999")) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else if (data.getStatus().equals("0000")) {

                mTvName.setText(data.getResult().getNickName());
                mSdvHead.setImageURI(Uri.parse(data.getResult().getHeadPic()));
                if (data.getResult().getUserSignStatus()==1){

                    mBtQIanDao.setText("签到");
                    signPresenter.reqeust(userId,sessionId);

                }else if (data.getResult().getUserSignStatus()==2){
                    mBtQIanDao.setText("已签到");

                    return;

                }

            }


        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /*@Override
    public void onStart() {
        super.onStart();
    }*/
}
