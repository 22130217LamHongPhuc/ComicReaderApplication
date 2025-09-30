package admin.example.apptruyentranh.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.api.ApiService;
import admin.example.apptruyentranh.api.RetrofitBase;
import admin.example.apptruyentranh.model.Banner;
import admin.example.apptruyentranh.model.Category;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.GetCategory;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    public static boolean isLoadData=false;


    List<Banner> listBanner = new ArrayList<>();
    ComicsesViewModel comicsesViewModel;

    ArrayList<Comics> comicsList = new ArrayList<>();
    ArrayList<Comics> comicsListAnother = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        comicsesViewModel = new ViewModelProvider(SplashActivity.this).get(ComicsesViewModel.class);


        loadData();
    }

    private void loadData() {
        loadBanner();





    }
    private void loadListComics(){
        comicsesViewModel.getListComicsFinish().observe(this, new Observer<Comics>() {
            @Override
            public void onChanged(Comics comics) {
                comicsList.add(comics);
                Log.d("update", comics.getData().getTitlePage());
                Log.d("move", comicsList.size()+"");
                if (comicsList.size() == 4 && comicsListAnother.size()==4 ) {
                    moveScreen();


                }
            }
        });
        comicsesViewModel.getListComicsUpdate().observe(this, new Observer<Comics>() {
            @Override
            public void onChanged(Comics comics) {
                comicsList.add(comics);
                Log.d("update", comics.getData().getTitlePage());
                Log.d("move", comicsList.size()+"");
                if (comicsList.size() == 4 && comicsListAnother.size()==4 ) {
                    moveScreen();


                }
            }
        });


        comicsesViewModel.getListComicsFavorite().observe(this, new Observer<Comics>() {
            @Override
            public void onChanged(Comics comics) {
                comicsList.add(comics);
                Log.d("update", comics.getData().getTitlePage());
                Log.d("move2", comicsList.size()+"");
                if (comicsList.size() == 4 && comicsListAnother.size()==4 ) {
                    moveScreen();


                }
            }
        });

        comicsesViewModel.getListComicsCategory().observe(this, new Observer<Comics>() {
            @Override
            public void onChanged(Comics comics) {

                comicsList.add(comics);
                Log.d("move2", comicsList.size()+"");

                Log.d("update", comics.getData().getTitlePage());
                if (comicsList.size() == 4 && comicsListAnother.size()==4 ) {
                    moveScreen();


                }
            }
        });

        comicsesViewModel.getListComicsAnotherCategory().observe(this, new Observer<List<Comics>>() {
            @Override
            public void onChanged(List<Comics> comics) {

                comicsListAnother.addAll(comics);
                Log.d("move2", comicsList.size()+"");
                if (comicsList.size() == 4 && comicsListAnother.size()==4 ) {
                    moveScreen();


                }
            }
        });
    }

    private void loadBanner() {

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Banner banner = snap.getValue(Banner.class);
                        listBanner.add(banner);

                    }

                }


                loadListComics();
                FirebaseDatabase.getInstance().getReference("Banner").removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        FirebaseDatabase.getInstance().getReference("Banner").addValueEventListener(eventListener);
    }

    private void moveScreen()   {
        Log.d("move", comicsList.size()+"");

                Intent intent = new Intent(SplashActivity.this, MainAppActivity.class);
                intent.putParcelableArrayListExtra("listBanner", (ArrayList<? extends Parcelable>) listBanner);
                intent.putParcelableArrayListExtra("listComicses", (ArrayList<? extends Parcelable>) comicsList);
                intent.putParcelableArrayListExtra("listComicsesAnother", (ArrayList<? extends Parcelable>) comicsListAnother);
                Log.d("yeah",comicsList.size()+"");

                startActivity(intent);
                finish();

            }



}



