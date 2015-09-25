package com.rwidman.homesec.Tasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rwidman.homesec.Fragments.AccessFragment;
import com.rwidman.homesec.Library.Library;
import com.rwidman.homesec.LoginActivity;
import com.rwidman.homesec.Model.Access;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class OpenAccessTask extends AsyncTask<Access, Void, Void> {

    private final int TIMEOUT = 20*1000;
    private final int DELAY = 100;
    private int mPort = -1;
    private AccessFragment mContext;
    private ArrayAdapter<Access> mAdapter;

    public OpenAccessTask(AccessFragment context, int port) {
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
    protected Void doInBackground(Access... params) {

        Access a = params[0];
        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            Thread.sleep(DELAY);
            socket.setSoTimeout(TIMEOUT);

            bw.write(Library.makeOrder("SET_ACCESS_OPEN", a.getName()));
            bw.flush();

            String statusAnswer = br.readLine();
            Log.d("ACCESSES TASK", "receiving= " + statusAnswer);
            //msgID#ACCESS_STATUS#[AccName,Status]
            String order =  statusAnswer.split("#")[1];
            String accStatus = order;
            if (!order.equals("NOT_ALLOWED")){
                JSONArray parameter = new JSONArray(statusAnswer.split("#")[2]);
                JSONArray nameStatus = parameter.getJSONArray(0);
                Log.d("ACCESSES TASK", "name-status= " + nameStatus);
                String accName = nameStatus.getString(0);
                Log.d("ACCESSES TASK", "accName= " + accName);
                accStatus = nameStatus.getString(1);
            }

            //TODO: send open access order and set state of a
            if (accStatus.equals("-43")) {
                accStatus = "already open";
            } else if (accStatus.equals("-41")){
                accStatus = "open not supported.";
            } else if (accStatus.equals("0")){
                accStatus = "open successful";
            }
            a.setState(accStatus);

        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent(mContext.getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        mAdapter.notifyDataSetChanged();
        mContext.showProgress(false);
    }

    @Override
    protected void onCancelled() {
        mContext.showProgress(false);
    }
}