package com.example.testapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WrapperRecipe {

    @SerializedName("recipe")
    @Expose
    private recipe Rec;

    public WrapperRecipe(recipe rec){
        Rec = rec;
    }

    public recipe getRec() {
        return this.Rec;
    }
}
