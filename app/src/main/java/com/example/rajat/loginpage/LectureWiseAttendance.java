package com.example.rajat.loginpage;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class LectureWiseAttendance extends Fragment implements NetworkResponseListener{

    private final String DEFAULT="N/A";
    TableLayout mTableLayout;
    LinearLayout mChartLayout;
    TextView datetv;
    String uid;
    public LectureWiseAttendance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lecture_wise_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String date,fdate;
        date=getArguments().getString("date");
        fdate=getArguments().getString("fdate");

        datetv=getActivity().findViewById(R.id.date);
        datetv.setText(fdate);

        mChartLayout = getActivity().findViewById(R.id.table_layout2);
        mChartLayout.removeAllViews();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo",getActivity().MODE_PRIVATE);
        uid = sharedPreferences.getString("uid",DEFAULT);

        GetAttendance getAttendance = new GetAttendance();
        getAttendance.setNetworkResponseListener(this);
        getAttendance.execute(uid,date);
    }

    @Override
    public void onSuccess(String response) {
        JSONArray subject,status;
        Log.d("JSON", "onSuccess: "+response);
        try {

            JSONObject jsonObject = new JSONObject(response);
            subject=jsonObject.getJSONArray("subject");
            status=jsonObject.getJSONArray("status");

            mTableLayout = new TableLayout(getActivity());
            mTableLayout.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            mTableLayout.setStretchAllColumns(true);

            for (int count = 0; count < subject.length(); count++) {
                TableRow row = new TableRow(getActivity());
                TableRow.LayoutParams rowLP = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
//                rowLP.setMargins(0,0,0,10);
                row.setPadding(0,0,0,40);
                row.setLayoutParams(rowLP);


                TextView textTV = new TextView(getActivity());
                textTV.setText(String.valueOf(subject.get(count)));

                textTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                textTV.setGravity(Gravity.CENTER);

                TextView valueTV = new TextView(getActivity());
                valueTV.setText(String.valueOf(status.get(count)));

                valueTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                valueTV.setGravity(Gravity.CENTER);

                row.addView(textTV);
                row.addView(valueTV);

                mTableLayout.addView(row);

            }
            mChartLayout.addView(mTableLayout);


        }catch(Exception e){e.printStackTrace();}
    }
}
