package com.example.mybox.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.transition.Explode;
import android.view.View;

import com.example.mybox.R;
import com.example.mybox.adapter.TravelViewPagerAdapter;
import com.example.mybox.base.BaseActivity;
import com.qslll.library.ExpandingPagerFactory;
import com.qslll.library.fragments.ExpandingFragment;
import com.zxning.library.entity.Travel;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 左右滑动demo演示.
 */
public class Coverflow2Activity extends BaseActivity implements ExpandingFragment.OnExpandingClickListener {
    //@Bind(R.id.viewPager)

    private ViewPager viewPager;
    //@Bind(R.id.back)
    //ViewGroup back;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_coverflow2);
        //ButterKnife.bind(this, view);
        viewPager = (ViewPager) view.findViewById(R.id.vp);
        TravelViewPagerAdapter adapter = new TravelViewPagerAdapter(getSupportFragmentManager());
        adapter.addAll(generateTravelList());
        viewPager.setAdapter(adapter);

        ExpandingPagerFactory.setupViewPager(viewPager);
        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ExpandingFragment expandingFragment = ExpandingPagerFactory.getCurrentFragment(viewPager);
                if (expandingFragment != null && expandingFragment.isOpenend()) {
                    expandingFragment.close();
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

        return view;
    }

    @Override
    public void onBackPressed() {
        if (!ExpandingPagerFactory.onBackPressed(viewPager)) {
            super.onBackPressed();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Explode slideTransition = new Explode();
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }

    private List<Travel> generateTravelList() {
        List<Travel> travels = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            travels.add(new Travel("Seychelles", R.drawable.iv_d_img_newyork));
            travels.add(new Travel("Shang Hai", R.drawable.iv_d_img_newyork));
            travels.add(new Travel("New York", R.drawable.iv_d_img_newyork));
            travels.add(new Travel("castle", R.drawable.iv_d_img_newyork));
        }
        return travels;
    }

    private void startInfoActivity(View view, Travel travel) {
        Activity activity = this;
        /*ActivityCompat.startActivity(activity,
                InfoActivity.newInstance(activity, travel),
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        new Pair<>(view, getString(R.string.transition_image)))
                        .toBundle());*/

    }

    @Override
    public void onExpandingClick(View v) {
        //v is expandingfragment layout
        View view = v.findViewById(R.id.image);
        Travel travel = generateTravelList().get(viewPager.getCurrentItem());
        startInfoActivity(view, travel);
    }
}
