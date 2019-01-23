package com.example.movie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.movie.R;
import com.example.movie.frag.Fragmain1;
import com.example.movie.frag.Fragmain2;
import com.example.movie.frag.Fragmain3;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能: 首页 Fragment 切换页面
 */
public class FragActivity extends AppCompatActivity {

    @BindView(R.id.radiobutton1)
    RadioButton radiobutton1;
    @BindView(R.id.radiobutton2)
    RadioButton radiobutton2;
    @BindView(R.id.radiobutton3)
    RadioButton radiobutton3;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);
        ButterKnife.bind(this);

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        final Fragmain1 fragmain1 = new Fragmain1();
        final Fragmain2 fragmain2 = new Fragmain2();
        final Fragmain3 fragmain3 = new Fragmain3();
        transaction.add(R.id.fragmain, fragmain1).show(fragmain1);
        transaction.add(R.id.fragmain, fragmain2).hide(fragmain2);
        transaction.add(R.id.fragmain, fragmain3).hide(fragmain3);
        transaction.commit();

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.radiobutton1:
                        transaction1.show(fragmain1).hide(fragmain2).hide(fragmain3);
                        break;
                    case R.id.radiobutton2:
                        transaction1.show(fragmain2).hide(fragmain1).hide(fragmain3);
                        break;
                    case R.id.radiobutton3:
                        transaction1.show(fragmain3).hide(fragmain2).hide(fragmain1);
                        break;
                }
                transaction1.commit();
            }
        });
    }
}
