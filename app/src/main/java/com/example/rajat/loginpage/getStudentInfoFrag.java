package com.example.rajat.loginpage;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class getStudentInfoFrag extends Fragment{

    private final String DEFAULT="N/A";

    TextView fname,lname,addr,phno,cls,div;
    String fnames,lnames,addrs,phnos,clss,divs,res;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_get_student_info,container,false);

//        parseJson();




        fname=(TextView) view.findViewById(R.id.fname);


        lname=(TextView) view.findViewById(R.id.lname);


        addr=(TextView) view.findViewById(R.id.addr);


        phno=(TextView) view.findViewById(R.id.phno);


        cls=(TextView) view.findViewById(R.id.cls);


        div=(TextView) view.findViewById(R.id.div);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo",getActivity().MODE_PRIVATE);
        fnames = sharedPreferences.getString("fname",DEFAULT);
        lnames = sharedPreferences.getString("lname",DEFAULT);
        addrs = sharedPreferences.getString("addr",DEFAULT);
        phnos = sharedPreferences.getString("phno",DEFAULT);
        clss = sharedPreferences.getString("cls",DEFAULT);
        divs = sharedPreferences.getString("div",DEFAULT);

        fname.setText(fnames);
        lname.setText(lnames);
        addr.setText(addrs);
        phno.setText(phnos);
        cls.setText(clss);
        div.setText(divs);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /*public void parseJson()
    {

        try {
            JSONObject respObj = new JSONObject(res);
            fnames = (respObj.getString("fname"));

            lnames = (respObj.getString("lname"));


            addrs = (respObj.getString("addr"));


            phnos= (respObj.getString("phno"));


            clss = (respObj.getString("cls"));


            divs = (respObj.getString("div"));
        }catch(Exception e){e.printStackTrace();}
    }*/

}
