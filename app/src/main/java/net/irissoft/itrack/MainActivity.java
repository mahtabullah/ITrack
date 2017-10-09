package net.irissoft.itrack;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    GPSTracker gps;
    Button btnShowLocation, stopgetlocation, btnshowalldata;


    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


    long gpstime = 1 * 60 * 1000;
    Handler hd = new Handler();


    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hd.postDelayed(runnable, 0);

        btnShowLocation = (Button) findViewById(R.id.getlocation);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //  getGpsLocation();
                Toast.makeText(getApplicationContext(), "GPS Start", Toast.LENGTH_SHORT).show();

                hd.postDelayed(runnable, 0);
            }
        });


        ////////GPS Stop Button
        stopgetlocation = (Button) findViewById(R.id.stopgetlocation);
        stopgetlocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                hd.removeCallbacks(runnable);
                Toast.makeText(getApplicationContext(), "GPS Stoped", Toast.LENGTH_SHORT).show();
            }
        });

        btnshowalldata = (Button) findViewById(R.id.btnshowalldata);
        btnshowalldata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(MainActivity.this, ShowAllDataActivity.class);
                startActivity(i);

                Toast.makeText(getApplicationContext(), "Going Second Page", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void getGpsLocation() {

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != MockPackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
                // If any permission above not allowed by user, this condition will
                // execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        gps = new GPSTracker(MainActivity.this);
        Date date = new Date();
        // check if GPS enabled
        if (gps.canGetLocation()) {

            double _latitude = gps.getLatitude();
            double _longitude = gps.getLongitude();

            // Viewtxt += "Lat:" + _latitude + " | Long: " + _longitude +" | Date: " + dateFormat.format(date).toString() + "\n";
            InsertIntoDatabase(1,Double.toString(_latitude), Double.toString(_longitude), dateFormat.format(date));


        } else {
            gps.showSettingsAlert();
        }

    }

    void InsertIntoDatabase(int userid,String lat, String lon, String time) {

        Database DBin = new Database(getApplicationContext());

        DBin.allLocation(new Location(userid,lat, lon, time));

        Toast.makeText(getApplicationContext(), " Data Save Successfullly", Toast.LENGTH_SHORT).show();


    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            getGpsLocation();
            hd.postDelayed(this, gpstime);
        }
    };


}
