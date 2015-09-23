package com.rwidman.homesec.Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rwidman.homesec.Model.Access;
import com.rwidman.homesec.R;

import java.util.List;

/**
 * Created by J.Ringler on 23.09.2015.
 */
public class AccessAdapter extends ArrayAdapter<Access> {

    public AccessAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public AccessAdapter(Context context, int resource, List<Access> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.accessitem, null);
        }

        Access a = getItem(position);

        if (a != null) {
            TextView name = (TextView) v.findViewById(R.id.access_name);
            TextView state = (TextView) v.findViewById(R.id.access_state);
            Button open = (Button) v.findViewById(R.id.access_button_open);
            Button close = (Button) v.findViewById(R.id.access_button_close);

            if (name != null) {
                name.setText(a.getName());
            }

            if (state != null) {
                state.setText(a.getState());
            }
/*
            if (open != null) {
                open.setOnClickListener();
            }

            if (close != null) {
                close.setOnClickListener();
            }
            */
        }

        return v;
    }

}
