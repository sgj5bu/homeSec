package com.rwidman.homesec.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

import com.rwidman.homesec.Cache.Cache;
import com.rwidman.homesec.Fragments.Adapters.LogEntryAdapter;
import com.rwidman.homesec.R;


public class LogEntryFragment extends ListFragment {

    public static LogEntryFragment newInstance() {
        LogEntryFragment fragment = new LogEntryFragment();
        return fragment;
    }

    public LogEntryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new LogEntryAdapter(getActivity(),
                R.layout.logentryitem, Cache.getInstance().getLogEntries()));
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState)
    {
        setEmptyText("No Logs");
    }

}
