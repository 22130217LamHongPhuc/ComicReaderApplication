package admin.example.apptruyentranh.ui;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.api.ApiService;
import admin.example.apptruyentranh.dbroom.ItemDAO;
import admin.example.apptruyentranh.dbroom.ItemDatabase;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.Comment;
import admin.example.apptruyentranh.model.Item;
import admin.example.apptruyentranh.model.ResponeChapter;
import admin.example.apptruyentranh.repository.ComicsesRepository;
import admin.example.apptruyentranh.ui.adapter.AdapterChapter;
import admin.example.apptruyentranh.ui.adapter.AdapterComment;
import admin.example.apptruyentranh.ui.adapter.AdapterVPgInformation;
import admin.example.apptruyentranh.ui.adapter.AdapterVPgerMain;
import admin.example.apptruyentranh.ui.bottomsheet.CommentBottomSheetFragment;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class DetailComicsActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView collapsingToolbarImageView;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    ComicsesViewModel comicsesViewModel;
    Comics comicsStory;
    ViewPager2 viewPager2;
    Button btnRead;
    ImageView imgHeart;
    CommentBottomSheetFragment bottomSheetCmt;


    String slug="";
    boolean check=false;
    ProgressBar progressBar;
    String apiFirstChapter;
    Random rd=new Random();
    RecyclerView rlvCmt;
    AdapterComment adapterComment;
    private ImageView imgCmt;
    Item item;
    List<Comment> list;
    ShimmerFrameLayout shimmerFrameLayout;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comics);


        mapping();
        comicsesViewModel=new ViewModelProvider(this).get(ComicsesViewModel.class);
        recieveData();
        createTabAndViewPager();
        listenerObserver();
        initital();


    }



    private void initital() {


        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                comicsesViewModel.searchContentChapter(apiFirstChapter, new AdapterChapter.DataLoadedCallback() {
                    @Override
                    public void onDataLoaded() {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(DetailComicsActivity.this, ContentChapterActivity.class);
                        comicsesViewModel.getContentChapter().observe(DetailComicsActivity.this, new Observer<ResponeChapter>() {
                            @Override
                            public void onChanged(ResponeChapter chapter) {


                                intent.putExtra("contentChapter",chapter);
                                startActivity(intent);

                                Item itemUpdate=new Item(item.getId(),item.getSlug(),item.getName(),item.getThumbUrl(),item.getViewer(),item.getLike(), item.getUpdatedAt());
                                comicsesViewModel.updateViewAndLike(itemUpdate,true);

                                comicsesViewModel.getContentChapter().removeObserver(this);
                                if(MainAppActivity.getUserCurrent()!=null){
                                    ItemDatabase.addItemIntoDatabase(comicsStory.getData().getItem(), DetailComicsActivity.this,"Readed");
                                }
                                itemUpdate=null;


                            }
                        });
                    }
                });
            }
        });

        imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainAppActivity.getUserCurrent()!=null){
                    comicsesViewModel.clickHeart(comicsStory.getData().getItem().getId(),MainAppActivity.getUserCurrent().getUid());
                    Item itemUpdate=new Item(item.getId(),item.getSlug(),item.getName(),item.getThumbUrl(),item.getViewer(),item.getLike(), item.getUpdatedAt());
                    comicsesViewModel.updateViewAndLike(itemUpdate,false);
                    ItemDatabase.addItemIntoDatabase(comicsStory.getData().getItem(), DetailComicsActivity.this,"Favorite");

                }else{
                    showDialogLogin();
                }

            }
        });

        imgCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainAppActivity.getUserCurrent()!=null){
                    bottomSheetCmt = new CommentBottomSheetFragment(comicsStory.getData().getItem().getId(), comicsesViewModel);

                    bottomSheetCmt.show(getSupportFragmentManager(), "SheetCmt");
                }else{
                    showDialogLogin();
                }

            }

        });


    }

    private void showDialogLogin() {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(this);

        alertDialog.setTitle("Đăng nhập");
        alertDialog.setView(R.layout.dialog_require_login);
        alertDialog.setMessage("Bạn cần đăng nhập để thể sử dụng chức năng này. Hãy đăng nhập ngay nhé");
        alertDialog.setPositiveButton( "Đồng ý",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                       Intent intent=new Intent(DetailComicsActivity.this,LoginActivity.class);
                       startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
            }
        });

        alertDialog.show();

    }

    private void createTabAndViewPager() {


        AdapterVPgInformation adapter = new AdapterVPgInformation(getSupportFragmentManager(), 	getLifecycle(),comicsesViewModel,DetailComicsActivity.this,comicsStory);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {

                tab.setText("Thong tin");
            } else if (position == 1) {

                tab.setText("Chuong");
            }
    }).attach();

    }

    private void listenerObserver() {
        item=comicsStory.getData().getItem();

        Log.d("statehe",1+"");

        if(comicsStory.getData().getItem().getChapters().get(0).getServerData().size()!=0){
            apiFirstChapter=item.getChapters().get(0).getServerData().get(0).getChapterApiData();

        }
        String urlImg= ApiService.BASE_URLIMG+item.getThumbUrl();



        Glide.with(collapsingToolbarImageView.getContext())
                .load(urlImg)

                .into(collapsingToolbarImageView);

        String name=item.getName();
        if(name!=null){
            collapsingToolbarLayout.setTitle(name);
        }


        if(comicsStory!=null){
            if(MainAppActivity.getUserCurrent()!=null){
                comicsesViewModel.getStatusHeart(comicsStory.getData().getItem().getId(),MainAppActivity.getUserCurrent().getUid()).observe(DetailComicsActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean!=null){
                            Log.d("statehe",aBoolean+"");
                            check=aBoolean;
                            if(check) {
                                imgHeart.setImageResource(R.drawable.heartred);
                                imgHeart.setEnabled(false);
                            }
                        }
                    }
                });
            }

        }
    }

    private void recieveData() {

        comicsStory=(Comics) getIntent().getSerializableExtra("comics");
    }

    private void mapping() {

        collapsingToolbarImageView = findViewById(R.id.collapsingToolbarImageView);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tlDetailComics);
        collapsingToolbarLayout=findViewById(R.id.collapsingToolbarLayout);
        viewPager2=findViewById(R.id.vpgInformation);
        btnRead=findViewById(R.id.btnReadComics);
        progressBar=findViewById(R.id.progress_detail);
        progressBar.setIndeterminateDrawable(new FadingCircle());


        imgHeart=findViewById(R.id.imgHeart);
        imgCmt=findViewById(R.id.imgCmt);


    }



    @Override
    protected void onDestroy() {

        Log.d("dess","dess");
        comicsesViewModel.setcomics();
        comicsesViewModel.setHeart();
        comicsesViewModel.setCmt();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle oldInstanceState) {
        super.onSaveInstanceState(oldInstanceState);
        if (oldInstanceState != null) {
            oldInstanceState.clear();
        }

    }
}
