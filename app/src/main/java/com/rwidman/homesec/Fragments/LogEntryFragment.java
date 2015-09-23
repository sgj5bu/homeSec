package com.rwidman.homesec.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rwidman.homesec.Cache.Cache;
import com.rwidman.homesec.Fragments.Adapters.LogEntryAdapter;
import com.rwidman.homesec.Fragments.Adapters.ModulAdapter;
import com.rwidman.homesec.R;


public class LogEntryFragment extends ListFragment {

    View mListView;
    View mProgressView;

    public static LogEntryFragment newInstance() {
        LogEntryFragment fragment = new LogEntryFragment();
        return fragment;
    }

    public LogEntryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_logentry,
                container, false);

        return rootView;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState)
    {
        mListView = view.findViewById(R.id.list);
        mProgressView = view.findViewById(R.id.control_progress);

        setListAdapter(new ModulAdapter(getActivity(),
                R.layout.logentryitem));
        ((TextView)this.getListView().getEmptyView()).setText("No Modules");
        Cache.getInstance().getLogEntries(this);
    }

    /**
     * Shows the progress UI
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mListView.setVisibility(show ? View.GONE : View.VISIBLE);
            mListView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mListView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mListView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
