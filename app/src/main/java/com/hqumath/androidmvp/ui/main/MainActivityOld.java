package com.hqumath.androidmvp.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseActivity;
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
public class MainActivityOld extends BaseActivity {
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";//当前显示的fragment

    private BottomNavigationView bottomNavigationView;

    private FragmentManager fragmentManager;

    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragments = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    public int initContentView() {
        LogUtil.d("TAG12", "MainActivity onCreate");
        return R.layout.activity_main;
    }

    @Override
    public void onResume() {
        LogUtil.d("TAG12", "MainActivity onResume");
        super.onResume();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            //binding.viewpager.setCurrentItem(item.getOrder());
            currentIndex = item.getOrder();
            showFragment();
            return true;
        });
        //binding.navigation.getMenu().getItem(position).setChecked(true);

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) { // “内存重启”时调用
            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT, 0);
            fragments.clear();//.removeAll();
            fragments.add(fragmentManager.findFragmentByTag(0 + ""));
            fragments.add(fragmentManager.findFragmentByTag(1 + ""));
            fragments.add(fragmentManager.findFragmentByTag(2 + ""));
            fragments.add(fragmentManager.findFragmentByTag(3 + ""));
            //恢复fragment页面
            restoreFragment();
        } else {
            fragments.add(new OneFragment());
            fragments.add(new TwoFragment());
            fragments.add(new ThreeFragment());
            fragments.add(new DemoFragment());
            showFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //“内存重启”时保存当前的fragment名字
        outState.putInt(CURRENT_FRAGMENT,currentIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void initData() {

    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment() {
        /*FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if (!fragments.get(currentIndex).isAdded()) {
            transaction.hide(currentFragment)
                    .add(R.id.main_container, fragments.get(currentIndex), "" + currentIndex);
            //第三个参数为添加当前的fragment时绑定一个tag
        } else {
            transaction.hide(currentFragment)
                    .show(fragments.get(currentIndex));
        }
        currentFragment = fragments.get(currentIndex);
        transaction.commit();*/
    }

    /**
     * 恢复fragment
     */
    private void restoreFragment() {
        FragmentTransaction mBeginTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if (i == currentIndex) {
                mBeginTransaction.show(fragments.get(i));
            } else {
                mBeginTransaction.hide(fragments.get(i));
            }
        }
        mBeginTransaction.commit();
        //把当前显示的fragment记录下来
        currentFragment = fragments.get(currentIndex);

    }
}
