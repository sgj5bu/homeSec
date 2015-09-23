package com.rwidman.homesec.Tasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rwidman.homesec.Fragments.PersonFragment;
import com.rwidman.homesec.Library.Library;
import com.rwidman.homesec.LoginActivity;
import com.rwidman.homesec.Model.Person;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GetPersonsTask extends AsyncTask<Void, Void, List<Person>> {

    private final int TIMEOUT = 20*1000;
    private final List<Person> personsList = new ArrayList<>();
    private int mPort = -1;
    private PersonFragment mContext;
    private ArrayAdapter<Person> mAdapter;

    public GetPersonsTask (PersonFragment context, int port) {
        mContext = context;
        mPort= port;
        mAdapter = ((ArrayAdapter<Person>) mContext.getListAdapter());
    }

    @Override
    protected void onPreExecute()
    {
        mContext.showProgress(true);
    }

    @Override
    protected List<Person> doInBackground(Void... params) {


        mContext.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.clear();
            }
        });
        Log.d("PersonTask", "Starting at Port: " + mPort);
        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            socket.setSoTimeout(TIMEOUT);
            bw.write(Library.makeOrder("GET_PERSONS"));
            bw.flush();

            String answer = br.readLine();
            String jsonString= answer.split("#")[2];

            JSONArray parameters = new JSONArray(jsonString);
            JSONArray names = parameters.getJSONArray(0);
            for(int i = 0; i < names.length(); i++)
            {
                String name = names.getString(i);
                personsList.add(new Person(name));
            }

            mContext.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.addAll(personsList);
                }
            });

            return personsList;

        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent(mContext.getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
        }

        mContext.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.clear();
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(List<Person> result) {
        super.onPostExecute(result);
        mAdapter.notifyDataSetChanged();
        mContext.showProgress(false);
    }

    @Override
    protected void onCancelled() {
        mContext.showProgress(false);
    }
}