package admin.example.apptruyentranh.ui.fragment;

import static android.os.Build.VERSION_CODES.O;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.style.FadingCircle;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.dbroom.ItemDatabase;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.ServerDatum;
import admin.example.apptruyentranh.ui.adapter.AdapterChapter;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;


public class FragmentChapter extends Fragment {
    ComicsesViewModel comicsesViewModel;
    Context context;
    RecyclerView rlvChapter;
    ProgressBar progressBar;
    Comics comicsStory;
    AdapterChapter adapterChapter;
    List<ServerDatum> list=new ArrayList<>();
    String dateupdate="";



    public FragmentChapter(ComicsesViewModel comicsesViewModel, Context context,Comics comics) {
        this.comicsesViewModel = comicsesViewModel;
        this.context = context;
        this.comicsStory=comics;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentchapter, null);
        mapping(view);
        initial();

        return view;
    }

    private void initial() {
        progressBar.setIndeterminateDrawable(new FadingCircle());

        list= comicsStory.getData().getItem().getChapters().get(0).getServerData();
        DateTimeFormatter formatter = null;

        if (android.os.Build.VERSION.SDK_INT >= O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(comicsStory.getData().getItem().getUpdatedAt(), formatter);
            LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
            dateupdate=localDateTime.getDayOfMonth()+"-"+localDateTime.getMonthValue()+"-"+localDateTime.getYear()+"";
        }


        adapterChapter=new AdapterChapter(list, dateupdate, context, comicsesViewModel, new AdapterChapter.LoadProGressBar() {
            @Override
            public void loadProgress() {

                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void addItemIntoDatabase() {
                comicsesViewModel.updateViewAndLike(comicsStory.getData().getItem(),true);
              ItemDatabase.addItemIntoDatabase(comicsStory.getData().getItem(), context,"Readed");
            }
        });
        rlvChapter.setLayoutManager( new LinearLayoutManager(context));
        rlvChapter.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        rlvChapter.setAdapter(adapterChapter);
    }



    void mapping(View view) {

      rlvChapter=view.findViewById(R.id.rlvListChapter);
      progressBar=view.findViewById(R.id.progress_chapter);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("stateee","pause");

    }

    @Override
    public void onStop() {
        if(progressBar.getVisibility()==View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
        Log.d("stateee","stop");
        super.onStop();
    }

    @Override
    public void onResume() {

        super.onResume();
        Log.d("stateee","resume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();Log.d("stateee","destroy");
    }
}
