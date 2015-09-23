package com.rwidman.homesec.Tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rwidman.homesec.Fragments.AccessFragment;
import com.rwidman.homesec.Library.Library;
import com.rwidman.homesec.Model.Access;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class GetAccessesTask extends AsyncTask<Void, Void, List<Access>> {

    //private final List<String> accessesNameList = new ArrayList<>();
    private final List<Access> accessesNameStatusList = new ArrayList<>();
    private int mPort = -1;
    private AccessFragment mContext;
    private ArrayAdapter<Access> mAdapter;

    public GetAccessesTask(AccessFragment context, int port) {
        mPort= port;
        mContext = context;
        mAdapter = ((ArrayAdapter<Access>) mContext.getListAdapter());
    }

    @Override
    protected void onPreExecute()
    {
        mContext.showProgress(true);
    }

    @Override
    protected List<Access> doInBackground(Void... params) {

        mAdapter.clear();
        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            bw.write(Library.makeOrder("GET_ACCESSES"));
            bw.flush();
            //get AccessesNames
            String answer = br.readLine();
            String jsonString= answer.split("#")[2];

            JSONArray accessNames = new JSONArray(jsonString);
            Log.d("ACCESSES TAKS", "received: " + jsonString);
            for(int i = 0; i < accessNames.length(); i++)
            {
                String name = accessNames.getString(i);
                //accessesNameList.add(name);
                //request state for each state
                Log.d("ACCESSES TASK","requesting= " + name);
                bw.write(Library.makeOrder("GET_ACCESS_STATUS",name));
                bw.flush();
            }
            //read incoming access states
            for (int i = 0; i < accessNames.length();i++){
                String statusAnswer = br.readLine();
                Log.d("ACCESSES TASK","receiving= " + statusAnswer);
                //msgID#ACCESS_STATUS#[AccName,Status]
                JSONArray nameStatus = new JSONArray(statusAnswer.split("#")[2]);
                Log.d("ACCESSES TASK","name-status= " + nameStatus);
                String accName = nameStatus.getString(0);
                Log.d("ACCESSES TASK","accName= " + accName);
                String accStatus = nameStatus.getString(1);
                Log.d("ACCESSES TASK","accStatus= " + accStatus + "now creating access.");
                accessesNameStatusList.add(new Access(accName, accStatus));
            }

            mAdapter.addAll(accessesNameStatusList);
            return accessesNameStatusList;



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
    protected void onPostExecute(List<Access> result) {
        super.onPostExecute(result);
        mAdapter.notifyDataSetChanged();
        mContext.showProgress(false);
    }

    @Override
    protected void onCancelled() {
        mContext.showProgress(false);
    }
}