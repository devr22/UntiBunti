package com.example.untibunti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class PhoneAuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        final CountryCodePicker countryCodePicker=findViewById(R.id.ccp);
        final EditText phone=findViewById(R.id.phoneNumberText);
        Button next=findViewById(R.id.btnNext);
        countryCodePicker.registerPhoneNumberTextView(phone);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(phone.getText().toString()))
                {
                    Toast.makeText(PhoneAuthActivity.this, "Enter A number", Toast.LENGTH_SHORT).show();
                }
                else if(phone.getText().toString().replace(" ","").length()!=10)
                {
                    Toast.makeText(PhoneAuthActivity.this, "Enter a Valid Number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent=new Intent(PhoneAuthActivity.this,VerificationActivity.class);
                    intent.putExtra("number",countryCodePicker.getFullNumberWithPlus().replace(" ",""));
                    startActivity(intent);
                    finish();
                }
            }
        });
        FirebaseFirestore.getInstance().collection("SHOP")
                .orderBy("category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Pao ji", document.getId() + " => " + document.getData());
                                Toast.makeText(getApplicationContext(), "yesss", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w("Arre", "Error getting documents.", task.getException());
                        }


                    }
                });

    }


    public void shopRegiste(View view) {
        startActivity(new Intent(view.getContext(),ShopRegisterActivity.class));
        finish();
    }

    public void registerCoupon(View view) {
        startActivity(new Intent(view.getContext(),CouponRegisterActivity.class));
        finish();

    }
}
