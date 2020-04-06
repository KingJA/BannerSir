package com.kingja.bannersir;

import com.kingja.bannersir.pager.AutoAdapter;

/**
 * Description:TODO
 * Create Time:2020/4/6 16:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface IBanner {
    void startAuto();
    void stopAuto();
    <T> void setAdapter(AutoAdapter<T> adapter);
}
