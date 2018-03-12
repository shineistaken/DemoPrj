package com.demo.my.meterial_animations_demo.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;

import com.demo.my.meterial_animations_demo.R;

public class MainActivity extends AppCompatActivity {

    private ActivityOptionsCompat fade;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TransitionActivity2.class),
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
//        setUpFadeTransition();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpFadeTransition() {
        // todo 从其他activity返回执行的是其他activity entertransition动画的逆放， 貌似只对本activity有效？
//        方法一： 代码设置
        Slide slide = new Slide();
        slide.setDuration(5000);
        slide.setSlideEdge(Gravity.TOP);
        getWindow().setReenterTransition(slide);

//        方法二： 布局文件设置
//        Transition fade = TransitionInflater.from(this).inflateTransition(R.transition.transition_activity_fade);
//        getWindow().setEnterTransition(fade);
    }
}
