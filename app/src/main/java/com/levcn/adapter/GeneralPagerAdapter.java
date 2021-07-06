package com.levcn.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/5 17:39
 * desc   :
 */
public class GeneralPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    public GeneralPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments != null ? fragments : new ArrayList<Fragment>();
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        //返回有效的View的个数
        return this.fragments.size();
    }

    public void onRefresh(List<Fragment> fragments) {
        this.fragments.clear();
        this.fragments.addAll(fragments);
        super.notifyDataSetChanged();
    }

    public void delete(int position) {
        this.fragments.remove(position);
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
