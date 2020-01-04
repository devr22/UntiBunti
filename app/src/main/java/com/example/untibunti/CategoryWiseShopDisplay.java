package com.example.untibunti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.untibunti.Model.ShopModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryWiseShopDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_shop_display);

        TextView tv=findViewById(R.id.tvCategoryTitle);
        String st= getIntent().getStringExtra("name");
        tv.setText(st);

        final RecyclerView recyclerView =findViewById(R.id.rvCWSDshops);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        final Context context =this;
        FirebaseFirestore.getInstance()
                .collection("SHOP")
                .whereEqualTo("category",st)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<ShopModel> list= new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    list.add(new ShopModel(documentSnapshot));
                }
                CWSDAdapter cwsdAdapter =new CWSDAdapter(list,context);
                recyclerView.setAdapter(cwsdAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
