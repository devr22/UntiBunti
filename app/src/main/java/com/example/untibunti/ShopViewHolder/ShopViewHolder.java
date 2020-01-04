package com.example.untibunti.ShopViewHolder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.untibunti.R;

public class ShopViewHolder extends RecyclerView.ViewHolder {

    public TextView shopName,shopDesc;
    public ImageView ShopImage;

    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);
        shopName=itemView.findViewById(R.id.tvShopName);
        shopDesc=itemView.findViewById(R.id.tvShopDesc);
        ShopImage=itemView.findViewById(R.id.ivShopImage);

    }
}
