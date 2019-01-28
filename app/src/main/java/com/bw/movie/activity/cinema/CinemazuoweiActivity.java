package com.bw.movie.activity.cinema;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.BuyPresenter;
import com.bw.movie.presenter.PayPresenter;
import com.bw.movie.util.MD5Utils;
import com.qfdqc.views.seattable.SeatTable;
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

/**
 * 影院选座页面
 */
public class CinemazuoweiActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.txt_session)
    TextView txtSession;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.seat_view)
    SeatTable seatTableView;
    @BindView(R.id.txt_jiesuan)
    TextView txtJiesuan;
    @BindView(R.id.txt_fuhao)
    TextView txtFuhao;
    @BindView(R.id.txt_choose_price)
    TextView txtChoosePrice;
    @BindView(R.id.img_confirm)
    ImageView imgConfirm;
    @BindView(R.id.img_abandon)
    ImageView imgAbandon;
    @BindView(R.id.rl_bot)
    RelativeLayout rlBot;
    double zongprice=0;
    int mount=0;

    private PopupWindow popupWindow;
    private ImageView pay_down;
    private double price;
    private IWXAPI api;
    private String sessionId;
    private int id;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinemazuowei);
        ButterKnife.bind(this);

        try {
            UserDao userDao = new UserDao(this);
            List<User> student = userDao.getStudent();
            if (student.size()>0){
                userId = student.get(0).getUserId();
                sessionId = student.get(0).getSessionId();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        seatTableView = (SeatTable) findViewById(R.id.seat_view);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id = extras.getInt("id");
        price = extras.getDouble("price");
        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");//第二个参数为APPID
        api.registerApp("wxb3852e6a6b7d9516");
        Log.e("TAG", "onCreate: "+ this.price);
        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                zongprice+= CinemazuoweiActivity.this.price;
                mount++;
                DecimalFormat df2 = new DecimalFormat("#0.00");
                String format = df2.format(zongprice);
                txtChoosePrice.setText(format+"");
            }

            @Override
            public void unCheck(int row, int column) {
                zongprice-= CinemazuoweiActivity.this.price;
                mount--;
                DecimalFormat df2 = new DecimalFormat("#0.00");
                String format = df2.format(zongprice);
                txtChoosePrice.setText(format+"");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            UserDao userDao = new UserDao(this);
            List<User> student = userDao.getStudent();
            if (student.size()>0){
                userId = student.get(0).getUserId();
                sessionId = student.get(0).getSessionId();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.img_confirm, R.id.img_abandon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_confirm:
                if (mount==0){
                    Toast.makeText(CinemazuoweiActivity.this, "请选择座位", Toast.LENGTH_SHORT).show();
                    return;
                }else if (mount >3){
                    Toast.makeText(CinemazuoweiActivity.this, "最多3个", Toast.LENGTH_SHORT).show();
                    return;
                }
                int height = getWindowManager().getDefaultDisplay().getHeight();
                View inflate = LayoutInflater.from(this).inflate(R.layout.pay_item, null);
                pay_down = inflate.findViewById(R.id.pay_down);
                Button mBt_Pay = inflate.findViewById(R.id.mBt_Pay);
                pay_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                DecimalFormat df2 = new DecimalFormat("#0.00");
                final String format = df2.format(zongprice);
                mBt_Pay.setText("微信支付"+format+"元");
                mBt_Pay.setOnClickListener(new View.OnClickListener() {
                    class BuyCall implements DataCall<Result> {
                        @Override
                        public void success(Result data) {

                            if (data.getStatus().equals("0000")){
                                Toast.makeText(CinemazuoweiActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                                PayPresenter payPresenter = new PayPresenter(new PayCall());
                                payPresenter.reqeust(userId,sessionId,1,data.getOrderId());
                            }else if (data.getStatus().equals("9999")){
                                startActivity(new Intent(CinemazuoweiActivity.this, LoginActivity.class));
                            }

                        }

                        @Override
                        public void fail(ApiException e) {

                        }
                    }

                    @Override
                    public void onClick(View v) {
                        BuyPresenter buyMovieTicketPresenter = new BuyPresenter(new  BuyCall());

                        String md5=userId+""+id+""+mount+"movie";
                        Log.e("TAG", "onClick: "+md5 );
                        String s = MD5Utils.md5Password(md5);
                        buyMovieTicketPresenter.reqeust(userId,sessionId,id,mount,s);
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

                break;
            case R.id.img_abandon:
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

    private class PayCall implements DataCall<Result> {
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
}

