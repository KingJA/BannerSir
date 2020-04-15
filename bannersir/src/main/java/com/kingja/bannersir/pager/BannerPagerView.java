package com.kingja.bannersir.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.kingja.bannersir.adapter.AutoAdapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:TODO
 * Create Time:2020/4/6 16:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BannerPagerView extends ViewGroup implements IBanner {
    private int childHeight;
    private int childWidth;
    private int childCount;
    private int x;
    private int index = 0;
    private Scroller scroller;
    private String TAG = getClass().getSimpleName();
    private boolean isAuto;
    private Timer timer = new Timer();
    private boolean isClick;
    private boolean isTouch;
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (isAuto&&!isTouch) {
                post(() -> {
                    if (++index >= childCount) {
                        index = 0;
                    }
                    scrollTo(childWidth * index, 0);
                    if (onPageChangeListener != null) {
                        onPageChangeListener.onPageSelected(index);
                    }
                });
            }
        }
    };
    private OnBannerClickListener onBannerClickListener;
    private OnPageChangeListener onPageChangeListener;

    public BannerPagerView(Context context) {
        this(context, null);
    }

    public BannerPagerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBannerSir();
    }


    private void initBannerSir() {
        scroller = new Scroller(getContext());

    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            View firstChild = getChildAt(0);
            childWidth = firstChild.getMeasuredWidth();
            childHeight = firstChild.getMeasuredHeight();
            int width = childWidth * childCount;
            setMeasuredDimension(width, childHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int leftMargin = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                childView.layout(leftMargin, 0, leftMargin + childWidth, childHeight);
                leftMargin += childWidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isClick = true;
                isTouch = true;
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                x = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                isClick = false;
                int moveX = (int) event.getX();
                int distance = moveX - x;
                scrollBy(-distance, 0);
                x = moveX;
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                index = (scrollX + childWidth / 2) / childWidth;
                if (index < 0) {
                    index = 0;
                } else if (index > childCount - 1) {
                    index = childCount - 1;
                }
                if (isClick) {
                    if (onBannerClickListener != null) {
                        onBannerClickListener.onBannerClick(index);
                    }
                } else {
                    int dx = index * childWidth - scrollX;
                    scroller.startScroll(scrollX, 0, dx, 0);
                    postInvalidate();
                    if (onPageChangeListener != null) {
                        onPageChangeListener.onPageSelected(index);
                    }
                }
                isTouch = false;
                break;
            default:
                break;

        }
        return true;
    }


    @Override
    public <T> void setAdapter(AutoAdapter<T> adapter) {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        List<T> data = adapter.getData();
        for (int i = 0; i < data.size(); i++) {
            //TODO
            View view = adapter.getView(getContext(),data.get(i), i);
            addView(view, lp);
        }
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
}
