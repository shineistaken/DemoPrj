package com.demo.my.meterial_animations_demo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;

import com.demo.my.meterial_animations_demo.R;

/**
 * Created by lenovo on 2018/3/8.
 */

public class TransitionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        setUpEnterTrainsition();
        setUpExitTrainsition();
    }

    private void setUpExitTrainsition() {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            Slide slide = new Slide();
//            slide.setDuration(1000);
//            slide.setSlideEdge(Gravity.BOTTOM);
//            getWindow().setExitTransition(slide);
//        }
    }

    //todo 设置两个transition貌似只有enter起作用？ 退出时播放的是enter动画逆放
    private void setUpEnterTrainsition() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(5000);
            getWindow().setEnterTransition(fade);
//            Slide slide = new Slide();
//            slide.setDuration(1000);
//            slide.setSlideEdge(Gravity.BOTTOM);
//            getWindow().setReenterTransition(slide);
        }
    }
}

