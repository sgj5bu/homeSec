package com.rwidman.homesec.Tasks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rwidman.homesec.Fragments.AccessFragment;
import com.rwidman.homesec.Fragments.ModulFragment;
import com.rwidman.homesec.Library.Library;
import com.rwidman.homesec.LoginActivity;
import com.rwidman.homesec.Model.Access;
import com.rwidman.homesec.Model.Modul;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TakePhotoTask extends AsyncTask<Modul, Void, Void> {

    private final int TIMEOUT = 20*1000;
    private final int DELAY = 100;
    private int mPort = -1;
    private ModulFragment mContext;
    private ArrayAdapter<Modul> mAdapter;

    public TakePhotoTask(ModulFragment context, int port) {
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
    protected Void doInBackground(Modul... params) {

        Modul m = params[0];
        try (   Socket socket = new Socket("10.8.0.1", mPort);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            Thread.sleep(DELAY);
            socket.setSoTimeout(TIMEOUT);

            bw.write(Library.makeOrder("GET_MODULE_PICTURE", m.getName()));
            bw.flush();

            String statusAnswer = br.readLine();
            String order =  statusAnswer.split("#")[1];


            int pictureCount = Integer.parseInt(order);

            Log.d("Module Photo Task","awaiting " + pictureCount + "pictures");
            for (int i = 0; i<pictureCount; i++){

                String  pictureMsg = br.readLine();
                String pictureData =  pictureMsg.split("#")[1];

                Log.d("Module Photo Task","Got #"+i);
                byte imageData[] = pictureData.getBytes();

                Bitmap bmp = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

               /* Intent pickIntent = new Intent();
                pickIntent.setType("image/*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);
                mContext.startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), 0);
                */
                }


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