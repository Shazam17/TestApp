package com.example.testapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentDetails extends Fragment {



    private recipe rec;
    private String adr;
    private ArrayList<recipe> sameRecipes;

    private ImgePagerAdapter adapter;
    private ViewPager viewPager;
    private TextView difficult;
    private TextView name;
    private TextView desc;
    private TextView insr;
    private RecyclerView recyclerView;
    private RecipeAdapter recAdapter;

    public static final String adress = "ADRESS";
    public static final String position = "POS";


    void parse(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.kode-t.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONPlaceHolderApi service = retrofit.create(JSONPlaceHolderApi.class);
        Call<WrapperRecipe> call = service.getRecipe(adr);

        call.enqueue(new Callback<WrapperRecipe>() {
            @Override
            public void onResponse(@NonNull Call<WrapperRecipe> call,@NonNull Response<WrapperRecipe> response) {
                if(response.isSuccessful()){
                    rec = response.body().getRec();

                    MainActivity activity = (MainActivity)getActivity();
                    name.setText(rec.getName());
                    desc.setText(rec.getDescription());
                    difficult.setText("Difficult is :" + Integer.toString(rec.getDifficulty()));
                    sameRecipes = rec.getSimilar();


                    String ins = rec.getInstructions().replace("<br>","\n");
                    insr.setText(ins);
                    adapter = new ImgePagerAdapter(getChildFragmentManager(), rec.getImages());
                    viewPager.setAdapter(adapter);

                    recAdapter = new RecipeAdapter(sameRecipes, new RecipeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecipeAdapter.RecipeHolder holder, int position){
                            FragmentTransaction transaction  = getActivity().getSupportFragmentManager().beginTransaction();
                            FragmentDetails details = new FragmentDetails();

                            Bundle bundle = new Bundle();
                            bundle.putInt(FragmentDetails.position,position);
                            bundle.putString(FragmentDetails.adress ,holder.getAdress());

                            details.setArguments(bundle);

                            transaction.addToBackStack(null);
                            transaction.setCustomAnimations(R.animator.slide_in,R.animator.slide_out,R.animator.slide_in_menu,R.animator.slide_out);

                            transaction.replace(R.id.container,details);

                            transaction.commit();
                        }
                    }, getActivity());


                    recyclerView.setAdapter(recAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    //recyclerView.setMinimumHeight(sameRecipes.size()* 50);
                }else{
                    switch (response.code()){
                        case 404:
                            Log.d("log","error 404");
                            break;
                        case 500:
                            Log.d("log","error 500");

                            break;
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<WrapperRecipe> call,@NonNull Throwable t) {
                Log.d("download", "onFailure: failed");
            }
        });
    }


    public String getAdr(){
        return adr;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            adr = getArguments().getString(adress);

            MainActivity activity = (MainActivity)getActivity();
            activity.setAdr(adr);
        }catch (@NonNull NullPointerException ex){
            Log.d("NULL_POINTER_EXCEPTION",ex.getMessage());
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_frag, container,false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.pager);
        name = view.findViewById(R.id.name_details);
        desc = view.findViewById(R.id.desc_details);
        insr = view.findViewById(R.id.instruction_details);
        recyclerView = view.findViewById(R.id.recycler_view_detail);
        difficult =  view.findViewById(R.id.difficult_detail);
        parse();
    }
}
