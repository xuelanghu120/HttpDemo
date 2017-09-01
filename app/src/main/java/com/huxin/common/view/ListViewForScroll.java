package com.huxin.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * scrollview中嵌套listview,防止只出现一个item
 */
public class ListViewForScroll extends ListView {
    public ListViewForScroll(Context context) {
        super(context);
    }

    public ListViewForScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
