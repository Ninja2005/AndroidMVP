package com.hqumath.androidmvp.ui.repos;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.adapter.MyFragmentPagerAdapter;
import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.ui.repos.MyReposFragment;
import com.hqumath.androidmvp.ui.repos.StarredFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: OneFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ReposFragment extends BaseFragment {

    private TabLayout tablayout;
    private ViewPager viewPager;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_repos;
    }

    protected void initView(View rootView) {
        tablayout = rootView.findViewById(R.id.tablayout);
        viewPager = rootView.findViewById(R.id.viewpager);
    }

    protected void initListener() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MyReposFragment());
        fragmentList.add(new StarredFragment());

        List<String> titles = new ArrayList<>();
        titles.add("MyRepos");
        titles.add("Starred");

        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        pagerAdapter.setData(fragmentList, titles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());
        tablayout.setupWithViewPager(viewPager);
    }

}