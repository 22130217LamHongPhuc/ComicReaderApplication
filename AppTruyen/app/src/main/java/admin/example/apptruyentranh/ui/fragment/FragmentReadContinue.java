package admin.example.apptruyentranh.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.dbroom.ItemDAO;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.ui.adapter.AdapterComicsDao;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class FragmentReadContinue extends Fragment {
    RecyclerView rlvReadContinue;
    AdapterComicsDao adapterComicsDao;
    ComicsesViewModel comicsesViewModel;
    ILoadProgress iLoadProgress;
    List<ItemDAO> listItem;

    public FragmentReadContinue(ComicsesViewModel comicsesViewModel ,ILoadProgress iLoadProgress) {
        this.comicsesViewModel=comicsesViewModel;
        this.iLoadProgress=iLoadProgress;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_read_continue,container,false);
        mapping(view);
        recieve();
        return view;
    }

    private void recieve() {
        if(getArguments()!=null){
            if(getArguments().getSerializable("comics")!=null){
                listItem =  getArguments().getParcelableArrayList("comics");
                if(listItem!=null){
                    adapterComicsDao=new AdapterComicsDao(listItem,getContext(),AdapterComicsDao.TypeBig,comicsesViewModel,iLoadProgress);
                    LinearLayoutManager manager=new LinearLayoutManager(getContext());
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);

                    rlvReadContinue.setLayoutManager(manager);
                    rlvReadContinue.setHasFixedSize(true);
                    rlvReadContinue.setAdapter(adapterComicsDao);

                }else{
                    Log.d("comicss","comicsnulll");

                }


            }else{
                Log.d("comic","comicsnulll");
            }
        }else{
            Log.d("comic","bundlenulll");

        }

    }

    private void mapping(View view) {
        rlvReadContinue =view.findViewById(R.id.rlvReadContinue);

    }
}
