package com.rwidman.homesec.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

import com.rwidman.homesec.Cache.Cache;
import com.rwidman.homesec.Fragments.Adapters.AccessAdapter;
import com.rwidman.homesec.R;


public class AccessFragment extends ListFragment {

    public static AccessFragment newInstance() {
        AccessFragment fragment = new AccessFragment();
        return fragment;
    }

    public AccessFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new AccessAdapter(getActivity(),
                R.layout.accessitem, Cache.getInstance().getAccesses()));
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState)
    {
        setEmptyText("No Access");
    }
}
