package com.example.rajat.loginpage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTable extends Fragment implements AdapterView.OnItemSelectedListener, NetworkResponseListener{


    public TimeTable() {
        // Required empty public constructor
    }

    int day;
    LinearLayout mChartLayout;
    TableLayout mTableLayout;
    String sub;
    String items[] = {"Monday","Tuesday","Wednesday","Thursday","Friday"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);
        // Inflate the layout for this fragment

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChartLayout = getActivity().findViewById(R.id.chart_layout);
        mChartLayout.removeAllViews();

        Date now = new Date();

        Spinner dropdown = getActivity().findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,items);

        dropdown.setAdapter(adapter);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        day=calendar.get(Calendar.DAY_OF_WEEK)-2;
        if(day>=0 && day<=4)
        {
            dropdown.setSelection(day);
        }
        else
        {
            dropdown.setSelection(0);
        }

        dropdown.setOnItemSelectedListener(this);
    }

    public void displayChartTable(JSONArray time, JSONArray subject, JSONArray teacher)
    {
        try {
            mTableLayout = new TableLayout(getActivity());
            mTableLayout.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            mTableLayout.setStretchAllColumns(true);

            for (int count = 0; count < 7; count++) {
                TableRow row = new TableRow(getActivity());
                TableRow.LayoutParams rowLP = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
//                rowLP.setMargins(0,0,0,10);
                row.setPadding(0,0,0,40);
                row.setLayoutParams(rowLP);


                TextView textTV = new TextView(getActivity());
                textTV.setText(String.valueOf(time.get(count)));

                textTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                textTV.setGravity(Gravity.CENTER);

                TextView valueTV = new TextView(getActivity());
                sub = String.valueOf(subject.get(count))+"\n("+String.valueOf(teacher.get(count))+")";
                valueTV.setText(sub);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        GetTimeTable getTimeTable = new GetTimeTable(getActivity());
        getTimeTable.setNetworkResponseListener(this);
       switch (position)
       {
           case 0:
                getTimeTable.execute("TE-A","A2","1");
//               Toast.makeText(getActivity(),"You Selected "+String.valueOf(parent.getSelectedItem()),Toast.LENGTH_SHORT).show();
               break;

           case 1:
               getTimeTable.execute("TE-A","A2","2");
//               Toast.makeText(getActivity(),"You Selected "+String.valueOf(parent.getSelectedItem()),Toast.LENGTH_SHORT).show();
               break;

           case 2:
               getTimeTable.execute("TE-A","A2","3");
//               Toast.makeText(getActivity(),"You Selected "+String.valueOf(parent.getSelectedItem()),Toast.LENGTH_SHORT).show();
               break;

           case 3:
               getTimeTable.execute("TE-A","A2","4");
//               Toast.makeText(getActivity(),"You Selected "+String.valueOf(parent.getSelectedItem()),Toast.LENGTH_SHORT).show();
               break;

           case 4:
               getTimeTable.execute("TE-A","A2","5");
//               Toast.makeText(getActivity(),"You Selected "+String.valueOf(parent.getSelectedItem()),Toast.LENGTH_SHORT).show();
               break;

           default:
//               Toast.makeText(getActivity(),"Wrong Choice",Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSuccess(String response) {
        JSONArray time;
        JSONArray subject;
        JSONArray teacher;

        try {
            JSONObject jsonObject = new JSONObject(response);

            time=jsonObject.getJSONArray("time");
            subject=jsonObject.getJSONArray("subject");
            teacher=jsonObject.getJSONArray("teachers");

            //Code for createing table


            displayChartTable(time,subject,teacher);

        }catch(Exception e){e.printStackTrace();}
    }
}
