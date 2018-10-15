package com.example.rajat.loginpage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;

import static android.graphics.Color.RED;

public class LoginActivity extends AppCompatActivity implements NetworkResponseListener {
    EditText unameET, passET;
    public TextView alertText;
    boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        isLogin=sharedPreferences.getBoolean("isLogin",false);
        if(isLogin)
        {
            Intent intent = new Intent(this, MenuDrawer.class);
            this.startActivity(intent);
        }

        unameET  = (EditText)findViewById(R.id.useridET);
        passET = (EditText)findViewById(R.id.passwordET);
        alertText = findViewById(R.id.alertTV);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        passET.setText("");
        alertText.setText("");
    }



    public void onLogin(View view)
    {
        alertText.setText("");
        String username = unameET.getText().toString();
        String password = passET.getText().toString();
        if(username.equals("")||password.equals("")) {
            if (username.equals(""))
                unameET.setError("User ID cannot be empty");
            if (password.equals(""))
                passET.setError("Password cannot be empty");
        }
        else {
            String type = "login";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.setNetworkResponseListener(this);
            backgroundWorker.execute(type, username, password);
        }
    }

    @Override
    public void onSuccess(String response) {
        String fname,lname,addr,phno,cls,div,uid;
        JSONObject obj = null;
        try {

            obj = new JSONObject(response);

            String status = obj.getString("result");

            if(status.equals("success")) {
//                    Toast.makeText(context, "You Logged in Successfully", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(context, scanBeacons.class);
//                    intent.putExtra("uid",uid);
//                    context.startActivity(intent);

                uid = obj.getString("uid");
                fname = obj.getString("fname");
                lname = obj.getString("lname");
                addr = obj.getString("addr");
                phno = obj.getString("phno");
                cls = obj.getString("cls");
                div = obj.getString("div");

                SharedPreferences sharedPreferences = this.getSharedPreferences("UserInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("uid",uid);
                editor.putString("fname",fname);
                editor.putString("lname",lname);
                editor.putString("addr",addr);
                editor.putString("phno",phno);
                editor.putString("cls",cls);
                editor.putString("div",div);
                editor.putBoolean("isLogin",true);
                editor.apply();

                Intent intent = new Intent(this, MenuDrawer.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
            }

            else
            {
                TextView txtView = findViewById(R.id.alertTV);
                txtView.setTextColor(RED);
                txtView.setText("User ID or Password is Incorrect!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
