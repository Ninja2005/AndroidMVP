package com.hqumath.androidmvp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseRecyclerAdapter;
import com.hqumath.androidmvp.base.BaseRecyclerViewHolder;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.utils.CommonUtil;
import com.hqumath.androidmvp.utils.StringUtil;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: MyRecyclerAdapter
 * 作    者: Created by gyd
 * 创建时间: 2019/2/14 14:35
 * 文件描述: 使用DiffUtil比对更新，减少刷新量
 * 注意事项: 不再使用
 * 版权声明:
 * ****************************************************************
 */
public class FollowRecyclerAdapter extends BaseRecyclerAdapter<UserInfoEntity> {

    public FollowRecyclerAdapter(Context context, List<UserInfoEntity> mData) {
        super(context, mData, R.layout.recycler_item_followers);
    }

    @Override
    public void convert(BaseRecyclerViewHolder holder, int position) {
        UserInfoEntity data = mData.get(position);
        holder.setText(R.id.tv_name, data.getLogin());

        ImageView ivHead = holder.getView(R.id.iv_head);
        if (!TextUtils.isEmpty(data.getAvatar_url())) {
            Glide.with(CommonUtil.getContext())
                    .load(data.getAvatar_url())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))//圆形
                    .into(ivHead);
        }
    }

    /**
     * 更高效的更新
     * notifyDataSetChanged 效率低
     */
    public void updateData(List<UserInfoEntity> newData) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(mData, newData));
        mData.clear();
        mData.addAll(newData);
        result.dispatchUpdatesTo(this);
    }

    private class DiffCallback extends DiffUtil.Callback {
        private List<UserInfoEntity> oldData, newData;

        DiffCallback(List<UserInfoEntity> oldData, List<UserInfoEntity> newData) {
            this.oldData = oldData;
            this.newData = newData;
        }

        @Override
        public int getOldListSize() {
            return oldData.size();
        }

        @Override
        public int getNewListSize() {
            return newData.size();
        }

        /**
         * 比对是否是同一个 viewType
         */
        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            UserInfoEntity oldItem = oldData.get(oldItemPosition);
            UserInfoEntity newItem = newData.get(newItemPosition);
            return oldItem.getId() == (newItem.getId());
        }

        /**
         * 比对内容是否相等
         */
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            UserInfoEntity oldItem = oldData.get(oldItemPosition);
            UserInfoEntity newItem = newData.get(newItemPosition);
            return StringUtil.equals(oldItem.getLogin(), newItem.getLogin())
                    && StringUtil.equals(oldItem.getAvatar_url(), newItem.getAvatar_url());
        }
    }
}
