package com.example.adam.androidtestapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {

    private LocationManager cLocationManager;
    private LocationListener iLocationListener;
    private final int LOCATION_PERMISSION = 555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        cLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        iLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                EditText gpsLogEditText  = (EditText)findViewById(R.id.gpslog);
                StringBuilder szBuilder = new StringBuilder();
                szBuilder.append("Current Location:").append(location.getLongitude()).append("|").append(location.getLatitude()).append("\n");
                gpsLogEditText.setText(szBuilder.toString()+gpsLogEditText.getText());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //requestPermissions(new String[]{
            //        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, LOCATION_PERMISSION);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, LOCATION_PERMISSION);
            return;
        }

        cLocationManager.requestLocationUpdates("gps", 1000, 0, iLocationListener);
    }
}
