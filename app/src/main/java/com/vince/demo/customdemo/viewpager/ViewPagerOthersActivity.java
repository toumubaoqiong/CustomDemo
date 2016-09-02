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
public class ViewPagerOthersActivity extends Activity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "ViewPagerOthersActivity";

    private ViewPager mViewPager;
    private MyPagerAdapterOthers mMyPagerAdapterOthers;
    private List<View> mListView;
    //标记是否进行了切换
    private boolean mIsChanged = false;
    //当前的显示界面
    private int mCurrentPagePosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        init();
    }

    public void init() {
        mViewPager = (ViewPager) findViewById(R.id.ViewPager_viewpager);
        mListView = new ArrayList<View>();
        mMyPagerAdapterOthers = new MyPagerAdapterOthers();

        mListView.add(getImageView(R.mipmap.koala));
        mListView.add(getImageView(R.mipmap.desert));
        mListView.add(getImageView(R.mipmap.hydrangeas));
        mListView.add(getImageView(R.mipmap.jellyfish));
        mListView.add(getImageView(R.mipmap.koala));
        mListView.add(getImageView(R.mipmap.desert));
        mMyPagerAdapterOthers.setmList(mListView);

        mViewPager.setAdapter(mMyPagerAdapterOthers);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(1);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mIsChanged = true;
        if (position > mListView.size() - 2) {
            mCurrentPagePosition = 1;
        } else if (position < 1) {
            mCurrentPagePosition = mListView.size() - 2;
        } else {
            mCurrentPagePosition = position;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (ViewPager.SCROLL_STATE_IDLE == state) {
            if (mIsChanged) {
                mIsChanged = false;
                mViewPager.setCurrentItem(mCurrentPagePosition, false);
            }
        }
    }
}
