package com.rwidman.homesec.Tasks;

import android.os.AsyncTask;

import com.rwidman.homesec.Model.Modul;
import com.rwidman.homesec.Model.Profile;

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

public class GetProfilesTask extends AsyncTask<Void, Void, List<Profile>> {

    private final List<Profile> profilesList = new ArrayList<>();
    private int mPort = -1;

    public GetProfilesTask(int port) {
        mPort= port;
    }

    @Override
    protected List<Profile> doInBackground(Void... params) {

        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            bw.write("1#gateway#remote#GET_PROFILES");
            bw.flush();

            String answer = br.readLine();
            String jsonString= answer.split("#")[2];

            JSONObject profiles = new JSONArray(jsonString).getJSONObject(0);
            Iterator<String> modulesIterator = profiles.keys();
            for (String profile = modulesIterator.next(); modulesIterator.hasNext(); profile = modulesIterator.next()) {
                JSONArray activeEnumString = profiles.getJSONArray(profile);
                Boolean isactive = activeEnumString.getString(0).equals("ACTIVE");
                profilesList.add(new Profile(profile, isactive));
            }
            return profilesList;

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