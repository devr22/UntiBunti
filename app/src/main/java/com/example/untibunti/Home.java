package com.example.untibunti;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String cins = "";
    TextView tvCoin, tvLocation;
    String number;

    public static final String TAG = "Home";
    private static final String fine_location = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String coarse_location = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int location_permission_request_code = 1234;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        number = firebaseAuth.getCurrentUser().getPhoneNumber();
        tvCoin = findViewById(R.id.tvCoin);
        tvLocation = findViewById(R.id.tvLocation);

        /*firebaseFirestore.collection("USERS").document(number).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Coins=documentSnapshot.getId();
            }
        });*/

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_frag);

        tvCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        getLocationPermission();
    }


    @Override
    protected void onResume() {
        super.onResume();
        firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("USERS").document(number).addSnapshotListener(new EventListener<DocumentSnapshot>() {


            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                tvCoin.setText(documentSnapshot.get("Coins").toString() );

            }
        });








    }


    public void getLocationPermission(){

        Log.d(TAG, "getLocationPermission: getting location permission");
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        if (ActivityCompat.checkSelfPermission(this, fine_location) == PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.checkSelfPermission(this, coarse_location) == PackageManager.PERMISSION_GRANTED)

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
            final Task location = fusedLocationProviderClient.getLastLocation();

            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (task.isSuccessful())
                    {
                        Log.d(TAG, "onComplete: location found");
                        Location currentLocation = (Location) task.getResult();
                        assert currentLocation != null;
                        Log.d(TAG, "onComplete: " + currentLocation.getLatitude() + "/" + currentLocation.getLongitude());

                        retrieveAndStoreLocation(currentLocation);
                    }
                    else
                    {
                        Log.d(TAG, "onComplete: Current Location is not found");
                        Toast.makeText(getApplicationContext(), "Unable to find device's current location",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }catch (Exception e){
            Log.d(TAG, "getDeviceLocation: failed to get device location / " + e.getMessage());
        }
    }

    @SuppressLint("SetTextI18n")
    private void retrieveAndStoreLocation(Location currentLocation){

        try {

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> address = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(),1);
            tvLocation.setText(address.get(0).getSubLocality() + "," + address.get(0).getLocality());

        }catch(Exception e){
            Log.d(TAG,"retrieveAndStoreLocation: " + e.getMessage());
        }


    }

}
