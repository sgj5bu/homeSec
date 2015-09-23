package com.rwidman.homesec.Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rwidman.homesec.Model.Person;
import com.rwidman.homesec.R;

import java.util.List;

/**
 * Created by J.Ringler on 23.09.2015.
 */
public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PersonAdapter(Context context, int resource, List<Person> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.personitem, null);
        }

        Person p = getItem(position);

        if (p != null) {
            TextView name = (TextView) v.findViewById(R.id.person_name);
            Button reset = (Button) v.findViewById(R.id.person_button_reset);

            if (name != null) {
                name.setText(p.getName());
            }

            if (reset != null) {
                reset.setTag(p);
                reset.setOnClickListener(new OnResetClickListener());
            }
        }

        return v;
    }

    private class OnResetClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Person p = (Person) v.getTag();
        }
    }
}
