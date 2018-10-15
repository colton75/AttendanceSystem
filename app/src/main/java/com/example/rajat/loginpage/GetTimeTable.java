package com.example.rajat.loginpage;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetTimeTable extends AsyncTask<String, Void, String> {

    Context context;
    private String cls,batch,day,div;

    NetworkResponseListener networkResponseListener;

    public GetTimeTable(Context context)
    {
        this.context=context;
    }

    @Override
    protected String doInBackground(String... params) {
        ConnectionManager connectionManager=new ConnectionManager();
        HttpURLConnection httpURLConnection;
        String url = "http://192.168.1.111:8080/JSPSample/ReturnTimeTable";
        String result="";
        try{

            httpURLConnection = connectionManager.getConnection(url);

            cls = params[0];
            div = params[1];
            batch = params[2];
            day = params[3];

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("cls",cls);
            jsonObject.accumulate("div",div);
            jsonObject.accumulate("batch",batch);
            jsonObject.accumulate("day",day);

            connectionManager.sendRequest(jsonObject,httpURLConnection);

            result = connectionManager.getResponse(httpURLConnection);

            httpURLConnection.disconnect();

            return result;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

    public void setNetworkResponseListener(NetworkResponseListener networkResponseListener)
    {
        this.networkResponseListener = networkResponseListener;
    }

    @Override
    protected void onPostExecute(String result) {


        networkResponseListener.onSuccess(result);
    }
}
