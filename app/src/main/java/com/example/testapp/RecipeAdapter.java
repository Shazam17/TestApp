package com.example.testapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {


    private ArrayList<recipe> recipes;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(RecipeHolder holder, int position);
    }

    private OnItemClickListener onItemClickListener;

    public class RecipeHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView desc;
        ImageView img;
        String adress;
        public RecipeHolder(View v){
            super(v);
            name = v.findViewById(R.id.name_item);
            desc = v.findViewById(R.id.desc_item);
            img = v.findViewById(R.id.img);
        }
        String getAdress(){
            return this.adress;
        }
    }

    public RecipeAdapter(ArrayList<recipe> recipelist, OnItemClickListener listener, Context context){
        this.recipes = recipelist;
        this.onItemClickListener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.recipe_item, parent, false);


       return new RecipeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeAdapter.RecipeHolder holder, final int position) {
            holder.name.setText(recipes.get(position).getName());
            holder.adress = recipes.get(position).getUUID().toString();

            String desc = recipes.get(position).getDescription();
            if(desc != null){
                if(desc.length() > 25){
                    desc = desc.substring(0 , 25) + "...";
                }
                holder.desc.setText(desc);
            }





        Glide
                .with(context)
                .load(recipes.get(position).getImages().get(0))
                .into(holder.img);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(holder,position);
                }
            });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
