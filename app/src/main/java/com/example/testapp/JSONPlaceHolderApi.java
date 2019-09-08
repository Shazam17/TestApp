package com.example.testapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {
    @GET("/recipes")
    public Call<ListModel>getRecipes();

    @GET("/recipes/{name}")
    public Call<WrapperRecipe>getRecipe(@Path("name") String name);

    @GET("/recipes/fc988768-c1e9-11e6-a4a6-cec0c932ce01")
    public Call<WrapperRecipe>getRecipeWrapper();



}
