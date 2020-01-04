package com.example.untibunti;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.untibunti.Model.ShopModel;
import com.example.untibunti.ShopViewHolder.ShopRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {


    ArrayList<String > categoryArrayList;
    Context context;



    public CategoryRecyclerAdapter(ArrayList<String> categoryArrayList, Context context) {
        this.categoryArrayList = categoryArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_listview_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        FirebaseFirestore.getInstance()
                .collection("SHOP")
                .whereEqualTo("category",new ShopCategories().getB().get(position))
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<ShopModel> shopArrayList = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    shopArrayList.add(new ShopModel(documentSnapshot));
                    Log.e("Snap",documentSnapshot.getData().toString());
                }

                holder.categoryName.setText(categoryArrayList.get(position));
                RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                holder.shopRV.setLayoutManager(layoutManager);
                holder.shopRV.setHasFixedSize(true);
                Log.e("ArrayList",shopArrayList.toString());

                ShopRecyclerAdapter shopRecyclerAdapter = new ShopRecyclerAdapter(shopArrayList);
                holder.shopRV.setAdapter(shopRecyclerAdapter);
                holder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(),CategoryWiseShopDisplay.class);
                        intent.putExtra("name",categoryArrayList.get(position));
                        view.getContext().startActivity(intent);

                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView categoryName;
        RecyclerView shopRV;
        Button more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName=itemView.findViewById(R.id.tvCategoryName);
            shopRV =itemView.findViewById(R.id.rvShops);
            more = itemView.findViewById(R.id.btnMore);


        }
    }


}
