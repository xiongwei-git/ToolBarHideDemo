package com.tekinarslan.material.sample;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class SampleActivity2 extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private ListView mDrawerList;
    private ViewPager pager;
    private String titles[] = new String[]{"Sample Tab 1", "Sample Tab 2", "Sample Tab 3"};
    private Toolbar mToolbar;
    private CustomScrollView mScrollView;

    private int mToolbarHeight = 0;
    private float mToolbarTranslationY = 0;
    private int mLastY = -1;
    private int mLastOldY = -1;

    //SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_v2);

        mScrollView = (CustomScrollView)findViewById(R.id.content_layout_scrollview);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        }

        //pager = (ViewPager) findViewById(R.id.viewpager);
        //slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        //pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles));

        //slidingTabLayout.setViewPager(pager);
//        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return Color.WHITE;
//            }
//        });
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        String[] values = new String[]{
                "DEFAULT", "RED", "BLUE", "MATERIAL GREY"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                        //slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 1:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.red));
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.red));
                        //slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.red));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                    case 2:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.blue));
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                        //slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                    case 3:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        //slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                }

            }
        });
        mScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mScrollView.setOnScrollListener(onScrollViewListener);
        mScrollView.setOnTouchListener(mOnTouchListener);

    }



    private CustomScrollView.ScrollViewListener onScrollViewListener = new CustomScrollView.ScrollViewListener() {
        @Override
        public void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy) {
            onMoveToolbar(y,oldy);
            Log.e("xiongwei","y = "+y+"::"+"oldy= "+oldy);
        }

        @Override
        public void onScrollStopped() {
            switchToolbarStatus();
        }
    };

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                mToolbarHeight = mToolbar.getHeight();
                mToolbarTranslationY = mToolbar.getTranslationY();
            }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                mScrollView.onScrollerStopListen();
            }
            return false;
        }
    };

    private void switchToolbarStatus(){
        if(mToolbarTranslationY >= 0 || Math.abs(mToolbarTranslationY) >= mToolbarHeight)return;
        boolean showOrHide = mToolbarTranslationY >= -(mToolbarHeight/2) || mScrollView.getScrollY() <= mToolbarHeight/2;
        Log.e("xiongwei","动画"+showOrHide);
        if(showOrHide){
            mToolbarTranslationY = 0;
            mToolbar.animate().translationY(0).start();
        }else {
            mToolbarTranslationY = -mToolbarHeight;
            mToolbar.animate().translationY(-mToolbarHeight).start();
        }
    }

    private void onMoveToolbar(int y,int oldY){
        if(y == mLastY && oldY == mLastOldY)return;
        mLastY = y;
        mLastOldY = oldY;
        int moveLength = y - oldY;
        //Log.e("xiongwei","mToolbarTranslationY = "+mToolbarTranslationY+"::"+"moveLength= "+moveLength);
        if(moveLength == 0)return;
        if(mToolbarTranslationY == 0 && moveLength <0 )return;
        if(mToolbarTranslationY == -mToolbarHeight && moveLength > 0 )return;
        mToolbarTranslationY = mToolbarTranslationY - moveLength;
        if(mToolbarTranslationY > 0){
            mToolbarTranslationY = 0;
        }else if(mToolbarTranslationY < -mToolbarHeight){
            mToolbarTranslationY = - mToolbarHeight;
        }
        mToolbar.setTranslationY(mToolbarTranslationY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}
