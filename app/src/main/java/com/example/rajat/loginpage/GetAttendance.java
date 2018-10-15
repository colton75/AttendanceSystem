package com.example.rajat.loginpage;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.net.HttpURLConnection;

public class GetAttendance extends AsyncTask<String,Void,String> {

    NetworkResponseListener networkResponseListener;
    @Override
    protected String doInBackground(String... params) {
        String result;

        ConnectionManager connectionManager=new ConnectionManager();
        HttpURLConnection httpURLConnection;

        String url = "http://192.168.1.111:8080/JSPSample/ReturnAttendance";
        String uid=params[0];
        String date=params[1];
        try{

            httpURLConnection = connectionManager.getConnection(url);

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("uid",uid);
            jsonObject.accumulate("date",date);

            connectionManager.sendRequest(jsonObject,httpURLConnection);

            result = connectionManager.getResponse(httpURLConnection);


            httpURLConnection.disconnect();

            return result;

        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    public void setNetworkResponseListener(NetworkResponseListener networkResponseListener) {
        this.networkResponseListener = networkResponseListener;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("JSON", "onPostExecute: "+result);
        networkResponseListener.onSuccess(result);
    }
}
