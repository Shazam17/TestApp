package com.example.testapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ImgePagerAdapter extends FragmentPagerAdapter {


    private ArrayList<String> imagesAdress;

    public ImgePagerAdapter(@NonNull FragmentManager fm, ArrayList<String> imagesAdress) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.imagesAdress = imagesAdress;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        ImageFragment imageFragment = new ImageFragment();
        Bundle bundle = new Bundle();

        bundle.putString(ImageFragment.adress,imagesAdress.get(position));

        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Override
    public int getCount() {
        return imagesAdress.size();
    }
}
