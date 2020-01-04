package com.example.untibunti.Model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ShopModel {

    public String name,description, shopID;
    public String src;
    public QueryDocumentSnapshot documentSnapshot;



    public ShopModel(QueryDocumentSnapshot queryDocumentSnapshot) {

        documentSnapshot=queryDocumentSnapshot;

        this.name = queryDocumentSnapshot.get("name").toString();
        this.description= queryDocumentSnapshot.get("description").toString();
        src="https://pixabay.com/get/57e5d5404c50b108f5d08460825668204022dfe05456754f76267bd4/shop-150362_1280.png?attachment";
        shopID=queryDocumentSnapshot.getId();

    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public ShopModel()
    {

    }
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
