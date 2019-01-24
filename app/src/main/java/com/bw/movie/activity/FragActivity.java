package com.bw.movie.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.frag.Fragmain1;
import com.bw.movie.frag.Fragmain2;
import com.bw.movie.frag.Fragmain3;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 功能: 首页 Fragment 切换页面
 */
public class FragActivity extends AppCompatActivity implements CustomAdapt, View.OnClickListener {


    @BindView(R.id.fragmain)
    FrameLayout fragmain;
    @BindView(R.id.homeactivity_imagefilm)
    ImageView homeactivityImagefilm;
    @BindView(R.id.homeactivity_imagecinema)
    ImageView homeactivityImagecinema;
    @BindView(R.id.homeactivity_imagemy)
    ImageView homeactivityImagemy;
    private Fragmain1 fragmain1;
    private Fragmain2 fragmain2;


    private Fragmain3 fragmain3;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);
        ButterKnife.bind(this);
        //哈哈
        initView();
        initEvent();
        //刚刚
        //aa
    }

    private void initEvent() {
        transaction = getSupportFragmentManager().beginTransaction();
        fragmain1 = new Fragmain1();
        fragmain2 = new Fragmain2();
        fragmain3 = new Fragmain3();

        transaction.add(R.id.fragmain, fragmain1);
        transaction.add(R.id.fragmain, fragmain2);
        transaction.add(R.id.fragmain, fragmain3);

        transaction.show(fragmain1);
        transaction.hide(fragmain2);
        transaction.hide(fragmain3);

        transaction.commit();
    }


    /**
     * @author dingtao
     * @date 2019/1/24 11:31 AM
     * 初始化布局
     */
    private void initView() {

        homeactivityImagefilm.setOnClickListener(this);
        homeactivityImagecinema.setOnClickListener(this);
        homeactivityImagemy.setOnClickListener(this);
//        homeactivityImagemy.setOnClickListener(this);

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleX", 1.17f);
        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleY", 1.17f);
        //存入集合
        set.playTogether(o1, o4);
        //开始执行
        set.start();
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        switch (v.getId()) {
            case R.id.homeactivity_imagefilm:
                //切换电影页
                isimagefilm(transaction);
                break;
            case R.id.homeactivity_imagecinema:
                //切换影院页
                isimagecinema(transaction);
                break;
            case R.id.homeactivity_imagemy:
                //切换我的页面
                isimagemy(transaction);
                break;
        }
        transaction.commit();
    }

    //切换电影页
    private void isimagefilm(FragmentTransaction transaction) {
        //显示和隐藏
        transaction.hide(fragmain3);
        transaction.hide(fragmain2);
        transaction.show(fragmain1);
        //切换图片
        homeactivityImagefilm.setImageResource(R.drawable.yingpian2);
        homeactivityImagecinema.setImageResource(R.drawable.yingyuan1);
        homeactivityImagemy.setImageResource(R.drawable.my1);
        //属性动画改变图片大小
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleX", 1.17f);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleX", 1.0f);
        ObjectAnimator o3 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleX", 1.0f);

        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleY", 1.17f);
        ObjectAnimator o5 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleY", 1.0f);
        ObjectAnimator o6 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleY", 1.0f);

        //存入集合
        set.playTogether(o1, o2, o3, o4, o5, o6);
        //开始执行
        set.start();
    }

    //切换影院页
    public void isimagecinema(FragmentTransaction transaction) {
        //显示和隐藏
        transaction.show(fragmain2);
        transaction.hide(fragmain1);
        transaction.hide(fragmain3);
        //切换图片
        homeactivityImagefilm.setImageResource(R.drawable.yingpian1);
        homeactivityImagecinema.setImageResource(R.drawable.yingyuan2);
        homeactivityImagemy.setImageResource(R.drawable.my1);

        //属性动画改变图片大小
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleX", 1.0f);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleX", 1.17f);
        ObjectAnimator o3 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleX", 1.0f);

        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleY", 1.0f);
        ObjectAnimator o5 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleY", 1.17f);
        ObjectAnimator o6 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleY", 1.0f);

        //存入集合
        set.playTogether(o1, o2, o3, o4, o5, o6);
        //开始执行
        set.start();

    }

    //切换我的页面
    private void isimagemy(FragmentTransaction transaction) {
        //显示和隐藏
        transaction.hide(fragmain1);
        transaction.show(fragmain3);
        transaction.hide(fragmain2);
        //切换图片
        homeactivityImagefilm.setImageResource(R.drawable.yingpian1);
        homeactivityImagecinema.setImageResource(R.drawable.yingyuan1);
        homeactivityImagemy.setImageResource(R.drawable.my2);

        //属性动画改变图片大小
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleX", 1.0f);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleX", 1.0f);
        ObjectAnimator o3 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleX", 1.17f);

        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleY", 1.0f);
        ObjectAnimator o5 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleY", 1.0f);
        ObjectAnimator o6 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleY", 1.17f);

        //存入集合
        set.playTogether(o1, o2, o3, o4, o5, o6);
        //开始执行
        set.start();

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
