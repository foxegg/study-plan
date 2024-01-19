package com.luoxianjun.leanplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.liufree.xmindparser.XmindParser;
import org.liufree.xmindparser.ZipUtils;
import org.liufree.xmindparser.pojo.Attached;
import org.liufree.xmindparser.pojo.Children;
import org.liufree.xmindparser.pojo.JsonRootBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private List<String> assetNameLists = new ArrayList<String>();
    /**
     * 当前选中内容
     */
    public static int position;
    public static List<Attached> attached;
    //当前深度
    private int deep = 0;
    private Map<Integer,List<Attached>> listAttached = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_title);
        listView = findViewById(R.id.main_list);
        ZipUtils.currentPath = getFilesDir().getAbsolutePath();
        initAssetNameLists();
        initListView(0);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(deep<2){
            deep++;
            initListView(position);
        }else {
            MainActivity.position = position;
            startActivity(new Intent(this, ChineseInfoActivity.class));
        }
    }

    private void initListView(int position){
        Log.i("luolaigang",deep+"");
        if(deep == 0){
            setListValue(assetNameLists);
        }else if (deep == 1) {
            JsonRootBean jsonRootBean = getAssetInfo(assetNameLists.get(position));
            List<Attached> attached = jsonRootBean.getRootTopic().getChildren().getAttached();
            listAttached.put(deep, attached);
            setListValue(getListTitles(attached));
        } else if (deep == 2) {
            MainActivity.position = position;
            List<Attached> attached = listAttached.get(deep-1).get(position).getChildren().getAttached();
            listAttached.put(deep, attached);
            setListValue(getListTitles(attached));
        }
    }

    private void setListValue(List<String> nameLists) {
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameLists));
        listView.setOnItemClickListener(this);
    }

    private List<String> getListTitles(List<Attached> attached) {
        List<String> nameLists = new ArrayList<>();
        MainActivity.attached = attached;
        for (Attached attachedOne : attached) {
            nameLists.add(attachedOne.getTitle());
        }
        return nameLists;
    }

    @Override
    public void onBackPressed() {
        if (deep == 0) {
            super.onBackPressed();
        } else {
            deep--;
            if(deep>0){
                List<Attached> attached = listAttached.get(deep);
                setListValue(getListTitles(attached));
            }else{
                initListView(0);
            }

        }
    }

    private void initAssetNameLists() {
        assetNameLists.clear();
        try {
            AssetManager assetManager = getAssets();
            String[] allFiles = assetManager.list("");
            for (String filename : allFiles) {
                if (filename.endsWith(".xmind")) {
                    Log.i("luolaigang", filename);
                    assetNameLists.add(filename);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonRootBean getAssetInfo(String filename) {
        InputStream inputStream = null;
        try {
            // 获取AssetManager实例
            AssetManager assetManager = getAssets();
            if (filename.endsWith(".xmind")) {
                inputStream = assetManager.open(filename);
                return XmindParser.readFile(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}