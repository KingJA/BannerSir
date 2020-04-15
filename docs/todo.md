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
轮播切换分平滑和瞬间两
加入页面切花你监听器，用add方式而不是setOn方式\
图片按比例显示

支持：
自动轮播
自定义Index页码：样式，位置
自定义指示器：样式，位置，间距，尺寸
指示器样式可使用xml方式引入layer-drawable，也可通过代码方式自定义，绘制casve


暂时不支持：
indicator尺寸不同及切换动画


后续功能：
页面切换效果
解决在scrollview中的滑动冲突问题，目前用重写scrollview解决，不彻底
定时器关闭问题























