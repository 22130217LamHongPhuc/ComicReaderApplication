package admin.example.apptruyentranh.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.model.Item;
import admin.example.apptruyentranh.ui.adapter.AdapterComics;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class RatingActivityy extends AppCompatActivity {

    private AdapterComics adapterComics;
    List<Item> itemsList=new ArrayList<>();
    private ComicsesViewModel comicsesViewModel;
    private RecyclerView rlvRating;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ProgressBar progress;
    private TabLayout tab;
    int currentTab=1;
    private TextView txt_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_activityy);
        comicsesViewModel=new ViewModelProvider(this).get(ComicsesViewModel.class);
        mapping();
        initial();
        loadData();
    }

    private void initial() {
        shimmerFrameLayout.startShimmer();
        adapterComics = new AdapterComics(itemsList, AdapterComics.TYPERATING, this, comicsesViewModel, new ILoadProgress() {
            @Override
            public void startProgress() {
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void endProgress() {
                progress.setVisibility(View.GONE);
            }
        });

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rlvRating.setLayoutManager(layoutManager);
        rlvRating.setHasFixedSize(true);
        rlvRating.setAdapter(adapterComics);

        tab.addTab(tab.newTab().setText("Lượt xem"));
        tab.addTab(tab.newTab().setText("Yêu thích"));

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equalsIgnoreCase("Lượt xem") && currentTab != 1) {
                    itemsList.sort(new Comparator<Item>() {
                        @Override
                        public int compare(Item t1, Item t2) {
                            return Integer.compare(t2.getViewer(), t1.getViewer());
                        }
                    });
                    adapterComics.setData(itemsList);
                    currentTab = 1;
                } else if (tab.getText().toString().equalsIgnoreCase("Yêu thích") && currentTab != 2) {
                    itemsList.sort(new Comparator<Item>() {
                        @Override
                        public int compare(Item t1, Item t2) {
                            return Integer.compare(t2.getLike(), t1.getLike());
                        }
                    });
                    adapterComics.setData(itemsList);
                    currentTab = 2;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });



    }


    private void loadData() {

        comicsesViewModel.getTopComics().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items != null && items.size() != 0) {


                    items.sort(new Comparator<Item>() {
                        @Override
                        public int compare(Item t1, Item t2) {
                            if (t1.getViewer() > t2.getViewer()) {
                                return -1;
                            }
                            return 1;
                        }
                    });
                    itemsList=items;
                    adapterComics.setData( itemsList);
                    txt_rating.setVisibility(View.VISIBLE);
                    tab.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.hideShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    comicsesViewModel.getTopComics().removeObserver(this);
                }


            }
        });


    }

    private void mapping() {
        rlvRating = findViewById(R.id.rlvRating);
        shimmerFrameLayout = findViewById(R.id.skeleton_rating);
        progress = findViewById(R.id.progress_rating);
        tab=findViewById(R.id.tlRating);
        txt_rating=findViewById(R.id.txtRating);
    }

}