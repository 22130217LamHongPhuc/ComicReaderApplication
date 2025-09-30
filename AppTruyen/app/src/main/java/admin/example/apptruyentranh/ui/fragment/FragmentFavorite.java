package admin.example.apptruyentranh.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.dbroom.ItemDAO;
import admin.example.apptruyentranh.dbroom.ItemDatabase;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.ui.LoginActivity;
import admin.example.apptruyentranh.ui.MainAppActivity;
import admin.example.apptruyentranh.ui.adapter.AdapterComics;
import admin.example.apptruyentranh.ui.adapter.AdapterComicsDao;
import admin.example.apptruyentranh.viewmodel.AuthViewModel;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class FragmentFavorite extends Fragment implements View.OnClickListener {

    private Button btnlogin;
    private View mainView = null;
    private View viewLoggedIn = null;
    ProgressBar progressBar;
    int currentPage = 1;
    AdapterComicsDao adapterComics;
    ArrayList<ItemDAO> list;
    TextView item1;
    TextView item2;
    TextView item3;
    TextView select;
    ColorStateList def;
    boolean isButtonMapped = false;
    RecyclerView rlvFavorite;
    TabLayout tabLayout;
    private String checkView = "";
    ComicsesViewModel comicsesViewModel;
    AuthViewModel authViewModel;
    Context context;
    private ProgressBar iLoadProgress;


    public FragmentFavorite(ComicsesViewModel comicsesViewModel, Context context) {
        this.comicsesViewModel = comicsesViewModel;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_love_logined, container);
        checkView = "Logined";
        mappingVer2(mainView);
        initial();


        return mainView;
    }


    @Override
    public void onStart() {

        super.onStart();
        Log.d("statefff", "start");
    }


    private void mappingVer2(View view) {
        rlvFavorite = view.findViewById(R.id.rlvLoveAndRead);
        item1 = view.findViewById(R.id.item1);
        item2 = view.findViewById(R.id.item2);
        progressBar=view.findViewById(R.id.progress_favorite);


        item1.setOnClickListener(this);
        item2.setOnClickListener(this);

        select = view.findViewById(R.id.select);
        def = item2.getTextColors();

        initial();

    }

    private void initial() {
        list = (ArrayList<ItemDAO>) ItemDatabase.getInstance(getContext()).ItemDao().getListComicss("Readed");
        list.sort(new Comparator<ItemDAO>() {
            @Override
            public int compare(ItemDAO t1, ItemDAO t2) {
                long i1 = Long.valueOf(t1.getTime()) ;
                long i2 = Long.valueOf(t2.getTime()) ;
                if(i1>i2){
                    return -1;
                }else{
                    return 1;
                }
            }
        });
        adapterComics = new AdapterComicsDao(list, context, AdapterComicsDao.TypeLong, comicsesViewModel, new ILoadProgress() {
            @Override
            public void startProgress() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void endProgress() {
                progressBar.setVisibility(View.GONE);
            }
        });
        rlvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        rlvFavorite.setAdapter(adapterComics);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item1) {
            if (currentPage != 1) {
                list = (ArrayList<ItemDAO>) ItemDatabase.getInstance(getContext()).ItemDao().getListComicss("Readed");
                list.sort(new Comparator<ItemDAO>() {
                    @Override
                    public int compare(ItemDAO t1, ItemDAO t2) {
                        long i1 = Long.valueOf(t1.getTime()) ;
                        long i2 = Long.valueOf(t2.getTime()) ;
                        if(i1>i2){
                            return -1;
                        }else{
                            return 1;
                        }
                    }
                });
                adapterComics.setData(list);


                currentPage = 1;
                select.animate().x(0).setDuration(100);
                item1.setTextColor(Color.WHITE);
                item2.setTextColor(def);

            }


        } else if (view.getId() == R.id.item2) {
            if (currentPage != 2) {
                currentPage = 2;
                item1.setTextColor(def);
                item2.setTextColor(Color.WHITE);
                int size = item2.getWidth();
                select.animate().x(size).setDuration(100);
                list = (ArrayList<ItemDAO>) ItemDatabase.getInstance(getContext()).ItemDao().getListComicss("Favorite");
                list.sort(new Comparator<ItemDAO>() {
                    @Override
                    public int compare(ItemDAO t1, ItemDAO t2) {
                        long i1 = Long.valueOf(t1.getTime()) ;
                        long i2 = Long.valueOf(t2.getTime()) ;
                        if(i1>i2){
                            return -1;
                        }else{
                            return 1;
                        }
                    }
                });
                Log.d("listdao",list.size()+"");
                adapterComics.setData(list);


            }


        }

    }

    private void listener() {

    }


}
