package com.bw.movie.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.ViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

public class GuideActivity extends AppCompatActivity implements CustomAdapt {


    @BindView(R.id.my_viewpage)
    ViewPager myViewpage;
    @BindView(R.id.my_radiogroup)
    RadioGroup myRadiogroup;
    private ArrayList<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        int image[] = {
                R.drawable.yingdaoye1,
                R.drawable.yingdaoye2,
                R.drawable.yingdaoye3,
                R.drawable.yingdaoye4
        };
        String text01[] = {
                "净化你的灵魂",
                "看遍人生百态",
                "荡漾你的心灵",
                "带您开启一段美好的电影之旅"
        };

        views = new ArrayList<>();
        for (int i = 0; i < image.length - 1; i++) {

            View inflate = View.inflate(this, R.layout.guide_01, null);
            ImageView mImageViewGuide01 = inflate.findViewById(R.id.mImageView_Guide01);
            TextView mTextView01Guide01 = inflate.findViewById(R.id.mTextView01_Guide01);
            TextView mTextView02Guide01 = inflate.findViewById(R.id.mTextView02_Guide01);
            mImageViewGuide01.setImageResource(image[i]);
            mTextView01Guide01.setText("一场电影");
            mTextView02Guide01.setText(text01[i]);
            views.add(inflate);
        }
        View inflate1 = View.inflate(this, R.layout.guide_02, null);
        ImageView mImageView_Guide02 = inflate1.findViewById(R.id.mImageView_Guide02);
        TextView mTextView01Guide02 = inflate1.findViewById(R.id.mTextView01_Guide02);
        TextView mTextView02Guide02 = inflate1.findViewById(R.id.mTextView02_Guide02);
        LinearLayout mlayout = inflate1.findViewById(R.id.mlayout);
        mImageView_Guide02.setImageResource(image[3]);
        mTextView01Guide02.setText("八维移动通信学院作品");
        mTextView02Guide02.setText(text01[3]);
        views.add(inflate1);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(views, this);
        myViewpage.setAdapter(viewPagerAdapter);

        //默认第一个选中
        myRadiogroup.check(myRadiogroup.getChildAt(0).getId());

        //联动
        myViewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                myRadiogroup.check(myRadiogroup.getChildAt(i).getId());


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    /**
     * 屏幕适配
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
