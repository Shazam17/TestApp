package com.example.testapp;

import android.widget.ArrayAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.UUID;

public class recipe {

    @SerializedName("uuid")
    @Expose
    private UUID uuid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("images")
    @Expose
    private ArrayList<String> images;

    @SerializedName("lastUpdated")
    @Expose
    private int lastUpdated;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("instructions")
    @Expose
    private String instructions;

    @SerializedName("difficulty")
    @Expose
    private int difficulty;

    @SerializedName("similar")
    @Expose
    private ArrayList<recipe> similar;




    public ArrayList<String> getImages(){
        return images;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return description;
    }

    public String getInstructions() {
        return  instructions; }


    public int getDifficulty() {
        return difficulty; }

    public ArrayList<recipe> getSimilar() {
        return similar;
    }

    public UUID getUUID(){
        return this.uuid;
    }

}
