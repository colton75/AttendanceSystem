package com.example.rajat.loginpage;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionManager {

    public HttpURLConnection getConnection(String connectionUrl)
    {


//        if (type.equals("login")) {
        try {
            URL url = new URL(connectionUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-type", "application/json;charset=utf-8");

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            return httpURLConnection;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendRequest(JSONObject jsonObject, HttpURLConnection httpURLConnection)
    {
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            bufferedWriter.write(jsonObject.toString());

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getResponse(HttpURLConnection httpURLConnection)
    {
        String result = "";
        String line = "";
        try {

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            bufferedReader.close();
            inputStream.close();

            return result;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
