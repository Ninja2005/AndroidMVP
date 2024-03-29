package com.hqumath.androidmvp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.hqumath.androidmvp.adapter.MyFragmentPagerAdapter;
import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.databinding.ActivityMainBinding;
import com.hqumath.androidmvp.ui.follow.FollowersFragment;
import com.hqumath.androidmvp.ui.repos.ReposFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    public View initContentView(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ReposFragment());
        fragmentList.add(new FollowersFragment());
        fragmentList.add(new SettingsFragment());
        fragmentList.add(new AboutFragment());

        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setData(fragmentList, null);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(3);//缓存当前界面每一侧的界面数
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        binding.navigation.setOnNavigationItemSelectedListener(item -> {
            binding.viewPager.setCurrentItem(item.getOrder());
            return true;
        });
    }
}
