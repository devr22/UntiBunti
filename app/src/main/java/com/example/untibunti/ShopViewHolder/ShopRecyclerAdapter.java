package com.example.untibunti.ShopViewHolder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.untibunti.Model.ShopModel;
import com.example.untibunti.R;
import com.example.untibunti.ShopDisplayActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopRecyclerAdapter extends RecyclerView.Adapter<ShopRecyclerAdapter.ViewHolder>
{

    ArrayList<ShopModel> shops;

    public ShopRecyclerAdapter(ArrayList<ShopModel> shops) {
        this.shops = shops;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shopmodel,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.shopName.setText(shops.get(position).getName());
        holder.shopDescription.setText(shops.get(position).getDescription());
        Picasso.get().load(shops.get(position).getSrc()).into(holder.imageView);
        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShopDisplayActivity.class);
                intent.putExtra("snap",shops.get(position).getShopID());
                view.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView shopName,shopDescription;
        ImageView imageView ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopName=itemView.findViewById(R.id.tvShopName);
            shopDescription = itemView.findViewById(R.id.tvShopDesc);
            imageView = itemView.findViewById(R.id.ivShopImage);
        }
    }

}
