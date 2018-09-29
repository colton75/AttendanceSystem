package com.example.rajat.loginpage;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import static android.support.constraint.Constraints.TAG;

public class GetSubject extends AsyncTask<MyTaskParams,Void,String> {

    private Collection<Beacon> beacon;
    private NetworkResponseListener networkResponseListener;
    private Context context;




    public GetSubject(Context context)
    {
        this.context = context;
    }

    @Override
    protected String doInBackground(MyTaskParams... myTaskParams) {
        ConnectionManager connectionManager=new ConnectionManager();
        HttpURLConnection httpURLConnection;
        String url = "http://192.168.1.4:8080/JSPSample/GetSubject1";
        String result="";
        try {
                httpURLConnection = connectionManager.getConnection(url);

                String cls = myTaskParams[0].cls;
                String div = myTaskParams[0].div;
                String batch = myTaskParams[0].batch;
                beacon = myTaskParams[0].beacon;


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("cls",cls);
                jsonObject.accumulate("div",div);
                jsonObject.accumulate("batch",batch);

                connectionManager.sendRequest(jsonObject,httpURLConnection);

                result = connectionManager.getResponse(httpURLConnection);


                httpURLConnection.disconnect();

//                Log.d("Background " , "Name : " + name);

                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
        return null;
    }

    public void setResponseListener(NetworkResponseListener networkResponseListener)
    {
        this.networkResponseListener = networkResponseListener;
    }

    @Override
    protected void onPostExecute(String result) {

        networkResponseListener.onSuccess(result);
    }
}
