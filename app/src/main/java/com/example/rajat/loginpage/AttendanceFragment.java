package com.example.rajat.loginpage;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment {

    CalendarView calendarView;
    String date;

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView = getActivity().findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String mnth = "", day = "";

                if (month < 10)
                    mnth = '0' + String.valueOf(month+1);
                else
                    mnth = String.valueOf(month+1);

                if (dayOfMonth < 10)
                    day = '0' + String.valueOf(dayOfMonth);
                else
                    day = String.valueOf(dayOfMonth);

                date = String.valueOf(year) + mnth + day;

                Bundle bundle = new Bundle();
                bundle.putString("date", date);

                LectureWiseAttendance lectureWiseAttendance = new LectureWiseAttendance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, lectureWiseAttendance);
                lectureWiseAttendance.setArguments(bundle);
                fragmentTransaction.commit();
                Log.d("CALENDAR", "onSelectedDayChange: " + year + mnth + day);
            }
        });
    }
}
