package admin.example.apptruyentranh.ui;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.dbroom.ItemDAO;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.Item;
import admin.example.apptruyentranh.ui.adapter.AdapterComics;
import admin.example.apptruyentranh.ui.adapter.AdapterComicsDao;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class ShowMoreActivity extends AppCompatActivity {
    RecyclerView rlvRating;
    AdapterComics adapterComics;
    int index = 1;
    Comics comicsq;
    ComicsesViewModel comicsesViewModel;
    String slug;
    List<Item> items=new ArrayList<>();
    Comics comics = null;
    TextView txtType;
    int position = 0;
    int indexPage = 1;

    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayoutManager layoutManage;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private ProgressBar progress;
    ArrayList<ItemDAO> listDaoItem;
    boolean isHistory = false;
    boolean isRating=false;
    AdapterComicsDao adapterComicsDao;

    private MutableLiveData<String> liveData = new MutableLiveData<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmore_rating);
        mapping();
        initial();
        recieve();



        shimmerFrameLayout.startShimmer();

        if (shimmerFrameLayout.isShimmerStarted()) {
            Log.d("shimer", "start");

        }
        if (shimmerFrameLayout.isShimmerVisible()) {
            Log.d("shimer", "visible");

        }


    }

    private void listenObserver() {
        if (comics.getData().getTitlePage().equalsIgnoreCase("Truyện Mới")) {
            comicsesViewModel.getListComicsUpdate().observe(this, new Observer<Comics>() {
                @Override
                public void onChanged(Comics comics) {
                    Log.d("sss", comics.getData().getTitlePage());
                    index++;
                    Log.d("sss", indexPage + "");
                    if (comics != null) {
                        // Sử dụng Handler để hoãn thay đổi dữ liệu
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {

                                setDataComics(comics);
                            }
                        });
                    }

                }
            });
        } else if (comics.getData().getTitlePage().equalsIgnoreCase("Truyện tranh hoàn thành")) {

            comicsesViewModel.getListComicsFinish().observe(this, new Observer<Comics>() {
                @Override
                public void onChanged(Comics comics) {
                    Log.d("sss", comics.getData().getTitlePage());
                    if (comics != null) {
                        // Sử dụng Handler để hoãn thay đổi dữ liệu
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                setDataComics(comics);
                            }
                        });
                    }

                }
            });
        } else if (comics.getData().getTitlePage().equalsIgnoreCase("Truyện tranh sắp ra mắt")) {
            comicsesViewModel.getListComicsFavorite().observe(this, new Observer<Comics>() {
                @Override
                public void onChanged(Comics comics) {
                    Log.d("sss", comics.getData().getTitlePage());
                    if (comics != null) {
                        // Sử dụng Handler để hoãn thay đổi dữ liệu
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                setDataComics(comics);
                            }
                        });
                    }

                }
            });
        } else {
            if (slug != null) {

                comicsesViewModel.getListComicsCategory().observe(this, new Observer<Comics>() {
                    @Override
                    public void onChanged(Comics comics) {
                        Log.d("sss", comics.getData().getTitlePage());
                        Log.d("sss", indexPage + "");
                        if (comics != null) {
                            for (int i = 0; i < comics.getData().getItems().size(); i++) {
                                Log.d("ttt", comics.getData().getItems().get(i).getName());
                            }
                            // Sử dụng Handler để hoãn thay đổi dữ liệu
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    setDataComics(comics);
                                }
                            });
                        }


                    }

                });

            }
        }
    }

    private void recieve() {
        comics = (Comics) getIntent().getSerializableExtra("Comics");
        slug = getIntent().getStringExtra("slug");
        listDaoItem = getIntent().getParcelableArrayListExtra("listDao");




       if (listDaoItem != null) {
            isHistory = true;
            txtType.setText("Đã đọc");
        } else if(comics!=null){
            if (comics.getData().getTitlePage().equalsIgnoreCase("Truyện Mới")) {
                txtType.setText(comics.getData().getTitlePage());
            } else if (comics.getData().getTitlePage().equalsIgnoreCase("Truyện tranh hoàn thành")) {
                txtType.setText(comics.getData().getTitlePage());
            } else if (comics.getData().getTitlePage().equalsIgnoreCase("Có thể bạn thích")) {
                txtType.setText(comics.getData().getTitlePage());
            } else {
                Log.d("titleee", comics.getData().getTitlePage());
                txtType.setText(comics.getData().getTitlePage());
            }


        }

        if(isHistory){
            rlvRating.setAdapter(adapterComicsDao);
            adapterComicsDao.setData(listDaoItem);


        }else{
            rlvRating.setAdapter(adapterComics);
            adapterComics.setType(AdapterComics.TYPECATEGORYS);
            items = comics.getData().getItems();
            adapterComics.setData(items);
            listenObserver();
            setScrollLoadComics();
        }
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.hideShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }


    private void setScrollLoadComics() {
        rlvRating.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItem = layoutManage.getItemCount();
                int first = layoutManage.findFirstVisibleItemPosition();
                int visible = layoutManage.getChildCount();
                Log.d("src", first + " - " + visible + " - " + totalItem);

                if (first >= 0 && first + visible > totalItem - 6 && !isLastPage && !isLoading) {
                    loadMore();

                }
            }
        });
    }

    private void loadMore() {
        Log.d("sss", "sstart");

        indexPage++;
        isLoading = true;

        if (comics.getData().getTitlePage().equalsIgnoreCase("Truyện Mới")) {
            comicsesViewModel.getListStateComics(indexPage, "Update");
        } else if (comics.getData().getTitlePage().equalsIgnoreCase("Truyện tranh hoàn thành")) {
            comicsesViewModel.getListStateComics(indexPage, "Finish");
        } else if (comics.getData().getTitlePage().equalsIgnoreCase("Truyện tranh sắp ra mắt")) {
            comicsesViewModel.getListStateComics(indexPage, "Favorite");

        } else {
            Log.d("titleee", comics.getData().getTitlePage());
            comicsesViewModel.searchComicsToCategory(slug, indexPage);
        }

    }


    private void setDataComics(Comics comics) {
        comicsq = comics;
        List<Item> listLoad = comics.getData().getItems();

        if (listLoad.size() != 0) {
//            items.addAll(listLoad);
            Log.d("sss", adapterComics.getList().size() + "");

            adapterComics.setListUpdate(listLoad);

            int oldPosition = position;
            position += listLoad.size();
            listLoad = null;
            isLoading = false;
        } else {
            isLastPage = true;
        }


    }

    private void mapping() {
        rlvRating = findViewById(R.id.rlvShowMore);
        txtType = findViewById(R.id.txtTypeComics);
        shimmerFrameLayout = findViewById(R.id.skeleton_showmore);
        progress = findViewById(R.id.progress_rating);

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
                    ShowMoreActivity.this.items=items;
                    adapterComics.setData( ShowMoreActivity.this.items);
                    comicsesViewModel.getTopComics().removeObserver(this);
                }


            }
        });


    }

    private void initial() {
        comicsesViewModel = new ViewModelProvider(this).get(ComicsesViewModel.class);



        adapterComics = new AdapterComics(items, AdapterComics.TYPERATING, this, comicsesViewModel, new ILoadProgress() {
            @Override
            public void startProgress() {
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void endProgress() {
                progress.setVisibility(View.GONE);
            }
        });

        adapterComicsDao = new AdapterComicsDao(listDaoItem, this, AdapterComicsDao.TypeLong, comicsesViewModel, new ILoadProgress() {
            @Override
            public void startProgress() {
                progress.setVisibility(View.VISIBLE);

            }

            @Override
            public void endProgress() {
                progress.setVisibility(View.GONE);

            }
        });


        layoutManage = new LinearLayoutManager(this);
        rlvRating.setLayoutManager(layoutManage);
        rlvRating.setHasFixedSize(true);


    }

}
