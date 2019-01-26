package com.bw.movie.activity.cinema;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.qfdqc.views.seattable.SeatTable;

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

    private PopupWindow popupWindow;
    private ImageView pay_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinemazuowei);
        ButterKnife.bind(this);
        seatTableView = (SeatTable) findViewById(R.id.seat_view);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

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

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);
    }

    @OnClick({R.id.img_confirm, R.id.img_abandon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_confirm:
                int height = getWindowManager().getDefaultDisplay().getHeight();
                View inflate = LayoutInflater.from(this).inflate(R.layout.pay_item, null);
                pay_down = inflate.findViewById(R.id.pay_down);
                pay_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
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

    }

