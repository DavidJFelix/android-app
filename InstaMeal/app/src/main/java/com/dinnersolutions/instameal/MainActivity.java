package com.dinnersolutions.instameal;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinnersolutions.instameal.api.AuthManager;
import com.dinnersolutions.instameal.api.InstamealApi;
import com.dinnersolutions.instameal.application.InstamealApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Inject
    InstamealApi instamealApi;
    @Inject
    AuthManager authManager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.main_pager)
    ViewPager mainPager;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setupViewPager();
        tabLayout.setupWithViewPager(mainPager);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(MealsFragment.newInstance(), "Meals");
        adapter.addFrag(DummyFragment.newInstance(ContextCompat.getColor(this, R.color.ripple_material_light)), "Meals");
        adapter.addFrag(DummyFragment.newInstance(ContextCompat.getColor(this, R.color.button_material_dark)), "Meals");
        mainPager.setAdapter(adapter);
    }

    public void inject(Context context) {
        InstamealApplication app = InstamealApplication.from(context);
        app.component().inject(this);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static class DummyFragment extends Fragment {
        public int color;

        public static DummyFragment newInstance(int color) {
            DummyFragment fragment = new DummyFragment();
            fragment.color = color;
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dummy_fragment, container, false);
            view.setBackgroundColor(color);
            return view;
        }
    }
}
