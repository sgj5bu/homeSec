package com.rwidman.homesec.Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rwidman.homesec.Fragments.ModulFragment;
import com.rwidman.homesec.Library.Library;
import com.rwidman.homesec.Model.Modul;
import com.rwidman.homesec.R;

import java.util.List;

/**
 * Created by J.Ringler on 23.09.2015.
 */
public class ModulAdapter extends ArrayAdapter<Modul> {

    private ModulFragment mContext;

    public ModulAdapter(ModulFragment context, int textViewResourceId) {
        super(context.getActivity(), textViewResourceId);
        mContext = context;
    }

    public ModulAdapter(ModulFragment context, int resource, List<Modul> items) {
        super(context.getActivity(), resource, items);
        mContext = context;
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
                    camera.setVisibility(View.VISIBLE);
                    camera.setTag(m);
                    camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Modul m = (Modul) v.getTag();
                            if (m.getState().equals("true")){
                                Library.getInstance().takePhoto(mContext,m);
                            }
                        }
                    });
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
