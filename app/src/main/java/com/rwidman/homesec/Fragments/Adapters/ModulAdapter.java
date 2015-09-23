package com.rwidman.homesec.Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rwidman.homesec.Model.Modul;
import com.rwidman.homesec.R;

import java.util.List;

/**
 * Created by J.Ringler on 23.09.2015.
 */
public class ModulAdapter extends ArrayAdapter<Modul> {

    public ModulAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ModulAdapter(Context context, int resource, List<Modul> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.modulitem, null);
        }

        Modul m = getItem(position);

        if (m != null) {
            TextView name = (TextView) v.findViewById(R.id.modul_name);
            TextView state = (TextView) v.findViewById(R.id.modul_state);
            TextView camera = (TextView) v.findViewById(R.id.modul_button_photo);

            if (name != null) {
                name.setText(m.getName());
            }

            if (state != null) {
                state.setText(m.getState());
            }

            if (camera != null) {
                if(m.hasCamera())
                {
                    //camera.setOnClickListener();
                }
                else
                {
                   camera.setVisibility(View.GONE);
                }
            }
        }

        return v;
    }

}
