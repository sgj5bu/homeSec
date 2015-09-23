package com.rwidman.homesec.Tasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rwidman.homesec.Fragments.ModulFragment;
import com.rwidman.homesec.Library.Library;
import com.rwidman.homesec.LoginActivity;
import com.rwidman.homesec.Model.Modul;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetModulesTask extends AsyncTask<Void, Void, List<Modul>> {

    private final int TIMEOUT = 20*1000;
    private final List<Modul> modulesList = new ArrayList<>();
    private int mPort = -1;
    private ModulFragment mContext;
    private ArrayAdapter<Modul> mAdapter;

    public GetModulesTask(ModulFragment context, int port) {
            mPort= port;
            mContext = context;
            mAdapter = ((ArrayAdapter<Modul>) mContext.getListAdapter());
        }

    @Override
    protected void onPreExecute()
    {
        mContext.showProgress(true);
    }

    @Override
    protected List<Modul> doInBackground(Void... params) {

        mContext.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.clear();
            }
        });
        Log.d("ModulesTask", "Starting at Port: " + mPort);
        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            socket.setSoTimeout(TIMEOUT);
            bw.write(Library.makeOrder("GET_MODULES"));
            bw.flush();

            Log.d("ModulesTask", "Written: GET_MODULES");

            String answer = br.readLine();
            String jsonString= answer.split("#")[2];


            Log.d("ModulesTask", "recieved: " + jsonString);
            JSONObject modules = new JSONArray(jsonString).getJSONObject(0);

            for (Iterator<String> modulesIterator = modules.keys(); modulesIterator.hasNext();) {
                String module = modulesIterator.next();
                String typ = modules.getJSONObject(module).getString("Typ");
                String status= modules.getJSONObject(module).getString("Status");
                Boolean hasCamera = modules.getJSONObject(module).getBoolean("hasCamera");
                modulesList.add(new Modul(module,typ,status,hasCamera));
            }
            mContext.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.addAll(modulesList);
                }
            });

            Log.d("ModulesTask", "list recieved");
            return modulesList;

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
        return modulesList;
    }

    @Override
    protected void onPostExecute(List<Modul> result) {
        super.onPostExecute(result);
        mAdapter.notifyDataSetChanged();
        mContext.showProgress(false);
    }

    @Override
    protected void onCancelled() {
        mContext.showProgress(false);
    }
}
