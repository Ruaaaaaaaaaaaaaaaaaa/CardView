package com.wmj.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by wmj on 2017-3-18.
 */

public class MyFrameLayout extends FrameLayout {
    public MyFrameLayout(Context context) {
        this(context,null);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private AddContentListener listener;
    void setAddContentListener(AddContentListener listener){
        this.listener = listener;
    }

    public AddContentListener getListener() {
        return listener;
    }

    public interface AddContentListener{
        void addContent();
    }
}
