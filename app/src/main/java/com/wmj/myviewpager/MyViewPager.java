package com.wmj.myviewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by wmj on 2017-3-14.
 */

public class MyViewPager extends FrameLayout {
    private static final String TAG = "MyViewPager";
    private ViewPager mViewPager;
    private Context mContext;
    private int mCount;
    private LinearLayout linearLayout;
    private ImageView mIVGrey;

    public MyViewPager(Context context) {
        this(context,null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyViewPager(context, attrs);
    }

    private void initMyViewPager(Context context, AttributeSet attrs) {
        mContext = context;
        mCount = 4;
        initViewPager();
        initPoint();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int k = 0;
            @Override
            public void onPageScrolled(final int position, final float positionOffset, int positionOffsetPixels) {
                mViewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        FrameLayout.LayoutParams params = (LayoutParams) mIVGrey.getLayoutParams();
                        params.setMargins((int) (mIVGrey.getWidth()*position+mIVGrey.getWidth()*positionOffset),0,0,0);
                        mIVGrey.setLayoutParams(params);
                    }
                });

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initPoint() {
        final FrameLayout frameLayout = new FrameLayout(mContext);
        FrameLayout.LayoutParams p =new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        p.setMargins(0,0,0,50);
        p.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
        frameLayout.setLayoutParams(p);
        linearLayout = new LinearLayout(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.post(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i< mCount;i++){
                    ImageView iv = new ImageView(mContext);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.circle_white,null));
                    linearLayout.addView(iv);
                }
            }
        });
        addView(frameLayout);
        frameLayout.addView(linearLayout);
        mIVGrey= new ImageView(mContext);
        mIVGrey.setImageDrawable(getResources().getDrawable(R.drawable.circle_grey,null));
        frameLayout.addView(mIVGrey);

    }

    private void initViewPager() {
        mViewPager = new ViewPager(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(params);
        addView(mViewPager);
    }

    public void setAdapter(PagerAdapter adapter){
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG,"onLayout" );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: ");
    }
}
