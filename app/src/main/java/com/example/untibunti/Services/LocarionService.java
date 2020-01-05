package com.example.untibunti.Services;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.type.LatLng;

public class LocarionService extends AppCompatActivity {

    public static final String TAG = "LocationService";
    private static final String fine_location = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String coarse_location = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int location_permission_request_code = 1234;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private void getLocationPermission(){

        Log.d(TAG, "getLocationPermission: getting location permission");
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), fine_location) == PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), coarse_location) == PackageManager.PERMISSION_GRANTED)

            {
                getDeviceLocation();
            }
            else
            {
                ActivityCompat.requestPermissions(this, permissions, location_permission_request_code);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this, permissions, location_permission_request_code);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d(TAG, "onRequestPermissionsResult: called");

        switch (requestCode)
        {
            case location_permission_request_code:{

                if (grantResults.length > 0)
                {
                    for (int i=0; i < grantResults.length; i++)
                    {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        {
                            Log.d(TAG, "onRequestPermissionsResult: Permission failed");
                            return;
                        }
                    }

                    Log.d(TAG, "onRequestPermissionsResult: Permission granted");
                    getDeviceLocation();
                }
            }
        }
    }

    private void getDeviceLocation(){

        Log.d(TAG, "getDeviceLocation: fetching device location");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            Task location = fusedLocationProviderClient.getLastLocation();

            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (task.isSuccessful())
                    {
                        Log.d(TAG, "onComplete: location found");
                        Location currentLocation = (Location) task.getResult();
                    }
                    else
                    {
                        Log.d(TAG, "onComplete: Current Location is not found");
                        Toast.makeText(getApplicationContext(), "Unable to find device's current location", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }catch (Exception e){
            Log.d(TAG, "getDeviceLocation: failed to get device location / " + e.getMessage());
        }
    }

}










