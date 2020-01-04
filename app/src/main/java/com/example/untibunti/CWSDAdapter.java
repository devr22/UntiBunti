package com.example.untibunti;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.untibunti.Model.ShopModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CWSDAdapter extends RecyclerView.Adapter<CWSDAdapter.ViewHolder> {


    ArrayList<ShopModel> list;
    Context context;

    public CWSDAdapter(ArrayList<ShopModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cwsdlayout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.shopName.setText(list.get(position).getName());
        holder.shopDesc.setText(list.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ShopDisplayActivity.class);
                intent.putExtra("snap",list.get(position).getShopID());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView shopName, shopDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shopName=itemView.findViewById(R.id.tvCWSDShopName);
            shopDesc = itemView.findViewById(R.id.tvCWSDShopDesc);
        }
    }


}
