package com.example.rajat.loginpage;

import android.Manifest;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;

public class AttendFrag extends Fragment implements BeaconConsumer, NetworkResponseListener, RangeNotifier {

    private BeaconManager mBeaconManager;

    Collection<Beacon> b1;
    Context context;

    TextView subjectTV, teacherTV;
    Button button;
    FloatingActionButton fab;

    boolean isPressed = true;

    BluetoothAdapter bluetoothAdapter;

    int flag=0;

    int counter=0;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1234);

        mBeaconManager = BeaconManager.getInstanceForApplication(getActivity());

        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));

        mBeaconManager.setEnableScheduledScanJobs(true);

        mBeaconManager.bind(this);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attend, container, false);

        return view;
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        subjectTV = getActivity().findViewById(R.id.subject);
        teacherTV = getActivity().findViewById(R.id.teacher);
        button = getActivity().findViewById(R.id.attend);
        fab = getActivity().findViewById(R.id.fab);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!bluetoothAdapter.isEnabled()) {
                    enableBluetooth();
                }

                else {
                    fabStartScanning();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendBtnClicked();
            }
        });
    }

    public void attendBtnClicked()
    {

    }

    public void enableBluetooth() {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.cordLayout), "Turn Bluetooth ON?",Snackbar.LENGTH_LONG).setAction("ON", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetoothAdapter.enable()) {
                    Toast.makeText(getActivity(),"Bluetooth Enabled",Toast.LENGTH_SHORT);
                }
            }
        });
        snackbar.show();
    }

    public void fabStartScanning()
    {
        if(isPressed) {
            fab.setImageResource(android.R.drawable.ic_media_pause);
            Toast.makeText(getActivity(),"Starting Beacon Scanning", Toast.LENGTH_SHORT).show();
            try {
                mBeaconManager.setForegroundBetweenScanPeriod(3000l);
                mBeaconManager.startRangingBeaconsInRegion(r);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        else
        {
            fab.setImageResource(android.R.drawable.ic_media_play);
            Toast.makeText(getActivity(),"Stopping Beacon Scanning", Toast.LENGTH_SHORT).show();
            try {
                mBeaconManager.stopRangingBeaconsInRegion(r);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            subjectTV.setText("N/A");
            teacherTV.setText("N/A");
            button.setEnabled(false);
            b1=null;
        }
        isPressed = !isPressed;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
        mBeaconManager.removeRangeNotifier(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeaconManager.unbind(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    GetSubject getSubject;

    Region r =new Region("myRangingUniqueId", null, null, null);

    @Override
    public void onBeaconServiceConnect() {
        Log.d("BEACON", "test");

        mBeaconManager.addRangeNotifier(this);
    }

    @Override
    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        getActivity().unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return getActivity().bindService(intent, serviceConnection, i);
    }

    @Override
    public void onSuccess(String response) {
        String subject,teacherName,namespace,instance;
        TextView subjectTV, teacherTV;
        Button button;
        Beacon b;

        subjectTV = getActivity().findViewById(R.id.subject);
        teacherTV = getActivity().findViewById(R.id.teacher);
        button = getActivity().findViewById(R.id.attend);
        try {
            JSONObject jsonObject = new JSONObject(response);
            subject=jsonObject.getString("subject");
            teacherName=jsonObject.getString("teacher_name");
            namespace=jsonObject.getString("namespace");
            instance=jsonObject.getString("instance");

            Iterator<Beacon> iterator = b1.iterator();
            while(iterator.hasNext()) {
                b = iterator.next();
                if (String.valueOf(b.getId1()).equals(namespace) && String.valueOf(b.getId2()).equals(instance)) {
                    subjectTV.setText(subject);
                    teacherTV.setText(teacherName);
                    button.setEnabled(true);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
//        if (getActivity()!=null) {
            Log.d("RESULT", "didRangeBeaconsInRegion: new beacon scan : " + counter++);

            if (beacons.size() > 0) {
                flag = 0;
                if (!beacons.equals(b1)) {
                    b1 = beacons;

                    MyTaskParams myTaskParams = new MyTaskParams("T.E.","A", "A1", beacons);
                    Log.d("RESULT", "didRangeBeaconsInRegion: before creating new object" + String.valueOf(getActivity()));
                    GetSubject getSubject = new GetSubject(getActivity());
                    getSubject.setResponseListener(AttendFrag.this);
                    Log.d("RESULT", "didRangeBeaconsInRegion: before async task");
                    getSubject.execute(myTaskParams);

                    Log.d("RESULT", "didRangeBeaconsInRegion: after async task");

                }
            }

            else {
                if (flag > 3) {
                    flag = 0;
                    subjectTV.setText("N/A");
                    teacherTV.setText("N/A");
                    button.setEnabled(false);
                    b1 = beacons;
                }
//            }
        }
    }

}
