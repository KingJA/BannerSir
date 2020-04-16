package com.kingja.bannersir.pager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kingja.bannersir.adapter.AutoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:TODO
 * Create Time:2020/4/6 16:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BannerPager extends ViewPager implements IBanner {
    private String TAG = getClass().getSimpleName();
    private boolean isAuto;
    private Timer timer = new Timer();
    private boolean isTouch;
    private boolean unlimited;
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (isAuto && !isTouch) {
                post(() -> {
                    setCurrentItem(getCurrentItem() + 1);
                });
            }
        }
    };
    private OnBannerClickListener onBannerClickListener;
    private OnPageChangeListener onPageChangeListener;
    private int count;
    private int currentPosition;

    public BannerPager(Context context) {
        this(context, null);
    }

    public BannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBannerSir();
    }


    private void initBannerSir() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public <T> void setAdapter(AutoAdapter<T> adapter) {
        count = adapter.getData().size();
        setOffscreenPageLimit(1);
        setAdapter(new AutoPagerAdapter<>(adapter));
        addOnPageChangeListener(autoPagerChangeListener);
    }

    public interface OnBannerClickListener {
        void onBannerClick(int position);
    }

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    public interface OnPageChangeListener {
        void onPageSelected(int position);
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    @Override
    public void startAuto() {
        isAuto = true;
    }

    @Override
    public void stopAuto() {
        isAuto = false;
    }

    public void setAuto(boolean isAuto, int period) {
        this.isAuto = isAuto;
        timer.schedule(task, period, period);
    }

    private ViewPager.OnPageChangeListener autoPagerChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            currentPosition = position % count;
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageSelected(position);
            }
        }
    };

    private class AutoPagerAdapter<T> extends PagerAdapter {
        private List<T> list;
        private List<View> pageViewList = new ArrayList<>();

        AutoPagerAdapter(AutoAdapter<T> adapter) {
            this.list = adapter.getData() == null ? new ArrayList<T>() : adapter.getData();
            boolean needDoubleSize = list.size() == 2;
            for (int j = 0; j < (needDoubleSize ? 2 : 1); j++) {
                for (int i = 0; i < this.list.size(); i++) {
                    pageViewList.add(adapter.getView(getContext(), list.get(i), i));
                }
            }
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
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e(TAG, "instantiateItem: " + position);
            View pageview = pageViewList.get(position % pageViewList.size());
            container.addView(pageview);
            ViewParent parent = pageview.getParent();
            if (parent != null) {
                ((ViewPager) pageview.getParent()).removeView(pageview);
                if (((ViewPager) parent).getChildCount() < pageViewList.size()) {
                    container.addView(pageview);
                }
            } else {
                container.addView(pageview);
            }
            return pageview;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }
}
