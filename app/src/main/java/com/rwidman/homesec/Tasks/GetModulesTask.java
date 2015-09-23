package com.rwidman.homesec.Tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rwidman.homesec.Fragments.ModulFragment;
import com.rwidman.homesec.Model.Modul;

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

public class GetModulesTask extends AsyncTask<Void, Void, List<Modul>> {

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
    protected List<Modul> doInBackground(Void... params) {

        mContext.showProgress(true);
        mAdapter.clear();
        Log.d("ModulesTask", "Starting at Port: " + mPort);
        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            bw.write("1#gateway#remote#GET_MODULES");
            bw.flush();

            Log.d("ModulesTask", "Written: GET_MODULES");

            String answer = br.readLine();
            String jsonString= answer.split("#")[2];

            JSONObject modules = new JSONArray(jsonString).getJSONObject(0);
            Iterator<String> modulesIterator = modules.keys();
            for (String module = modulesIterator.next(); modulesIterator.hasNext(); module = modulesIterator.next()) {
                String typ = modules.getJSONObject(module).getString("Typ");
                String status= modules.getJSONObject(module).getString("Status");
                Boolean hasCamera = modules.getJSONObject(module).getBoolean("hasCamera");
                modulesList.add(new Modul(module,typ,status,hasCamera));
            }
            mAdapter.addAll(modulesList);
            return modulesList;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mAdapter.addAll(modulesList);
        return modulesList;
    }

    @Override
    protected void onPostExecute(List<Modul> result) {
        super.onPostExecute(result);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCancelled() {
        mContext.showProgress(false);
    }
}
