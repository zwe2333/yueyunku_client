package com.gdcp.yueyunku_client.factory;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.ui.fragment.BaseFragment;
import com.gdcp.yueyunku_client.ui.fragment.MeFragment;
import com.gdcp.yueyunku_client.ui.fragment.YuedongquanFragment;
import com.gdcp.yueyunku_client.ui.fragment.YueyundongFrament;

/**
 * Created by Asus on 2017/5/10.
 */

public class FragmentFactory {
    private static FragmentFactory sFragmentFactory;

    private BaseFragment mYuedongquanFrament;
    private BaseFragment mYueyundongFrament;
    private BaseFragment mMeFragment;

    public static FragmentFactory getInstance() {
        if (sFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (sFragmentFactory == null) {
                    sFragmentFactory = new FragmentFactory();
                }
            }
        }
        return sFragmentFactory;
    }

    public BaseFragment getFragment(int id) {
        switch (id) {
            case R.id.tab_yuedongquan:
                return getYuedongquanFragment();
            case R.id.tab_yueyundong:
                return getYueyundongFragment();
            case R.id.tab_me:
                return getMeFragment();
        }
        return null;
    }


    public BaseFragment getYuedongquanFragment() {
        if (mYuedongquanFrament==null){
            mYuedongquanFrament=new YuedongquanFragment();
        }
        return mYuedongquanFrament;
    }

    public BaseFragment getYueyundongFragment() {
        if (mYueyundongFrament==null){
            mYueyundongFrament=new YueyundongFrament();
        }
        return mYueyundongFrament;
    }

    public BaseFragment getMeFragment() {
        if (mMeFragment==null){
            mMeFragment=new MeFragment();
        }
        return mMeFragment;
    }
}
