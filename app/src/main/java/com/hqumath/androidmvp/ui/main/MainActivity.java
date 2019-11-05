package com.hqumath.androidmvp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: MainActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/3/6 17:15
 * 文件描述: 主界面
 * 注意事项: show/hide切换时内存占用高，且需要自己管理fragment生命周期
 *          改用viewpager支持懒加载
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
        fragmentList.add(new OneFragment());
        fragmentList.add(new TwoFragment());
        fragmentList.add(new ThreeFragment());
        fragmentList.add(new DemoFragment());

        MyFragmentPagerAdapter pagerAdapter =
                new MyFragmentPagerAdapter(getSupportFragmentManager());//11111111111111111111111111111111112111111111111
        pagerAdapter.setData(fragmentList, null);
        viewPager.setAdapter(pagerAdapter);
        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            viewPager.setCurrentItem(item.getOrder());
            return true;
        });
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<BaseFragment> fragmentList;
        private List<String> titles;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        public void setData(List<BaseFragment> fragmentList, List<String> titles) {
            this.fragmentList = fragmentList;
            this.titles = titles;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles == null ? "" : titles.get(position);
        }
    }
}
