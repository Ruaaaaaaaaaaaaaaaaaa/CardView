package com.wmj.myviewpager;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyFrameLayout frameLayout = (MyFrameLayout) findViewById(R.id.framelayout);
        for(int i = 0;i<5;i++){
            MyCardView c = new MyCardView(MainActivity.this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            c.setLayoutParams(params);
            c.setCardBackgroundColor(Color.GRAY);
            c.addView(LayoutInflater.from(MainActivity.this).inflate(R.layout.item,c,false));
            frameLayout.addView(c);
        }
        frameLayout.setAddContentListener(new MyFrameLayout.AddContentListener() {
            @Override
            public void addContent() {
                Log.i("qqq",frameLayout.getChildCount()+"");
                if(frameLayout.getChildCount()==1){
;                    for(int i = 0;i<5;i++){
                        MyCardView c = new MyCardView(MainActivity.this);
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        params.gravity = Gravity.CENTER;
                        c.setLayoutParams(params);
                        c.setCardBackgroundColor(Color.GRAY);
                        c.addView(LayoutInflater.from(MainActivity.this).inflate(R.layout.item,c,false));
                        frameLayout.addView(c);
                    }
                }
            }
        });
//        final List<View> list = new ArrayList<>();
//        for(int i = 0 ;i<4;i++){
//            View view = LayoutInflater.from(this).inflate(R.layout.item,null);
//            list.add(view);
//        }
//        MyViewPager myViewPager = (MyViewPager) findViewById(R.id.myviewpager);
//        PagerAdapter adapter = new PagerAdapter() {
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                container.addView(list.get(position));
//                return list.get(position);
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//               container.removeView(list.get(position));
//            }
//
//            @Override
//            public int getCount() {
//                return list.size();
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view ==object;
//            }
//        };
//        myViewPager.setAdapter(adapter);
    }
}
