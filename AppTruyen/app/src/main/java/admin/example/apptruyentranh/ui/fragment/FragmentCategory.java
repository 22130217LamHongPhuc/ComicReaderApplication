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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.ui.ShowMoreActivity;
import admin.example.apptruyentranh.ui.adapter.AdapterComics;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class FragmentCategory extends Fragment implements View.OnClickListener {

    ComicsesViewModel comicsesViewModel;
    RecyclerView rlvCategory;
    String slug="";
    Button btn_add;
    int currentPage = 1;
    AdapterComics adapterComics;
    TextView item1;
    TextView item2;
    TextView item3;
    TextView select;
    ColorStateList def;
    ArrayList<Comics> comicsList = new ArrayList<>();
    Context context;

    ILoadProgress iLoadProgress;
    Comics comics;

    public FragmentCategory() {
        // Bắt buộc phải có constructor mặc định trống
    }

    public FragmentCategory(ComicsesViewModel comicsesViewModel, Context context, ILoadProgress iLoadProgress) {
        this.comicsesViewModel = comicsesViewModel;
        this.context = context;
        this.iLoadProgress = iLoadProgress;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        Log.d("fragcate", "sss");

        mapping(view);
        recieve();
        listenerObserver();


        return view;
    }

    private void listenerObserver() {
        comicsesViewModel.getListComicsCategory().observe((LifecycleOwner) context, new Observer<Comics>() {
            @Override
            public void onChanged(Comics comics) {

                if (comics != null) {
                    Log.d("ccc",comics.getData().getTitlePage());
                    Log.d("ccc",comicsList.size()+"");
                    if (!comicsList.contains(comics)) {
                        comicsList.add(comics);
                        adapterComics.setData(comics.getData().getItems());
                    }

                }
            }
        });
    }


    private void recieve() {
        if (getArguments() != null) {
            if (getArguments().getSerializable("comics") != null) {
                Comics comics = (Comics) getArguments().getSerializable("comics");
                Log.d("comic", comics.getData().getTitlePage());
                comicsList.add(comics);

                adapterComics = new AdapterComics(comics.getData().getItems(), AdapterComics.TYPECATEGORY, context, comicsesViewModel, iLoadProgress);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.VERTICAL);

                rlvCategory.setLayoutManager(manager);
                rlvCategory.setAdapter(adapterComics);


            } else {
                Log.d("comic", "comicsnulll");
            }
        } else {
            Log.d("comic", "bundlenulll");

        }

    }

    private void mapping(View view) {
        rlvCategory = view.findViewById(R.id.rlvCatogory);
        item1 = view.findViewById(R.id.item1);
        item2 = view.findViewById(R.id.item2);
        item3 = view.findViewById(R.id.item3);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        select = view.findViewById(R.id.select);
        def = item2.getTextColors();
        btn_add = view.findViewById(R.id.btnShowAdd);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowMoreActivity.class);
                if(currentPage==1){
                    comics=getComicsToCategory("Action");
                    intent.putExtra("Comics", (Serializable) comics);
                    intent.putExtra("slug","action");


                } else if(currentPage==2){
                    comics=getComicsToCategory("Chuyển Sinh");
                    intent.putExtra("Comics", (Serializable) comics);
                    intent.putExtra("slug","chuyen-sinh");

                }else {
                    comics=getComicsToCategory("Xuyên Không");
                    intent.putExtra("Comics", (Serializable) comics);
                    intent.putExtra("slug","xuyen-khong");

                }

                context.startActivity(intent);
                iLoadProgress.startProgress();
            }
        });

    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.item1) {
            if (currentPage != 1) {
                currentPage = 1;

                if (checkContainComics("Action")) {
                    for (int i = 0; i < comicsList.size(); i++) {
                        if (comicsList.get(i).getData().getTitlePage().equalsIgnoreCase("Action")) {
                            adapterComics.setData(comicsList.get(i));
                            break;
                        }

                    }
                } else {

                    comicsesViewModel.searchGetComics("action");



                }

                select.animate().x(0).setDuration(100);
                item1.setTextColor(Color.WHITE);
                item2.setTextColor(def);
                item3.setTextColor(def);
            }


        } else if (view.getId() == R.id.item2) {

            if (currentPage != 2) {
                currentPage = 2;
                if (checkContainComics("Chuyển Sinh")) {
                    for (int i = 0; i < comicsList.size(); i++) {
                        if (comicsList.get(i).getData().getTitlePage().equalsIgnoreCase("Chuyển Sinh")) {
                            adapterComics.setData(comicsList.get(i));
                            break;
                        }

                    }
                } else {


                    comicsesViewModel.searchComicsToCategory("chuyen-sinh",1);
                }

                item1.setTextColor(def);
                item2.setTextColor(Color.WHITE);
                item3.setTextColor(def);
                int size = item2.getWidth();
                select.animate().x(size).setDuration(100);

            }

        } else if (view.getId() == R.id.item3) {

            if (currentPage != 3) {
                currentPage = 3;
                boolean check=checkContainComics("Xuyên Không");
                Log.d("ccc",check+"");
                if (check) {
                    Log.d("ccc",check+"");
                    for (int i = 0; i < comicsList.size(); i++) {
                        if (comicsList.get(i).getData().getTitlePage().equalsIgnoreCase("Xuyên Không")) {
                            adapterComics.setData(comicsList.get(i));
                            break;
                        }

                    }
                } else {
                    Log.d("ccc",check+"");



                    comicsesViewModel.searchComicsToCategory("xuyen-khong",1);



                }


                item1.setTextColor(def);
                item3.setTextColor(Color.WHITE);
                item2.setTextColor(def);
                int size = item2.getWidth() * 2;
                select.animate().x(size).setDuration(100);
            }


        }
    }

    private boolean checkContainComics(String category) {
        for (int i = 0; i < comicsList.size(); i++) {
            if (comicsList.get(i).getData().getTitlePage().equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }


    private Comics getComicsToCategory(String category ) {

        for (int i = 0; i < comicsList.size(); i++) {
            if (comicsList.get(i).getData().getTitlePage().equalsIgnoreCase(category)) {
               return comicsList.get(i);
            }
        }
        return null;
    }

    @Override
    public void onStart() {
        Log.d("statecate","starrt");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("statecate","resume");

        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("statecate","pause");

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("statecate","stop");
        iLoadProgress.endProgress();

        super.onStop();
    }
}

