package com.levcn.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/1 17:11
 * desc   :
 */
public class MyFragmentAdapter extends FragmentStateAdapter {

    private List<Fragment> mFragmentList;

    public MyFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        mFragmentList = new ArrayList<>();
    }

    public MyFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
        mFragmentList = new ArrayList<>();
    }

    public MyFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        mFragmentList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    /**
     * 添加一个Fragment
     */
    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
        notifyDataSetChanged();
    }

    /**
     * 删除一个Fragment
     */
    public void removeFragment() {
        if (mFragmentList.size() > 0) {
            mFragmentList.remove(mFragmentList.size() - 1);
            notifyDataSetChanged();
        }
    }

}
