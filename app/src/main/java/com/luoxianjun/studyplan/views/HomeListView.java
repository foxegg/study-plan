package com.luoxianjun.studyplan.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.luoxianjun.studyplan.R;

import java.util.List;

public class HomeListView extends ListView {
    private List<String> nameLists;
    private LayoutInflater lf;
    private MyAdapter myAdapter = new MyAdapter();

    public HomeListView(Context context) {
        super(context);
        init(context);
    }

    public HomeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        lf = LayoutInflater.from(context);
        setAdapter(myAdapter);
    }

    public void refreshData(List<String> nameLists) {
        this.nameLists = nameLists;
        myAdapter.notifyDataSetChanged();
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return nameLists!=null?nameLists.size():0;
        }

        @Override
        public String getItem(int position) {
            return nameLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = lf.inflate(R.layout.home_list_item, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.image = convertView.findViewById(R.id.image);
                viewHolder.name = convertView.findViewById(R.id.name);
                convertView.setTag(viewHolder);
            }
            String szcp = getItem(position);
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.name.setText(szcp);
            return convertView;
        }

        class ViewHolder {
            ImageView image;
            TextView name;
            TextView limit_count;
        }
    }
}
