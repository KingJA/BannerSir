package com.kingja.bannersir.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/8/4 15:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface AutoAdapter<T> {
    View getView(T data, int position);
    View getView(Context context,T data, int position);

    List<T> getData();

}
