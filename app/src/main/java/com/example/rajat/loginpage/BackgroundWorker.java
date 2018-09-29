package com.example.rajat.loginpage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.graphics.Color.RED;

public class BackgroundWorker extends AsyncTask<String, Void, String> {

    Context context;
    //private String url;
    NetworkResponseListener networkResponseListener;

    BackgroundWorker(Context ctx) {
        //this.url = url;
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        String type = params[0];
        String login_url = "http://192.168.1.4:8080/JSPSample/HelloWorld";
        if (type.equals("login")) {
            try {
                String user_id = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-type","application/json;charset=utf-8");

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("user_id",user_id);
                jsonObject.accumulate("password",password);

                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                //String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(jsonObject.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

//                Log.d("Background " , "Name : " + name);
                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;


    }

    public void setNetworkResponseListener(NetworkResponseListener networkResponseListener)
    {
        this.networkResponseListener = networkResponseListener;
    }


        @Override
        protected void onProgressUpdate (Void...values){
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute (String result){
            //Log.d("Servlet", result);
            networkResponseListener.onSuccess(result);
        }

        @Override
        protected void onPreExecute () {
            super.onPreExecute();
        }
    }
