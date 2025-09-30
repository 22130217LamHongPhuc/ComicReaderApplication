package admin.example.apptruyentranh.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.custom.GridSpacingItemDecoration;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.ui.ShowMoreActivity;
import admin.example.apptruyentranh.ui.adapter.AdapterComics;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class FragmentComicsAnotherCate extends Fragment {
    View view;
    RecyclerView rlvAnime,rlvCoDai,rlvNgonTinh,rlvWebtoon;
    AdapterComics adapterAnime,adapterCoDai,adapterNgonTinh,adapterWebtoon;
    TextView txtAnimeMore,txtCoDaiMore,txtNgonTinhMore,txtWebtoonMore;
    Context context;

    ILoadProgress iLoadProgress;
    List<Comics> comicsList;

    ComicsesViewModel comicsesViewModel;

    public FragmentComicsAnotherCate(ComicsesViewModel comicsesViewModel, Context context, ILoadProgress iLoadProgress) {
        this.comicsesViewModel = comicsesViewModel;
        this.context = context;
        this.iLoadProgress = iLoadProgress;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragmentcomicsanothercate,container,false);
        mapping(view);
        recieve();
        initial();
        return view;
    }

    private void initial() {
        if(comicsList!=null){

            initialForAdapter();
        }

        txtAnimeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowMoreActivity.class);

                   Comics comics=getComicsToCategory("Anime");
                    intent.putExtra("Comics", (Serializable) comics);
                    intent.putExtra("slug","action");
                    startActivity(intent);

                }
        });

        txtCoDaiMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowMoreActivity.class);

                Comics comics=getComicsToCategory("Cổ Đại");
                intent.putExtra("Comics", (Serializable) comics);
                intent.putExtra("slug","co-dai");
                startActivity(intent);


            }
        });

        txtNgonTinhMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowMoreActivity.class);

                Comics comics=getComicsToCategory("Ngôn Tình");
                intent.putExtra("Comics", (Serializable) comics);
                intent.putExtra("slug","ngon-tinh");
                startActivity(intent);


            }
        });

        txtWebtoonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowMoreActivity.class);

                Comics comics=getComicsToCategory("Webtoon");
                intent.putExtra("Comics", (Serializable) comics);
                intent.putExtra("slug","webtoon");
                startActivity(intent);


            }
        });
    }

    private Comics getComicsToCategory(String category ) {

        for (int i = 0; i < comicsList.size(); i++) {
            if (comicsList.get(i).getData().getTitlePage().equalsIgnoreCase(category)) {
                return comicsList.get(i);
            }
        }
        return null;
    }


    private void initialForAdapter() {
        for (int i = 0; i < comicsList.size(); i++) {
            Comics comics=comicsList.get(i);
            setAdapterForRlv(comics);
        }
    }

    private void setAdapterForRlv(Comics comics) {
        String title=comics.getData().getTitlePage();
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3); // Tạo layoutManager mới cho mỗi RecyclerView

        if(title.equalsIgnoreCase("Anime")){
            adapterAnime=new AdapterComics(comics.getData().getItems(),AdapterComics.TYPEBIGANOTHER,context,comicsesViewModel,iLoadProgress);
            rlvAnime.setLayoutManager(layoutManager);
            rlvAnime.setHasFixedSize(true);
            rlvAnime.setAdapter(adapterAnime);
        }else if(title.equalsIgnoreCase("Cổ Đại")){
            adapterCoDai=new AdapterComics(comics.getData().getItems(),AdapterComics.TYPEBIGANOTHER,context,comicsesViewModel,iLoadProgress);
            rlvCoDai.setLayoutManager(layoutManager);
            rlvCoDai.setHasFixedSize(true);
            rlvCoDai.setAdapter(adapterCoDai);
        } else if (title.equalsIgnoreCase("Ngôn Tình")) {
            adapterNgonTinh=new AdapterComics(comics.getData().getItems(),AdapterComics.TYPEBIGANOTHER,context,comicsesViewModel,iLoadProgress);
            rlvNgonTinh.setLayoutManager(layoutManager);
            rlvNgonTinh.setHasFixedSize(true);
            rlvNgonTinh.setAdapter(adapterNgonTinh);
        }else{
            adapterWebtoon=new AdapterComics(comics.getData().getItems(),AdapterComics.TYPEBIGANOTHER,context,comicsesViewModel,iLoadProgress);
            rlvWebtoon.setLayoutManager(layoutManager);
            rlvWebtoon.setHasFixedSize(true);
            rlvWebtoon.setAdapter(adapterWebtoon);
        }
    }

    private void recieve() {
       comicsList = getArguments().getParcelableArrayList("comics");
    }

    private void mapping(View view) {
        rlvAnime=view.findViewById(R.id.rlvAnime);
        rlvCoDai=view.findViewById(R.id.rlvCoDai);
        rlvNgonTinh=view.findViewById(R.id.rlvNgonTinh);
        rlvWebtoon=view.findViewById(R.id.rlvWebtoon);
        txtAnimeMore=view.findViewById(R.id.txtComicsAnimeAnotherShowAdd);
        txtCoDaiMore=view.findViewById(R.id.txtComicsCoDaiAnotherShowAdd);
        txtNgonTinhMore=view.findViewById(R.id.txtComicsNgonTinhAnotherShowAdd);
        txtWebtoonMore=view.findViewById(R.id.txtComicsWebtoonAnotherShowAdd);


    }
}
