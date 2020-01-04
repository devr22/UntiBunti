package com.example.untibunti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CouponRegisterActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    EditText SRPhone,CouponTitle,CouponDesc,CoinsReq;
    Spinner CoupCat;
    String[] cat={"Basic","Silver","Gold","Platinum","Task"};
    Map<String,String> CoupDet =new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_register);

        firebaseFirestore=FirebaseFirestore.getInstance();
        SRPhone=findViewById(R.id.etShopRegPhone);
        CouponTitle=findViewById(R.id.etCouponTitle);
        CouponDesc=findViewById(R.id.etCouponDesc);
        CoinsReq=findViewById(R.id.etCoinsReq);
        CoupCat=findViewById(R.id.spCouponCat);
        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,cat);
        CoupCat.setAdapter(arrayAdapter);

        CoupCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CoupDet.put("Category",cat[i]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void submitCoupon(View view) {
        CoupDet.put("Title",CouponTitle.getText().toString());
        CoupDet.put("Description",CouponDesc.getText().toString());
        CoupDet.put("Coins",CoinsReq.getText().toString());

        firebaseFirestore.collection("SHOP").document(SRPhone.getText().toString()).collection("COUPONS").add(CoupDet).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(CouponRegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CouponRegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
