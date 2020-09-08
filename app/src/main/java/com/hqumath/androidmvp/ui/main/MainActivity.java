package com.hqumath.androidmvp.ui.main;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.adapter.MyFragmentPagerAdapter;
import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.ui.follow.FollowersFragment;
import com.hqumath.androidmvp.ui.repos.ReposFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: MainActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/3/6 17:15
 * 文件描述: 主界面
 * 注意事项: show/hide切换时内存占用高，且需要自己管理fragment生命周期
 * 改用viewpager支持懒加载
 * 版权声明:
 * ****************************************************************
 */
public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.navigation);
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void initData() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ReposFragment());
        fragmentList.add(new FollowersFragment());
        fragmentList.add(new SettingsFragment());
        fragmentList.add(new DemoFragment());

        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setData(fragmentList, null);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            viewPager.setCurrentItem(item.getOrder());
            return true;
        });
    }
}
