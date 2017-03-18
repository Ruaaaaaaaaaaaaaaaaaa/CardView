package com.wmj.myviewpager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by wmj on 2017-3-17.
 */

public class MyCardView extends CardView {
    private static final String TAG = "MyCardView";
    private Context mContext;
    private final int width;
    private int oldX;
    private int distance;

    private boolean isChoose=true;

    private  View frontground;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                //MyCardView.this.setVisibility(View.GONE);
                ((MyFrameLayout)(MyCardView.this.getParent())).getListener().addContent();
                ((MyFrameLayout)(MyCardView.this.getParent())).removeView(MyCardView.this);
            }
        }
    };


    public MyCardView(Context context) {
        this(context,null);
    }

    public MyCardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity)(context)).getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        frontground = new View (context);
        frontground.setBackgroundColor(Color.WHITE);
        frontground.setAlpha(0f);
        addView(frontground);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        switch (widthMode){
            case MeasureSpec.EXACTLY:
                measureWidth =Math.max(measureWidth,600);
                break;
            case MeasureSpec.AT_MOST:
                measureWidth = Math.min(measureWidth,600);
                break;
        }
        switch (widthMode){
            case MeasureSpec.EXACTLY:
                measureHeight = Math.max(measureHeight,1000);
                break;
            case MeasureSpec.AT_MOST:
                measureHeight = Math.min(measureHeight,1000);
                break;
        }
        setMeasuredDimension(measureWidth,measureHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isChoose){
            return false;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN" );
                oldX = (int) event.getRawX();
                distance = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");
                distance = (int) (event.getRawX() - oldX)+distance;
                oldX = (int) event.getRawX();
                if(distance>0) {
                    setPivotX(getMeasuredWidth()-100);
                    setPivotY(getMeasuredHeight()+50);
                    doRightAnimate();
                }else{
                    setPivotX(0+50);
                    setPivotY(getMeasuredHeight()+100);
                    doLeftAnimate();
                }

                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP" );
                if(offset<0.3f){
                    distance = 0;
                    doRightAnimate();
                }else {
                    isChoose = false;
                    doOutAnimate();
                }
                oldX = 0;
                distance = 0;
                break;
        }
        return true;
    }

    private void doOutAnimate() {
        float f,l;
        if(distance>0){
            f = 45f;
            l = 200f;
        }else{
            f = -45f;
            l = -200f;
        }
        float curTranslationX = this.getTranslationX();
        float curTranslationY = this.getTranslationY();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(this, "translationX", curTranslationX, l);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", curTranslationY, -200f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(this, "rotation", frotate, f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(translationX).with(translationY).after(rotate);
        animSet.setDuration(500);
        animSet.start();

       //// TODO: 2017-3-18
        mHandler.sendEmptyMessageDelayed(1,1200);

    }


    private float fscaleY = 1f;
    private float fscaleX = 1f;
    private float frotate = 0f;
    private float ffadeInOut = 0f;
    private float offset;// 0到1
    private void doRightAnimate() {
        offset = 1f*distance/width*2;
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", fscaleY, 1f-0.1f*(offset));
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", fscaleX, 1f-0.1f*(offset));
        ObjectAnimator rotate = ObjectAnimator.ofFloat(this, "rotation", frotate, 30f*(offset));
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat( frontground , "alpha", ffadeInOut, 0.5f*(offset));
        fscaleY = 1f-0.1f*(offset);
        fscaleX = 1f-0.1f*(offset);
        frotate = 30f*(offset);
        ffadeInOut = 0.5f*(offset);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).with(scaleX).with(scaleY);
        animSet.setDuration(1);
        animSet.start();
    }

    private void doLeftAnimate() {
        offset = -1f*distance/width*2;
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", fscaleY, 1f-0.1f*(offset));
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", fscaleX, 1f-0.1f*(offset));
        ObjectAnimator rotate = ObjectAnimator.ofFloat(this, "rotation", frotate, -30f*(offset));
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat( frontground , "alpha", ffadeInOut, 0.5f*(offset));
        fscaleY = 1f-0.1f*(offset);
        fscaleX = 1f-0.1f*(offset);
        frotate = -30f*(offset);
        ffadeInOut = 0.5f*(offset);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).with(scaleX).with(scaleY);
        animSet.setDuration(1);
        animSet.start();
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG,"onLayout");
    }

}

//public class MyCardView extends LinearLayout{
//    private static final String TAG = "MyCardView";
//    private final int width;
//    private int oldX;
//    private int distance;
//    private int mLeft;
//    private int mTop;
//    private int mRight;
//    private int mBottom;
//    private boolean isFirst=true;
//
//
//    public MyCardView(Context context) {
//        this(context,null);
//    }
//
//    public MyCardView(Context context, AttributeSet attrs) {
//        this(context, attrs,0);
//    }
//
//    public MyCardView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        DisplayMetrics metric = new DisplayMetrics();
//        ((Activity)(context)).getWindowManager().getDefaultDisplay().getMetrics(metric);
//        width = metric.widthPixels;     // 屏幕宽度（像素）
//        int height = metric.heightPixels;   // 屏幕高度（像素）
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Log.i(TAG, "ACTION_DOWN" );
//                oldX = (int) event.getRawX();
//                distance = 0;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i(TAG, "ACTION_MOVE");
//                Log.i(TAG, "distance = "+distance);
//                Log.i(TAG, "width/2 = "+width/2);
//                if(Math.abs(distance)>90){
//                    break;
//                }
//                distance = (int) (event.getRawX() - oldX)+distance;
//                oldX = (int) event.getRawX();
//                layout(mLeft+distance, getTop(), mRight + distance, getBottom());
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.i(TAG, "ACTION_UP" +distance);
//                if(distance<60) {
//                    distance = 0;
//                    layout(mLeft, getTop(), mRight, getBottom());
//                }
//                oldX = 0;
//                distance = 0;
//                dosomething();
//                break;
//        }
//        return true;
//    }
//
//    private void dosomething() {
//
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//        Log.i(TAG,"onLayout");
//        if(isFirst) {
//            mLeft = l;
//            mTop = t;
//            mRight = r;
//            mBottom = b;
//            isFirst = false;
//        }
//        Log.i(TAG,"getMeasuredWidth() "+getMeasuredWidth());
//        Log.i(TAG,"getMeasuredHeight() "+getMeasuredHeight());
//        if(distance>0){
//            setPivotX(getMeasuredWidth()+20);
//            setPivotY(getMeasuredHeight());
//        }else{
//            setPivotX(0-20);
//            setPivotY(getMeasuredHeight());
//        }
//        float tmp = 1f*distance/width/2*90;
//        Log.i(TAG,"tmp= "+tmp);
//        setRotation(distance/3);
//    }
//}
