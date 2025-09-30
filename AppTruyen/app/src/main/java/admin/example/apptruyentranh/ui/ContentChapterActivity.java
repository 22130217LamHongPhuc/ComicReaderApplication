package admin.example.apptruyentranh.ui;

import static java.util.stream.Collectors.mapping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.ResponeChapter;
import admin.example.apptruyentranh.ui.adapter.AdapterChapter;
import admin.example.apptruyentranh.ui.adapter.AdapterContentChapter;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class ContentChapterActivity extends AppCompatActivity {
    RecyclerView rlvChapter;
    ComicsesViewModel comicsesViewModel;
    AdapterContentChapter adapterChapter;
    ResponeChapter responeChapter;
    List<ResponeChapter.ChapterImage> imageList = new ArrayList<>();
    TextView txtchaptername;
    ProgressBar progressBar;
    boolean isLoading = false;
    boolean isLastPage = false;
    int currentPage = 1;
    int totalPage = 5;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_chapter);
        mapping();
         responeChapter=(ResponeChapter) getIntent().getSerializableExtra("contentChapter");
        Log.d("contenthe",responeChapter.getNameChapter());
        initial();
    }

    private void mapping() {
        rlvChapter = findViewById(R.id.rlvContentChapter);

        txtchaptername = findViewById(R.id.txtlistChapterPosition);
    }

    private void initial() {



        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        comicsesViewModel = new ViewModelProvider(this).get(ComicsesViewModel.class);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;


        adapterChapter = new AdapterContentChapter(responeChapter, this, width, height, imageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rlvChapter.setLayoutManager(layoutManager);
        rlvChapter.setHasFixedSize(true);




        rlvChapter.setAdapter(adapterChapter);
        rlvChapter.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItem = layoutManager.getItemCount();
                int first = layoutManager.findFirstVisibleItemPosition();
                int visible = layoutManager.getChildCount();
                Log.d("src", first + " - " + visible + " - " + totalItem);

                if (!isLoading && first >= 0 && first+visible>=totalItem-3 && !isLastPage) {
                    loadMore();
                    Log.d("sss","scroll");
                }
            }
        });

        totalPage = responeChapter.getListContentChapter().size() / 10;
        txtchaptername. setText("Chapter " + responeChapter.getNameChapter());
        imageList = createList();

        position = imageList.size();
        adapterChapter.setData(imageList, responeChapter);
        adapterChapter.notifyItemRangeInserted(0,10);





    }

    public void loadMore() {

        if (position >= responeChapter.getListContentChapter().size()) {
            isLastPage = true;
            return;
        }

        isLoading = true;


        List<ResponeChapter.ChapterImage> newList=createList();

        for (int i = 0; i < newList.size(); i++) {
            Log.d("new",newList.get(i).getImageFile());

        }
        int oldPosition = position;
        position += newList.size();

        Log.d("pos",position+"");

        // Sử dụng Handler để trì hoãn việc thay đổi đến khung hình tiếp theo
        List<ResponeChapter.ChapterImage> finalNewList = newList;
        new Handler(Looper.getMainLooper()).post(() -> {
            imageList.addAll(finalNewList);
            adapterChapter.setData(imageList, responeChapter);
            adapterChapter.notifyItemRangeInserted(oldPosition, finalNewList.size());
            isLoading = false;
        });

        newList=null;
    }

    private List<ResponeChapter.ChapterImage> createList() {
        List<ResponeChapter.ChapterImage> list=new ArrayList<>();
        int positionNext=Math.min(position + 10, responeChapter.getListContentChapter().size());
        for (int i = position; i < positionNext; i++) {
            Log.d("pos",i+"");
            Log.d("cre",responeChapter.getListContentChapter().get(i).getImageFile());
            list.add(responeChapter.getListContentChapter().get(i));
        }

        return list;


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}