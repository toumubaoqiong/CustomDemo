package com.vince.demo.customdemo.viewpager;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * decription ：viewPager 循环适配器，设置值最大
 */
public class MyPagerAdapter extends PagerAdapter {
    private static final String TAG = "MyPagerAdapter";

    private List<View> mList;

    public void setmList(List<View> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.i(TAG, "instantiateItem: " + position);
        position %= mList.size();

        container.addView(mList.get(position));//添加有父布局的控件会报错，因此要在前面进行处理
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        position %= mList.size();
        container.removeView(mList.get(position));
    }
}