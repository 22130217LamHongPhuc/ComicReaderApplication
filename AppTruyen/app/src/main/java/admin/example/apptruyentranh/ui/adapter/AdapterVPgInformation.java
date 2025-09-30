package admin.example.apptruyentranh.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import admin.example.apptruyentranh.model.Banner;
import admin.example.apptruyentranh.model.Category;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.ui.MainAppActivity;
import admin.example.apptruyentranh.ui.fragment.FragmentChapter;
import admin.example.apptruyentranh.ui.fragment.FragmentFavorite;
import admin.example.apptruyentranh.ui.fragment.FragmentHome;
import admin.example.apptruyentranh.ui.fragment.FragmentInformation;
import admin.example.apptruyentranh.ui.fragment.FragmentProfile;
import admin.example.apptruyentranh.ui.fragment.FragmentSearch;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;


public class AdapterVPgInformation extends FragmentStateAdapter {

    ComicsesViewModel comicsesViewModel;
    Context context;
    Comics comics;


    public AdapterVPgInformation(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ComicsesViewModel comicsesViewModel, Context mainAppActivity,Comics comics) {

        super(fragmentManager, lifecycle);

        this.comicsesViewModel=comicsesViewModel;
        this.context=mainAppActivity;
        this.comics=comics;


    }



    @Override
    public Fragment createFragment(int position) {
        Fragment fragment =null;

        switch(position){
            case 0:
                fragment=  new FragmentInformation(comicsesViewModel,context,comics);
                break;
            case 1:
                fragment=  new FragmentChapter(comicsesViewModel,context,comics);
                break;



        };


        return fragment;



    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
