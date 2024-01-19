package com.luoxianjun.studyplan.views.usergrants;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class GrantImageView extends androidx.appcompat.widget.AppCompatImageView {

    public GrantImageView(Context context) {
        super(context);
    }

    public GrantImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GrantImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ViewGroup.LayoutParams vp = getLayoutParams();
        vp.height = getMeasuredWidth();
        setLayoutParams(vp);
    }
}
