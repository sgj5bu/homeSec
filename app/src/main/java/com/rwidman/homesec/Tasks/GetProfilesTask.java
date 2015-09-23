package com.rwidman.homesec.Tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rwidman.homesec.Fragments.ProfileFragment;
import com.rwidman.homesec.Library.Library;
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
    private ProfileFragment mContext;
    private ArrayAdapter<Profile> mAdapter;

    public GetProfilesTask(ProfileFragment context, int port) {
        mPort= port;
        mContext = context;
        mAdapter = ((ArrayAdapter<Profile>) mContext.getListAdapter());
    }

    @Override
    protected void onPreExecute()
    {
        mContext.showProgress(true);
    }

    @Override
    protected List<Profile> doInBackground(Void... params) {

        mAdapter.clear();
        Log.d("ProfileTask", "Starting at Port: " + mPort);
        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            bw.write(Library.makeOrder("GET_PROFILES"));
            bw.flush();

            String answer = br.readLine();
            String jsonString= answer.split("#")[2];

            Log.d("ProfilesTask", "recieved: " + jsonString);

            JSONObject profiles = new JSONArray(jsonString).getJSONObject(0);

            for (Iterator<String> profilesIterator = profiles.keys(); profilesIterator.hasNext();) {
                String profile = profilesIterator.next();
                String activeEnumString = profiles.getString(profile);
                Boolean isactive = activeEnumString.equals("ACTIVE");
                profilesList.add(new Profile(profile, isactive));
            }
            mAdapter.addAll(profilesList);
            return profilesList;

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
    protected void onPostExecute(List<Profile> result) {
        super.onPostExecute(result);
        mAdapter.notifyDataSetChanged();
        mContext.showProgress(false);
    }

    @Override
    protected void onCancelled() {
        mContext.showProgress(false);
    }
}