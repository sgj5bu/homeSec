package com.rwidman.homesec.Tasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.rwidman.homesec.Fragments.AccessFragment;
import com.rwidman.homesec.LoginActivity;
import com.rwidman.homesec.Model.Access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class CloseAccessTask extends AsyncTask<Access, Void, Void> {

    private final int TIMEOUT = 20*1000;
    private final int DELAY = 100;
    private int mPort = -1;
    private AccessFragment mContext;
    private ArrayAdapter<Access> mAdapter;

    public CloseAccessTask(AccessFragment context, int port) {
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

            //TODO: send open access order and set state of a

            a.setState("CLOSE");
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