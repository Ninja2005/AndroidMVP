package com.hqumath.androidmvp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseRecyclerAdapter;
import com.hqumath.androidmvp.base.BaseRecyclerViewHolder;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.utils.CommonUtil;

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
public class FollowRecyclerAdapter extends BaseRecyclerAdapter<UserInfoEntity> {

    private List<UserInfoEntity> mDatas;

    public FollowRecyclerAdapter(Context context, List<UserInfoEntity> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        this.mDatas = mDatas;
    }

    @Override
    public void convert(BaseRecyclerViewHolder holder, int position) {
        UserInfoEntity data = mDatas.get(position);
        holder.setText(R.id.tv_name, data.getLogin());

        ImageView ivHead = holder.getView(R.id.iv_head);
        if (!TextUtils.isEmpty(data.getAvatar_url())) {
            Glide.with(CommonUtil.getContext())
                    .load(data.getAvatar_url())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))//圆形
                    .into(ivHead);
        }
    }
}
