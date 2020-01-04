package com.example.untibunti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.untibunti.Model.ShopModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ShopDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_display);
        final TextView tvSDshopName;
        tvSDshopName=findViewById(R.id.tvSDshopName);
        final TextView sdShopDesc = findViewById(R.id.tvSDShopDesc);

        String s = getIntent().getStringExtra("snap");

        FirebaseFirestore.getInstance()
                .collection("SHOP")
                .document(s)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                tvSDshopName.setText(documentSnapshot.get("name").toString());
                sdShopDesc.setText(documentSnapshot.get("description").toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShopDisplayActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
