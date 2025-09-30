package admin.example.apptruyentranh.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.ui.ShowMoreActivity;
import admin.example.apptruyentranh.ui.adapter.AdapterComics;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class FragmentComicsFavorite extends Fragment {
    private final ComicsesViewModel comicsesViewModel;
    private final ILoadProgress iLoadProgress;
    TextView txt_showAdd;
    RecyclerView rlvFinish;
    AdapterComics adapterComics;
    private Comics comics;

    public FragmentComicsFavorite(ComicsesViewModel comicsesViewModel , ILoadProgress iLoadProgress) {
        this.comicsesViewModel=comicsesViewModel;
        this.iLoadProgress=iLoadProgress;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_comicsfavorite,container,false);
        mapping(view);
        recieve();
        return view;
    }

    private void recieve() {
        if(getArguments()!=null){
            if(getArguments().getSerializable("comicsesFavorite")!=null){
                 comics = (Comics) getArguments().getSerializable("comicsesFavorite");
                Log.d("comic",comics.getData().getTitlePage());
                adapterComics =new AdapterComics(comics.getData().getItems(), AdapterComics.TYPEBIG,getContext(),comicsesViewModel,iLoadProgress);
                LinearLayoutManager manager=new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);

                rlvFinish.setLayoutManager(manager);
                rlvFinish.setAdapter(adapterComics);


            }else{
                Log.d("comic","comicsnulll");
            }
        }else{
            Log.d("comic","bundlenulll");

        }

    }



    private void mapping(View view) {
        txt_showAdd=view.findViewById(R.id.txtComicsFavoriteShowAdd);
        rlvFinish=view.findViewById(R.id.rlvComicsFavorite);

        txt_showAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ShowMoreActivity.class);
                intent.putExtra("Comics", (Serializable) comics);
                startActivity(intent);
            }
        });


    }
}
