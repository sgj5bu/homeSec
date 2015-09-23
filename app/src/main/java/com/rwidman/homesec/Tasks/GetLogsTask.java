package com.rwidman.homesec.Tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rwidman.homesec.Fragments.LogEntryFragment;
import com.rwidman.homesec.Library.Library;
import com.rwidman.homesec.Model.LogEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetLogsTask extends AsyncTask<Void, Void, List<LogEntry>> {

    private final List<LogEntry> logsList = new ArrayList<>();
    private int mPort = -1;
    private LogEntryFragment mContext;
    private ArrayAdapter<LogEntry> mAdapter;

    public GetLogsTask(LogEntryFragment context, int port) {
        mPort= port;
        mContext = context;
        mAdapter = ((ArrayAdapter<LogEntry>) mContext.getListAdapter());
    }

    @Override
    protected void onPreExecute()
    {
        mContext.showProgress(true);
    }

    @Override
    protected List<LogEntry> doInBackground(Void... params) {

        mAdapter.clear();
        Log.d("LogEntryTask", "Starting at Port: " + mPort);
        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            bw.write(Library.makeOrder("GET_LOG"));
            bw.flush();

            String answer = br.readLine();
            String jsonString= answer.split("#")[2];

            Log.d("LogEntryTask", "recieved: " + jsonString);
            JSONObject logs = new JSONArray(jsonString).getJSONObject(0);
            Iterator<String> modulesIterator = logs.keys();
            for (String logID = modulesIterator.next(); modulesIterator.hasNext(); logID = modulesIterator.next()) {
                String modul = logs.getJSONObject(logID).getString("modul");
                String text= logs.getJSONObject(logID).getString("text");
                String time = logs.getJSONObject(logID).getString("time");
                String topic = logs.getJSONObject(logID).getString("topic");
                String eventID = logs.getJSONObject(logID).getString("eventID");


                logsList.add(new LogEntry(logID, modul, topic, time, text, eventID));
            }
            mAdapter.addAll(logsList);
            return logsList;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mAdapter.clear();
        return null;
    }

    @Override
    protected void onPostExecute(List<LogEntry> result) {
        super.onPostExecute(result);
        mAdapter.notifyDataSetChanged();
        mContext.showProgress(false);
    }

    @Override
    protected void onCancelled() {
        mContext.showProgress(false);
    }
}