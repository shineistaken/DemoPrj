package com.demo.shineistaken;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;


/**
 * 使用java代码实现constraintLayout
 */
public class SetConstraintByJavaCodeActivity extends AppCompatActivity {

    private TextView tv;
    private ConstraintLayout cl;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cl = new ConstraintLayout(this);
        tv = new TextView(this);
        bt = new Button(this);
        setContentView(cl);
        cl.addView(tv);
        cl.addView(bt);
//      //   NOTE :  -***-   给没有在layout中声明id的控件设置id必须先在 res/values/ids.xml 文件中配置，否则不能正常在R文件中注册使用
//   Created at : 2018/8/9  by lenovo

        /* ids.xml content
    <?xml version="1.0" encoding="utf-8"?>
        <resources>
            <item name="tv" type="id" />
        <item name="bt" type="id" />
    </resources>
        */
        tv.setId(R.id.tv);
        tv.setBackgroundColor(Color.parseColor("#234fb2"));
        tv.setText("DSFAFWEGQWEGA");
        bt.setId(R.id.bt);
        bt.setOnClickListener(v -> finish());

        ConstraintSet set = new ConstraintSet();
        // 参数意义：  
        //              connect(int startID,
        //                    int startSide,
        //                    int endID,
        //                    int endSide)  
        //  把id=startID控件的 startSide 一边 约束到 id=endID控件的 endSide 一边( PARENT_ID 除外，eg:某个控件左侧设置约束到parent，代码里也要写成
        // constraintSet.connect(xxx.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);  这与xml里一致)
        set.connect(tv.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        set.connect(tv.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        set.connect(tv.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        set.connect(tv.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        set.constrainWidth(tv.getId(), ConstraintLayout.LayoutParams.WRAP_CONTENT);
        set.constrainHeight(tv.getId(), 100);
        set.applyTo(cl);

        ConstraintSet btSets = new ConstraintSet();
        btSets.clone(set);
        btSets.constrainHeight(bt.getId(), ConstraintLayout.LayoutParams.WRAP_CONTENT);
        btSets.constrainWidth(bt.getId(), ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
        btSets.connect(bt.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        btSets.connect(bt.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        btSets.connect(bt.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        btSets.connect(bt.getId(), ConstraintSet.TOP, tv.getId(), ConstraintSet.BOTTOM, dp2px(40));
        btSets.applyTo(cl);
    }

    private int dp2px(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
