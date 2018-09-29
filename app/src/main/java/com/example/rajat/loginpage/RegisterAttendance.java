package com.example.rajat.loginpage;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RegisterAttendance extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {

        String dbname;
        String cls = params[0];
        String div = params[1];
        String subCode = params[2];
        String result;

        ConnectionManager connectionManager=new ConnectionManager();
        HttpURLConnection httpURLConnection;

        dbname = cls+div+"attendance";
        String url = "http://192.168.1.4:8080/JSPSample/RegisterAttendace";

        try{

            httpURLConnection = connectionManager.getConnection(url);

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("cls",cls);
            jsonObject.accumulate("div",div);
            jsonObject.accumulate("dbname",dbname);
            jsonObject.accumulate("subCode",subCode);

            connectionManager.sendRequest(jsonObject,httpURLConnection);

            result = connectionManager.getResponse(httpURLConnection);


            httpURLConnection.disconnect();

            return result;

        }catch(Exception e){e.printStackTrace();}

        return null;
    }
}
