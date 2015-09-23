package com.rwidman.homesec.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

import com.rwidman.homesec.Cache.Cache;
import com.rwidman.homesec.Fragments.Adapters.ProfileAdapter;
import com.rwidman.homesec.R;


public class ProfileFragment extends ListFragment {

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ProfileAdapter(getActivity(),
                R.layout.profileitem, Cache.getInstance().getProfiles()));
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState)
    {
        setEmptyText("No Profiles");
    }
}
