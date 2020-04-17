package com.kingja.bannersir.pager;

import com.kingja.bannersir.adapter.AutoAdapter;

/**
 * Description:TODO
 * Create Time:2020/4/6 16:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface IBanner {
    void startAuto();
    void stopAuto();
    <T> void setAdapter(AutoAdapter<T> adapter, boolean autoRoll, int period);
}
