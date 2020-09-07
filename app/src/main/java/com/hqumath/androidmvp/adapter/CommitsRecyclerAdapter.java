package com.hqumath.androidmvp.adapter;

import android.content.Context;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseRecyclerAdapter;
import com.hqumath.androidmvp.base.BaseRecyclerViewHolder;
import com.hqumath.androidmvp.bean.CommitEntity;

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
public class CommitsRecyclerAdapter extends BaseRecyclerAdapter<CommitEntity> {

    private List<CommitEntity> mDatas;

    public CommitsRecyclerAdapter(Context context, List<CommitEntity> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        this.mDatas = mDatas;
    }

    @Override
    public void convert(BaseRecyclerViewHolder holder, int position) {
        CommitEntity data = mDatas.get(position);
        holder.setText(R.id.tv_name, data.getCommit().getCommitter().getName());
        holder.setText(R.id.tv_time, data.getCommit().getCommitter().getDate());
        holder.setText(R.id.tv_message, data.getCommit().getMessage());
        holder.setText(R.id.tv_sha, data.getSha());
    }
}
