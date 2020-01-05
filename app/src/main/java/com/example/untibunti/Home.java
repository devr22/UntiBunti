package com.example.untibunti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Home extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String cins = "";
    TextView tvCoin;
    String number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        number = firebaseAuth.getCurrentUser().getPhoneNumber();
          tvCoin = findViewById(R.id.tvCoin);



        /*firebaseFirestore.collection("USERS").document(number).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Coins=documentSnapshot.getId();

            }
        });*/


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_frag);

        

        NavigationUI.setupWithNavController(bottomNavigationView, navController);



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
}
