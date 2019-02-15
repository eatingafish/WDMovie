package com.bw.movie.frag;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.bean.DetailsData;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.DetailsPresenter;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 影院详情
 */
public class CinemaDetailsXqFragment extends Fragment {

    @BindView(R.id.txt_dw)
    TextView txtDw;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_lx)
    TextView txtLx;
    @BindView(R.id.txt_dt)
    TextView txtDt;
    @BindView(R.id.txt_gj)
    TextView txtGj;
    @BindView(R.id.txt_zj)
    TextView txtZj;
    Unbinder unbinder;
    private int userId;
    private String sessionId;
    private int cinemaId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema_details_xq, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        Intent intent = getActivity().getIntent();
        cinemaId = intent.getIntExtra("yingid", 1);
        return view;
    }


    private void initData() {
        try {
            UserDao userDao = new UserDao(getContext());
            List<User> student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        DetailsPresenter detailsPresenter = new DetailsPresenter(new DetailsCall());
        detailsPresenter.reqeust(userId, sessionId, 18);
    }

    class DetailsCall implements DataCall<Result<DetailsData>> {

        private DetailsData result;

        @Override
        public void success(Result<DetailsData> data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                result = data.getResult();
                txtDw.setText(result.getAddress());
                txtDt.setText(result.getAddress());
                txtPhone.setText(result.getPhone());
                txtLx.setText(result.getVehicleRoute());
                txtDt.setText(result.getVehicleRoute());
                txtGj.setText(result.getAddress());
                txtZj.setText(result.getVehicleRoute());
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
        }


    }

    @OnClick(R.id.txt_phone)
    public void onClick() {
        Intent intentPhone = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + txtPhone);
        intentPhone.setData(data);
        startActivity(intentPhone);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
