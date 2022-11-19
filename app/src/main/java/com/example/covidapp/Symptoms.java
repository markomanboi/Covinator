package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Symptoms extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorWhite)));
        actionBar.setElevation(0);
        actionBar.setTitle("");

        mSlideViewPager=(ViewPager) findViewById(R.id.viewPager);
        mDotLayout= (LinearLayout) findViewById(R.id.dots);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        mSlideViewPager.setAdapter(new MyAdapter());
        addDotsIndicator(0);
    }

    public class MyAdapter extends PagerAdapter {

        LayoutInflater layoutInflater;

        int layouts[]={R.layout.symptoms_page1, R.layout.symptoms_page2, R.layout.symptoms_page3,
                        R.layout.symptoms_page4};
        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == (ConstraintLayout) o;
        }

        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.symptoms_page1,container,false);
            View view2=layoutInflater.inflate(R.layout.symptoms_page2,container,false);
            View view3=layoutInflater.inflate(R.layout.symptoms_page3,container,false);
            View view4=layoutInflater.inflate(R.layout.symptoms_page4,container,false);
            View[] viewarr ={view,view2,view3,view4};

            container.addView(viewarr[position]);
            return viewarr[position];
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ConstraintLayout)(object));
        }
    }

    public void addDotsIndicator(int position){
        mDots=new TextView[4];
        mDotLayout.removeAllViews();
        for (int i=0; i<mDots.length; i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorGrey));

            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }
    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}