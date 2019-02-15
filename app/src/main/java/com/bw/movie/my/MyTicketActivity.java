package com.bw.movie.my;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.cinema.CinemazuoweiActivity;
import com.bw.movie.adapter.MyAdapter;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.TicketBean;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.BuyPresenter;
import com.bw.movie.presenter.PayPresenter;
import com.bw.movie.presenter.TicketPresenter;
import com.bw.movie.util.MD5Utils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.sql.SQLException;
import java.text.DecimalFormat;
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
    private int id = 1;
    private String sessionId;
    private int userId;
    private IWXAPI api;
    private PopupWindow popupWindow;
    private ImageView pay_down;

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
        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");//第二个参数为APPID
        api.registerApp("wxb3852e6a6b7d9516");
        try {
            UserDao userDao = new UserDao(this);
            List<User> student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
                Log.e("wj", "MovieMessageActivity======" + sessionId);
                Log.e("wj", "MovieMessageActivity======" + userId);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        ticketPresenter.reqeust(userId, sessionId, 1, 5, id);
        // ticketRecy.addItemDecoration(new SpaceItemDecoration(20));
        myAdapter.setBuyClick(new MyAdapter.BuyClick() {
            @Override
            public void buyClicks(double price, final int sum) {
                Log.e("GGG", "buyClicks: " + price + "" + sum);
                int height = getWindowManager().getDefaultDisplay().getHeight();
                View inflate = LayoutInflater.from(MyTicketActivity.this).inflate(R.layout.pay_item, null);
                pay_down = inflate.findViewById(R.id.pay_down);
                Button mBt_Pay = inflate.findViewById(R.id.mBt_Pay);
                pay_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                DecimalFormat df2 = new DecimalFormat("#0.00");
                final String format = df2.format(price * sum);
                mBt_Pay.setText("微信支付" + format + "元");
                mBt_Pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BuyPresenter buyMovieTicketPresenter = new BuyPresenter(new BuysCall());
                        String md5 = userId + "" + id + "" + sum + "movie";
                        Log.e("TAG", "onClick: " + md5);
                        String s = MD5Utils.md5Password(md5);
                        buyMovieTicketPresenter.reqeust(userId, sessionId, id, sum, s);
                    }
                });

                popupWindow = new PopupWindow(inflate, RelativeLayout.LayoutParams.MATCH_PARENT, height / 100 * 30);
                //设置背景,这个没什么效果，不添加会报错
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                //设置点击弹窗外隐藏自身
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                //设置位置
                popupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
                //设置PopupWindow的View点击事件
            }
        });
    }

    @OnClick({R.id.ticket_wait_money, R.id.ticket_finish, R.id.ticket_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ticket_wait_money:
                id = 1;
                myAdapter.clearIteam();
                ticketWaitMoney.setBackgroundResource(R.drawable.myattention_bg);
                ticketWaitMoney.setTextColor(Color.WHITE);
                ticketFinish.setBackgroundResource(R.drawable.myattention_bg2);
                ticketFinish.setTextColor(Color.BLACK);
                ticketPresenter.reqeust(userId, sessionId, 1, 5, id);

                myAdapter.notifyDataSetChanged();
                break;
            case R.id.ticket_finish:
                id = 2;
                myAdapter.clearIteam();
                ticketPresenter.reqeust(userId, sessionId, 1, 5, id);
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
            if (result.getStatus().equals("0000")) {
                Log.d("qqq", "success: " + result.toString());
                Toast.makeText(MyTicketActivity.this, result.getMessage() + "成功", Toast.LENGTH_SHORT).show();
                List<TicketBean> result1 = result.getResult();
                myAdapter.addList(result1);
                myAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(MyTicketActivity.this, e.getMessage() + "失败", Toast.LENGTH_SHORT).show();
        }
    }

    private class PaysCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            PayReq req = new PayReq();
            req.appId = data.getAppId();
            req.partnerId = data.getPartnerId();
            req.prepayId = data.getPrepayId();
            req.nonceStr = data.getNonceStr();
            req.timeStamp = data.getTimeStamp();
            req.packageValue = data.getPackageValue();
            req.sign = data.getSign();
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            //3.调用微信支付sdk支付方法
            api.sendReq(req);
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class BuysCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(MyTicketActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                PayPresenter payPresenter = new PayPresenter(new PaysCall());
                payPresenter.reqeust(userId, sessionId, 1, data.getOrderId());
            } else if (data.getStatus().equals("9999")) {
                startActivity(new Intent(MyTicketActivity.this, LoginActivity.class));
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
