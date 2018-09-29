package com.example.rajat.loginpage;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

public class MenuDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView uname;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ImageView avtarImageView;

    getStudentInfoFrag getStudentInfo;
//    AttendFrag attendFrag;
    Context context;

//    FragmentTransaction transaction;
    FragmentManager fm;
//    public String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer);
        context = this;

//        transaction = getFragmentManager().beginTransaction();

//        attendFrag = new AttendFrag();

        Log.d("DRAWER","onCreate of MenuDrawer called.");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.menu_drawer);
        toggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        uname = (TextView) header.findViewById(R.id.uname);

        avtarImageView = header.findViewById(R.id.avtaarIV);
        avtarImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MenuDrawer " , "onClick ");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                getStudentInfoFrag getStudentInfoFragName = new getStudentInfoFrag();
                transaction.replace(R.id.fragment_container , getStudentInfoFragName);
                transaction.commit();
                mDrawerLayout.closeDrawer(Gravity.START);
                setTitle("Profile");
            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("UserInfo",this.MODE_PRIVATE);
//        this.uname = (TextView) inflatedView.findViewById(R.id.uname);
        String fullName = sharedPreferences.getString("fname","N/A")+" "+sharedPreferences.getString("lname","N/A");
        if(fullName.contains("N/A"))
        {
            Toast.makeText(this,"Could not find User Name",Toast.LENGTH_SHORT);
        }
        else {
            uname.setText(fullName);
        }
    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
        transaction2.add(R.id.fragment_container , new getStudentInfoFrag());
        transaction2.commit();

    }

    @Override
    public void onBackPressed() {

        if(mDrawerLayout.isDrawerOpen(Gravity.START))
        {
            mDrawerLayout.closeDrawer(Gravity.START);
        }

        if(!mDrawerLayout.isDrawerOpen(Gravity.START))
        {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Exit");
            alertDialog.setMessage("Are you sure you want to exit");
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((MenuDrawer) context).finishAffinity();
//                    System.exit(0);
                }
            });
            alertDialog.create();
            alertDialog.show();
        }

        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {

            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment myFragment = null;
//        Class fragmentClass;
        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();

        switch(item.getItemId())
        {
            case R.id.attend:
                Log.d("MENU","Attend clicked");
                transaction1.replace(R.id.fragment_container ,new AttendFrag());
                break;

            case R.id.attendance:
                transaction1.replace(R.id.fragment_container,new AttendanceFragment());
                Log.d("MENU","Attendance clicked");
                break;

            case R.id.notification:
                Log.d("MENU","Notification clicked");
                break;

            case R.id.timetable:
                Log.d("MENU","TimeTable clicked");
                transaction1.replace(R.id.fragment_container ,new TimeTable());
                break;

            case R.id.logout:
                Log.d("MENU","Logout Clicked");
                SharedPreferences sharedPreferences = this.getSharedPreferences("UserInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin",false);
                editor.apply();
//                Intent intent = new Intent(this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                this.startActivity(intent);
                Toast.makeText(this,"Logged Out Successfully", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this , LoginActivity.class));
                this.finish();
                break;

            default:

                Log.d("MENU","Default Case");
        }
        transaction1.commit();
//        item.setChecked(true);
        mDrawerLayout.closeDrawer(Gravity.START);
        setTitle(item.getTitle());

        return true;
    }
}
