package com.rwidman.homesec.Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rwidman.homesec.Model.LogEntry;
import com.rwidman.homesec.R;

import java.util.List;

/**
 * Created by J.Ringler on 23.09.2015.
 */
public class LogEntryAdapter extends ArrayAdapter<LogEntry> {

    public LogEntryAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public LogEntryAdapter(Context context, int resource, List<LogEntry> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.logentryitem, null);
        }

        LogEntry l = getItem(position);

        if (l != null) {
            TextView topic = (TextView) v.findViewById(R.id.logentry_topic);
            TextView modul = (TextView) v.findViewById(R.id.logentry_modul);
            Button photo = (Button) v.findViewById(R.id.logentry_button_photo);
            Button details = (Button) v.findViewById(R.id.logentry_button_details);

            if(topic != null)
            {
                topic.setText(l.getTopic());
            }

            if(modul != null)
            {
                modul.setText(l.getModuleName());
            }

            if(photo != null)
            {
                photo.setTag(l);
                photo.setOnClickListener(new OnPhotoClickListener());
            }

            if(details != null)
            {
                details.setTag(l);
                details.setOnClickListener(new OnDetailsClickListener());
            }

        }
        return v;
    }

    private class OnPhotoClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            LogEntry l = (LogEntry) v.getTag();
        }
    }

    private class OnDetailsClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            LogEntry l = (LogEntry) v.getTag();
        }
    }
}
