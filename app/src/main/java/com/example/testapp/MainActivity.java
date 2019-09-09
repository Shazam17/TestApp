package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private ShareActionProvider mShareActionProvider;
    private FragmentList fragmentList;
    private Toolbar bar;



    private String adr;
    private final String adressOut = "ADROUT";
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

    public void setAdr(String adress){
        this.adr = adr;
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        if(adr != null){
            FragmentTransaction transaction  = getSupportFragmentManager().beginTransaction();
            FragmentDetails details = new FragmentDetails();

            Bundle bundle = new Bundle();
            bundle.putString(FragmentDetails.adress ,adr);

            details.setArguments(bundle);

            transaction.addToBackStack(null);
            transaction.setCustomAnimations(R.animator.slide_in,R.animator.slide_out,R.animator.slide_in_menu,R.animator.slide_out);

            transaction.replace(R.id.container,details);

            transaction.commit();
        }else{
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container,fragmentList);
            transaction.addToBackStack(null);

            transaction.commit();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parseSite();
        bar = findViewById(R.id.toolbar);
        bar.setTitle(R.string.app_name);

    }



}
