package com.hqumath.androidmvp.adapter;

import android.content.Context;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseRecyclerAdapter;
import com.hqumath.androidmvp.base.BaseRecyclerViewHolder;
import com.hqumath.androidmvp.bean.ReposEntity;

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
public class ReposRecyclerAdapter extends BaseRecyclerAdapter<ReposEntity> {

    private List<ReposEntity> mDatas;

    public ReposRecyclerAdapter(Context context, List<ReposEntity> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        this.mDatas = mDatas;
    }

    @Override
    public void convert(BaseRecyclerViewHolder holder, int position) {
        ReposEntity data = mDatas.get(position);
        holder.setText(R.id.tv_name, data.getName());
        holder.setText(R.id.tv_description, data.getDescription());
        holder.setText(R.id.tv_author, data.getOwner().getLogin());
    }
}
