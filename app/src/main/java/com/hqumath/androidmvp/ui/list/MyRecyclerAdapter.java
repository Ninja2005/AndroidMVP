package com.hqumath.androidmvp.ui.list;

import android.content.Context;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseRecyclerAdapter;
import com.hqumath.androidmvp.base.BaseRecyclerViewHolder;
import com.hqumath.androidmvp.bean.ProductInfo;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: MyRecyclerAdapter
 * 作    者: Created by gyd
 * 创建时间: 2019/2/14 14:35
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MyRecyclerAdapter extends BaseRecyclerAdapter<ProductInfo> {

    private List<ProductInfo> mDatas;

    public MyRecyclerAdapter(Context context, List<ProductInfo> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        this.mDatas = mDatas;
    }

    @Override
    public void convert(BaseRecyclerViewHolder holder, int position) {
        holder.setText(R.id.tv_name, mDatas.get(position).getProductName());
        holder.setText(R.id.tv_value, mDatas.get(position).getProductProfit() + "%");
    }
}
