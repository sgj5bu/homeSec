package com.rwidman.homesec.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

import com.rwidman.homesec.Cache.Cache;
import com.rwidman.homesec.Fragments.Adapters.ProfileAdapter;
import com.rwidman.homesec.R;


public class ProfileFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setListAdapter(new ProfileAdapter(getActivity(),
                R.layout.profileitem, Cache.getInstance().getProfiles()));
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState)
    {
        setEmptyText("No Profiles");
    }
}
