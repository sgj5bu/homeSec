package com.rwidman.homesec.Tasks;

/**
 * Created by xxxx on 23.09.2015.
 */

import android.os.AsyncTask;

import com.rwidman.homesec.Cache.Cache;
import com.rwidman.homesec.Model.Access;

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

public class GetAccessesTask extends AsyncTask<Void, Void, List<Access>> {

    //private final List<String> accessesNameList = new ArrayList<>();
    private final List<Access> accessesNameStatusList = new ArrayList<>();
    private int mPort = -1;

    public GetAccessesTask(int port) {
        mPort= port;
    }

    @Override
    protected List<Access> doInBackground(Void... params) {

        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            bw.write(Cache.makeOrder("GET_ACCESSES"));
                    bw.flush();
            //get AccessesNames
            String answer = br.readLine();
            String jsonString= answer.split("#")[2];

            JSONArray accessNames = new JSONArray(jsonString);
            for(int i = 0; i < accessNames.length(); i++)
            {
                String name = accessNames.getString(i);
                //accessesNameList.add(name);
                //request state for each state
                bw.write(Cache.makeOrder("GET_ACCESS_STATUS"));
                bw.flush();
            }
            //read incoming access states
            for (int i = 0; i < accessNames.length();i++){
                String statusAnswer = br.readLine();
                //msgID#ACCESS_STATUS#[AccName,Status]
                JSONArray nameStatus = new JSONArray(statusAnswer.split("#")[2]);
                String accName = nameStatus.getString(0);
                String accStatus = nameStatus.getString(1);
                accessesNameStatusList.add(new Access(accName,accStatus));
            }
            return accessesNameStatusList;



        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}