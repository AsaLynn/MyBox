package com.example.mybox.factory;

import android.support.v4.app.Fragment;

import com.example.mybox.R;
import com.example.mybox.fragment.HomeComprehensiveFragment;
import com.example.mybox.fragment.HomeMyFragment;
import com.example.mybox.fragment.HomeNetFrament;
import com.example.mybox.fragment.HomePageFragment;
import com.example.mybox.fragment.HomeStorageFragment;
import com.zxning.library.tool.UIUtils;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {
    //Map<Integer,Fragment>
    private static Map<Integer, Fragment> fragmentMap = new HashMap<Integer, Fragment>();

    /**
     * 根据integers.xml中不同id,获取不同的fragment.保证了获取fragment的唯一性.
     *
     * @param intId
     * @return
     */
    public static Fragment createFragment(int intId) {//position
        int index = UIUtils.getInteger(intId);
       Fragment fragment = fragmentMap.get(index);
        if (fragment != null) {
            return fragment;
        } else {
            switch (intId) {
                case R.integer.home_index:
                    //首页
                    fragment = new HomePageFragment();
                    break;
                case R.integer.wealth_index:
                    //财富.wealth
                    fragment = new HomeStorageFragment();
                    break;
                case R.integer.net_index:
                    fragment = new HomeNetFrament();
                    break;
                case R.integer.me_index:
                    fragment = new HomeMyFragment();
                    break;
                case R.integer.page1_index:
                    //页面第一页.
                    fragment = new HomePageFragment();
                    break;
                case R.integer.comprehensive_index:
                    //页面第一页.
                    fragment = new HomeComprehensiveFragment();
                    break;
                case R.integer.net1_index:
                    fragment = new HomeNetFrament();
                    break;
                case R.integer.comprehensive1_index:
                    fragment = new HomeComprehensiveFragment();
                    break;
                case R.integer.storage1_index:
                    fragment = new HomeStorageFragment();
                    break;
            }
            fragmentMap.put(index, fragment);
        }
        return fragment;
    }

    public static void clearAllFragment() {//position
        fragmentMap.clear();
    }

}
