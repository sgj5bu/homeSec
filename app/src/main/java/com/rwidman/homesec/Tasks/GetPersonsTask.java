package com.rwidman.homesec.Tasks;

import android.os.AsyncTask;

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

    private final List<Person> personsList = new ArrayList<>();
    private int mPort = -1;

    public GetPersonsTask (int port) {
        mPort= port;
    }

    @Override
    protected List<Person> doInBackground(Void... params) {

        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            bw.write("1#gateway#remote#GET_PERSONS");
            bw.flush();

            String answer = br.readLine();
            String jsonString= answer.split("#")[2];

            JSONArray names = new JSONArray(jsonString);
            for(int i = 0; i < names.length(); i++)
            {
                String name = names.getString(i);
                personsList.add(new Person(name));
            }

            return personsList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}