package com.rwidman.homesec.Fragments.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rwidman.homesec.Fragments.AccessFragment;
import com.rwidman.homesec.Library.Library;
import com.rwidman.homesec.Model.Access;
import com.rwidman.homesec.R;

import java.util.List;

/**
 * Created by J.Ringler on 23.09.2015.
 */
public class AccessAdapter extends ArrayAdapter<Access> {

    private AccessFragment mContext;

    public AccessAdapter(AccessFragment context, int textViewResourceId) {
        super(context.getActivity(), textViewResourceId);
        mContext = context;
    }

    public AccessAdapter(AccessFragment context, int resource, List<Access> items) {
        super(context.getActivity(), resource, items);
        mContext = context;
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

            if (open != null) {
                open.setTag(a);
                open.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Access a = (Access) v.getTag();
                        Library.getInstance().openAccess(mContext, a);
                    }
                });
            }

            if (close != null) {
                close.setTag(a);
                close.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Access a = (Access) v.getTag();
                        Library.getInstance().closeAccess(mContext, a);
                    }
                });
            }
        }
        return v;
    }


}
