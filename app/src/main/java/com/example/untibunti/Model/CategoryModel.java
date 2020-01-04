package com.example.untibunti.Model;

import java.util.ArrayList;

public class CategoryModel {
    private String category;
    private ArrayList<ShopModel> arrayList;

    public CategoryModel() {
    }

    public CategoryModel(String category, ArrayList<ShopModel> arrayList) {
        this.category = category;
        this.arrayList = arrayList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<ShopModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ShopModel> arrayList) {
        this.arrayList = arrayList;
    }
}
