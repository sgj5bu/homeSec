package com.rwidman.homesec.Tasks;

import android.os.AsyncTask;

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

/**
 * Created by xxxx on 23.09.2015.
 */
public class GetPersonsTask extends AsyncTask<Void, Void, List<Modul>> {

    private final List<Modul> personsList = new ArrayList<>();
    private int mPort = -1;

    public GetPersonsTask (int port) {
        mPort= port;
    }

    @Override
    protected List<Modul> doInBackground(Void... params) {

        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            bw.write("GET_PERSONS");
            bw.flush();

            String answer = br.readLine();
            String jsonString= answer.split("#")[2];

            JSONArray names = new JSONArray(jsonString);
            for(int i = 0; i < names.length(); i++)
            {
                String name = names.getString(i);
            }

            return personsList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}