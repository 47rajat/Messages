package com.wssholmes.stark.messagesotp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private HomeScreenViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.home_screen_pager);
        mTabLayout = (TabLayout) findViewById(R.id.home_screen_tabs);
        mPagerAdapter = new HomeScreenViewPagerAdapter(getSupportFragmentManager(), this);

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
