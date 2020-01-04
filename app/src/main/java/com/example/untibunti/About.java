package com.example.untibunti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView save=findViewById(R.id.save);
        Button back=findViewById(R.id.btback);
        final EditText name=findViewById(R.id.etName);
        EditText eMAIL=findViewById(R.id.etEmail);
        final EditText phone=findViewById(R.id.etMobile);
        final EditText Location =findViewById(R.id.etLocation);
        final Context vs=this;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid=user.getUid();
        final String email=user.getEmail();
        eMAIL.setText(email);

        final DatabaseReference reff= FirebaseDatabase.getInstance().getReference("Users");
        reff.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String nam=dataSnapshot.child("name").getValue(String.class);
                String phon=dataSnapshot.child("phone").getValue(String.class);
                String location=dataSnapshot.child("location").getValue(String.class);
                if(nam!=null)
                {
                    name.setText(nam);
                    phone.setText(phon);
                    Location.setText(location);

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w( "Failed to read value.", databaseError.toException());
                Toast.makeText(vs, databaseError.toException().toString(), Toast.LENGTH_SHORT).show();


            }




    });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DatabaseReference rr=reff.child(uid);
               rr.child("name").setValue(String.valueOf(name.getText()));
               startActivity(new Intent(vs,Home.class));

            }

        });
    }


}
