package com.vince.demo.customdemo.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.vince.demo.customdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * viewpager  实现滑动效果
 */
public class ViewPagerActivity extends Activity{

    private static final String TAG = "ViewPagerActivity";

    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;
    private List<View> mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        init();
    }

    public void init() {
        mViewPager = (ViewPager) findViewById(R.id.ViewPager_viewpager);
        mListView = new ArrayList<View>();
        mPagerAdapter = new MyPagerAdapter();

        mListView.add(getImageView(R.mipmap.desert));
        mListView.add(getImageView(R.mipmap.hydrangeas));
        mListView.add(getImageView(R.mipmap.jellyfish));
        mListView.add(getImageView(R.mipmap.koala));
        mPagerAdapter.setmList(mListView);

        mViewPager.setAdapter(mPagerAdapter);

        int n = (Integer.MAX_VALUE / 2) % mListView.size();
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - n);
    }

    /**
     * 获取视图控件
     *
     * @param id
     * @return
     */
    private ImageView getImageView(int id) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);
        return imageView;
    }
}
