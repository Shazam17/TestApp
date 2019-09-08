package com.example.testapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListModel {

    @SerializedName("recipes")
    @Expose
    private ArrayList<recipe> recipes;

    ArrayList<recipe> getRecipes(){
        return recipes;
    }


}
