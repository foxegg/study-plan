package com.luoxianjun.studyplan.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.luoxianjun.studyplan.R;

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
        content = findViewById(R.id.text_content);
        title.setText(attached.getTitle());
        content.setText(attached.getChildren().getAttached().get(0).getTitle());
    }

}