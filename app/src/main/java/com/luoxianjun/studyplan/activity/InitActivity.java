package com.luoxianjun.studyplan.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.luoxianjun.studyplan.R;
import com.luoxianjun.studyplan.utils.ViewHelp;


public abstract class InitActivity extends AppCompatActivity {
    public boolean lightBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        try {
            ViewHelp.setFullscreen(this, lightBar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public void setLightBar(boolean lightBar) {
        this.lightBar = lightBar;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            ((TextView) findViewById(R.id.title)).setText(getTitle());
            LinearLayout fl = findViewById(R.id.content);
            FrameLayout.LayoutParams fll = (FrameLayout.LayoutParams) fl.getLayoutParams();
            fll.topMargin = ViewHelp.getStatusBarHeight(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}