package com.kingja.bannersir.indicator;

import android.view.View;

/**
 * Description:TODO
 * Create Time:2017/8/5 11:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface Indicator {

    void setIndicatorNormal();
    void setIndicatorSelected();
    void setIndicatorSize(int width,int height);
    View getInstance();
}
