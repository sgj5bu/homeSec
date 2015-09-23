package com.rwidman.homesec.Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rwidman.homesec.Model.Profile;
import com.rwidman.homesec.R;

import java.util.List;

/**
 * Created by J.Ringler on 23.09.2015.
 */
public class ProfileAdapter extends ArrayAdapter<Profile> {

    public ProfileAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ProfileAdapter(Context context, int resource, List<Profile> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.profileitem, null);
        }

        Profile p = getItem(position);

        if (p != null) {
            TextView name = (TextView) v.findViewById(R.id.profile_name);
            TextView state = (TextView) v.findViewById(R.id.profile_state);
            Button activate = (Button) v.findViewById(R.id.profile_button_activate);

            if (name != null) {
                name.setText(p.getName());
            }

            if (state != null) {
                state.setText(p.isActive() ? "Active" : "Inactive");
            }

            if (activate != null) {
                if(p.isActive())
                {
                    activate.setVisibility(View.GONE);
                }
                else
                {
                    activate.setVisibility(View.VISIBLE);
                    activate.setTag(p);
                    activate.setOnClickListener(new OnActivateClickListener());
                }
            }

        }
        return v;
    }

    private class OnActivateClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Profile p = (Profile) v.getTag();
        }
    }

}
