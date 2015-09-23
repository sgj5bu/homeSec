package com.rwidman.homesec;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.rwidman.homesec.Fragments.AccessFragment;
import com.rwidman.homesec.Fragments.LogEntryFragment;
import com.rwidman.homesec.Fragments.ModulFragment;
import com.rwidman.homesec.Fragments.PersonFragment;
import com.rwidman.homesec.Fragments.ProfileFragment;

import java.util.ArrayList;

public class ControlActivity extends FragmentActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private ControlFragmentAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ControlFragmentAdapter(getSupportFragmentManager());

        Fragment modul= new ModulFragment();
        mPagerAdapter.addFragment(modul);

        Fragment access= new AccessFragment();
        mPagerAdapter.addFragment(access);

        Fragment person= new PersonFragment();
        mPagerAdapter.addFragment(person);

        Fragment profile= new ProfileFragment();
        mPagerAdapter.addFragment(profile);

        Fragment logEntry= new LogEntryFragment();
        mPagerAdapter.addFragment(logEntry);

        //set the adapter to the View pager
        mPager.setAdapter(mPagerAdapter);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ControlFragmentAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 5;
        ArrayList<Fragment> fragments = new ArrayList<>();
        private final String[] pageTitle = { "Module", "Access", "Person", "Profiles", "Logs" };

        public ControlFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitle[position];
        }

        public void addFragment(Fragment frag) {
            fragments.add(frag);
        }
    }
}
