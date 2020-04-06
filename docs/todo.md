1.PagerAdapter 缓存视图
2.按住触摸时候不能轮播
3.可以矢量图，也可以位图
4.切换动画

Scorller的应用
1.创建
scroller = new Scroller(getContext());
2.重写
 @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }
3.滑动
    int dx = index * childWidth - scrollX;
    scroller.startScroll(scrollX, 0, dx, 0);
    postInvalidate();

平滑滑动
点击事件
调用方法
高度测量