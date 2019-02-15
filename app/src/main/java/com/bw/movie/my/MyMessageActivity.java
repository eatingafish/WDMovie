package com.bw.movie.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.Dao.UserDao;
import com.bw.movie.R;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.bean.UserInfo;
import com.bw.movie.bean.UserMessage;
import com.bw.movie.core.DataCall;
import com.bw.movie.exception.ApiException;
import com.bw.movie.presenter.SelectUserPresenter;
import com.bw.movie.presenter.UpHeadPresenter;
import com.bw.movie.presenter.UpdatePresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能:个人信息
 */
public class MyMessageActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.myMessage_head)
    SimpleDraweeView myMessageHead;
    @BindView(R.id.my_back)
    ImageView myBack;
    @BindView(R.id.my_pwd_update)
    ImageView myPwdUpdate;
    @BindView(R.id.mTv_UserName)
    TextView mTvUserName;
    @BindView(R.id.mTv_UserSex)
    TextView mTvUserSex;
    @BindView(R.id.mTv_UserBirthday)
    TextView mTvUserBirthday;
    @BindView(R.id.mTv_UserPhone)
    TextView mTvUserPhone;
    @BindView(R.id.mTv_UserMail)
    TextView mTvUserMail;
    @BindView(R.id.xiugai)
    TextView xiugai;
    private List<User> student;
    private String sessionId;
    private int userId;
    int sex = 0;
    private String box;
    private String name;
    private UpdatePresenter updatePresenter;
    List<Object> objects = new ArrayList<>();
    private List<UserInfo> userBeans = new ArrayList<>();
    private UserDao userDao;
    private SelectUserPresenter selectUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);

        try {
            userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        selectUserPresenter = new SelectUserPresenter(new UserCall());
        selectUserPresenter.reqeust((int) userId, sessionId);
        updatePresenter = new UpdatePresenter(new Updatecall());
        myMessageHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showChoosePhotoDialog();

                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                startActivityForResult(intent1, 0);
            }
        });
        initView();
    }

    private void initView() {

        UserInfo userInfo = student.get(0).getUserInfo();
        //设置值
        // sdvMessageheadpic.setImageURI(userInfo.getHeadPic());
        mTvUserName.setText(userInfo.getNickName());
        int sex = userInfo.getSex();
        if (sex == 1) {
            mTvUserSex.setText("男");
        } else {
            mTvUserSex.setText("女");
        }
        mTvUserBirthday.setText(userInfo.getLastLoginTime() + "");
        mTvUserPhone.setText(userInfo.getPhone());
//        String wdpwd = userInfo.getWdpwd();
//        decrypt = EncryptUtil.decrypt(wdpwd);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            userDao = new UserDao(this);
            student = userDao.getStudent();
            if (student.size() != 0) {
                sessionId = student.get(0).getSessionId();
                userId = student.get(0).getUserId();
            }
           // selectUserPresenter.reqeust((int) userId, sessionId);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.my_back, R.id.my_pwd_update, R.id.xiugai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_back:
                finish();
                break;
            case R.id.my_pwd_update:
                startActivity(new Intent(this, MypwdupActivity.class));
                break;
            case R.id.xiugai:
                xiugai();
                break;
        }
    }


    private void xiugai() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view1 = View.inflate(this, R.layout.activity_mine_dialog, null);
        builder.setTitle("修改信息");
        builder.setView(view1);
        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText newName = view1.findViewById(R.id.newname);
                EditText newBox = view1.findViewById(R.id.newbox);

                RadioButton man = view1.findViewById(R.id.man);
                RadioButton woman = view1.findViewById(R.id.woman);

                if (man.isChecked()) {
                    sex = 1;
                }
                if (woman.isChecked()) {
                    sex = 2;
                }

                name = newName.getText().toString().trim();
                box = newBox.getText().toString().trim();

                Log.e("asd", "id" + userId);
                Log.e("asd", "session" + sessionId);
                Log.e("asd", "name" + name);
                Log.e("asd", "sex" + sex);
                Log.e("asd", "email" + box);

                updatePresenter.reqeust(userId, sessionId, name, sex, box);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private class UserCall implements DataCall<Result<UserMessage>> {
        @Override
        public void success(Result<UserMessage> data) {

            if (data.getStatus().equals("0000")) {

                mTvUserName.setText(data.getResult().getNickName());
                myMessageHead.setImageURI(data.getResult().getHeadPic());
                // mTvUserMail.setText(data.getResult().get);
                //mTvUserBirthday.setText(data.getResult().getBirthday());
                mTvUserPhone.setText(data.getResult().getPhone());
                mTvUserSex.setText(data.getResult().getSex());

            }else{
                Toast.makeText(MyMessageActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {
            //Toast.makeText(MyMessageActivity.this, ""+e.getCode(), Toast.LENGTH_SHORT).show();

        }
    }

    private class Updatecall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                mTvUserName.setText(name);
                if (sex==1){
                    mTvUserSex.setText("男");
                }else {
                    mTvUserSex.setText("女");
                }
                mTvUserMail.setText(box);
                Toast.makeText(MyMessageActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(MyMessageActivity.this, "修改失败"+e.getCode()+e.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 得到图片的路径
     *
     * @param fileName
     * @param requestCode
     * @param data
     * @return
     */
    public String getFilePath(String fileName, int requestCode, Intent data) {
        if (requestCode == 1) {
            return fileName;
        } else if (requestCode == 0) {
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor
                    .getString(actual_image_column_index);
            // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                actualimagecursor.close();
            return img_path;
        }
        return null;
    }

    private class UpHeadC implements DataCall<Result> {
        @Override
        public void success(Result data) {
            selectUserPresenter.reqeust((int) userId, sessionId);
            //Toast.makeText(MyMessageActivity.this, "111" + data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            String filePath = getFilePath(null, requestCode, data);
            objects.add(filePath);
            Uri data1 = data.getData();
            myMessageHead.setImageURI(data1);
            //userBeans.get(0).setHeadPic(data1.toString());
            UpHeadPresenter upHeadPresenter = new UpHeadPresenter(new UpHeadC());
            //Toast.makeText(this, "哈哈哈", Toast.LENGTH_SHORT).show();
            upHeadPresenter.reqeust(userId, sessionId, objects);
            objects.clear();
        }

    }

    /**
     * 屏幕适配
     *
     * @return
     */
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }


}
