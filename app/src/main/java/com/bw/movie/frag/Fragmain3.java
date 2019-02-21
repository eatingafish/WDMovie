package com.bw.movie.frag;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
import com.bw.movie.core.DownLoadService;
import com.bw.movie.exception.ApiException;
import com.bw.movie.my.MyAlllistActivity;
import com.bw.movie.my.MyFeedBackActivity;
import com.bw.movie.my.MyMessageActivity;
import com.bw.movie.my.MyTicketActivity;
import com.bw.movie.my.Myattention;
import com.bw.movie.presenter.MyVersionPresenter;
import com.bw.movie.presenter.SelectUserPresenter;
import com.bw.movie.presenter.SignPresenter;
import com.bw.movie.presenter.TicketRecordPresenter;
import com.bw.movie.presenter.VipPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

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
    SimpleDraweeView mSdvHead;
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
    private MyVersionPresenter myVersionPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmain3, container, false);
        unbinder = ButterKnife.bind(this, view);
        vipPresenter = new VipPresenter(new VipCall());
        vipPresenter.reqeust(userId, sessionId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
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

        ticketRecordPresenter = new TicketRecordPresenter(new TicketCall());
        mBtQIanDao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signPresenter.reqeust(userId, sessionId);


            }
        });
        mTvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student.size() == 0) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    // 跳转
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            userDao = new UserDao(getContext());
            student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
                vipPresenter.reqeust(userId, sessionId);

            } else if (student.size() == 0) {
                mTvName.setText("登录/注册");
                mSdvHead.setImageResource(R.drawable.myhead);
            }
            //Toast.makeText(getContext(), student.size() + "", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        vipPresenter.reqeust(userId, sessionId);
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

    @OnClick({R.id.mSdv_Sound, R.id.mSdv_Head, R.id.mymessages, R.id.mylike, R.id.myticket, R.id.myfeedback, R.id.mynew, R.id.myloginout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mSdv_Sound:
                if (student.size() == 0) {
                    Toast.makeText(getContext(), "您未登录,请先登录!", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getContext(), MyAlllistActivity.class));
                }
                break;
            case R.id.mSdv_Head:

                break;
            case R.id.mSdv_Message:

            case R.id.mymessages:
                if (student.size() == 1) {
                    selectUserPresenter.reqeust((int) userId, sessionId);
                } else {
                    Toast.makeText(getContext(), "您未登录,请先登录!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.mylike:
                if (student.size() == 1) {
                    startActivity(new Intent(getContext(), Myattention.class));
                } else {
                    Toast.makeText(getContext(), "您未登录,请先登录!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.myticket:
                if (student.size() == 1) {
                    startActivity(new Intent(getContext(), MyTicketActivity.class));
                } else {
                    Toast.makeText(getContext(), "您未登录,请先登录!", Toast.LENGTH_SHORT).show();
                }
                // ticketRecordPresenter.reqeust((int) userId, sessionId);
                break;
            case R.id.myfeedback:
                startActivity(new Intent(getContext(), MyFeedBackActivity.class));
                break;
            case R.id.mynew:
                // 版本
                if (student.size() == 0) {
                    Toast.makeText(getContext(), "您未登录,请先登录!", Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        String versionName = getVersionName(getContext());
                        myVersionPresenter = new MyVersionPresenter(new getverson());
                        myVersionPresenter.reqeust(userId, sessionId, versionName);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                 break;
            case R.id.myloginout:
                //退出登录
                if (student.size() == 0) {
                    Toast.makeText(getContext(), "您未登录,请先登录!", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getContext(),LoginActivity.class));
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
                            List<User> student = userDao.getStudent();
                            userDao.deleteStudent(student.get(0));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        student.clear();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        // 清空当前栈 ，并且创建新的栈
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        // 跳转
                        // startActivity(intent);
                        mTvName.setText("登录/注册");
                        mSdvHead.setImageResource(R.drawable.myhead);

                    }
                });
                builder.setCancelable(false);
                //    设置一个NegativeButton
                builder.setNegativeButton("暂不退出", null);
                //    显示出该对话框
                builder.show();

                //
                //startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
    }


    /**
     * 获取版本号
     *
     * @throws PackageManager.NameNotFoundException
     */
    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    private class UserCall implements DataCall<Result<UserMessage>> {
        @Override
        public void success(Result<UserMessage> data) {
            if (data.getStatus().equals("9999")) {
                //startActivity(new Intent(getContext(), LoginActivity.class));
                Toast.makeText(getContext(), "您未登录,请先登录!", Toast.LENGTH_SHORT).show();
            } else if (data.getStatus().equals("0000")) {
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
                //startActivity(new Intent(getContext(), LoginActivity.class));
                Toast.makeText(getContext(), "您未登录,请先登录!", Toast.LENGTH_SHORT).show();
            } else if (data.getStatus().equals("0000")) {

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
                // startActivity(new Intent(getContext(), LoginActivity.class));
                Toast.makeText(getContext(), "您未登录,请先登录!", Toast.LENGTH_SHORT).show();
            } else if (data.getStatus().equals("0000")) {
                //Toast.makeText(getContext(), "购票记录", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class VipCall implements DataCall<Result<VipBean>> {
        @Override
        public void success(final Result<VipBean> data) {

            if (data.getStatus().equals("0000")) {

                mTvName.setText(data.getResult().getNickName());
                mSdvHead.setImageURI(data.getResult().getHeadPic());
                //Toast.makeText(getContext(), "设置头像", Toast.LENGTH_SHORT).show();
                if (data.getResult().getUserSignStatus() == 1) {

                    mBtQIanDao.setText("签到");


                } else if (data.getResult().getUserSignStatus() == 2) {
                    mBtQIanDao.setText("已签到");

                    return;

                }
                return;
            }
            mTvName.setText("登录/注册");
            mSdvHead.setImageResource(R.drawable.myhead);

            //  Toast.makeText(getContext(), "您未登录,请先登录!", Toast.LENGTH_SHORT).show();


        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //最新版本
    private class getverson implements DataCall<Result> {
        @Override
        public void success(final Result data) {
            if (data.getFlag() == 2) {
                Toast.makeText(getActivity(), "当前已是最新版本!", Toast.LENGTH_SHORT).show();
            } else if (data.getFlag() == 1) {
                AlertDialog.Builder builer = new AlertDialog.Builder(getContext());
                builer.setTitle("版本升级");
                builer.setMessage("发现新版本");
                //当点确定按钮时从服务器上下载 新的apk 然后安装
                builer.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), DownLoadService.class);
                        intent.putExtra("download_url", data.getDownloadUrl());
                        getActivity().startService(intent);
                    }
                });
                //当点取消按钮时进行登录
                builer.setNegativeButton("稍后下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //LoginMain();
                    }
                });
                AlertDialog dialog = builer.create();
                dialog.show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }

    }

    }

    /*@Override
    public void onStart() {
        super.onStart();
    }*/
