package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private FragmentList fragmentList;

    private ArrayList<recipe> recipes;
    private String url = "https://test.kode-t.ru/";

    public ArrayList<recipe> getRecipes(){
        return this.recipes;
    }

    private void parseSite(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.kode-t.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONPlaceHolderApi service = retrofit.create(JSONPlaceHolderApi.class);

        Call<ListModel> call = service.getRecipes();

        call.enqueue(new Callback<ListModel>() {
            @Override
            public void onResponse(@NonNull Call<ListModel> call,@NonNull Response<ListModel> response) {
                if(response.isSuccessful()){

                    recipes = response.body().getRecipes();

                    fragmentList = new FragmentList();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,fragmentList);
                    transaction.addToBackStack(null);

                    transaction.commit();

                }else{
                    switch (response.code()){
                        case 404:
                            Toast tst1 = Toast.makeText(getApplicationContext(),"page didnt find",Toast.LENGTH_SHORT);
                            tst1.show();
                        case 500:
                            Toast tst2 = Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT);
                            tst2.show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListModel> call,@NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseSite();
    }






      /*  Call<WrapperRecipe> call = service.getRecipeWrapper();
        call.enqueue(new Callback<WrapperRecipe>() {
            @Override
            public void onResponse(Call<WrapperRecipe> call, Response<WrapperRecipe> response) {

                Log.d("error","Success");

                if(response.isSuccessful()){
                    Toast tst1 = Toast.makeText(getApplicationContext(),response.body().getRec().getName(),Toast.LENGTH_SHORT);
                    tst1.show();
                }else{
                    switch (response.code()){
                        case 404:
                            Toast tst1 = Toast.makeText(getApplicationContext(),"page didnt find",Toast.LENGTH_SHORT);
                            tst1.show();
                        case 500:
                            Toast tst2 = Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT);
                            tst2.show();
                    }
                }
            }
            @Override
            public void onFailure(Call<WrapperRecipe> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });*/


}
