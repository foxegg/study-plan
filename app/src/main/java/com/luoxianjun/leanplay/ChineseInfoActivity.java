package com.luoxianjun.leanplay;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.luoxianjun.leanplay.databinding.ActivityChineseInfoBinding;

import org.liufree.xmindparser.pojo.Attached;

public class ChineseInfoActivity extends AppCompatActivity {
    private TextView title;
    private TextView content;
    private Attached attached;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese_info);
        attached = MainActivity.attached.get(MainActivity.position);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        title.setText(attached.getTitle());
        content.setText(attached.getChildren().getAttached().get(0).getTitle());
    }

}