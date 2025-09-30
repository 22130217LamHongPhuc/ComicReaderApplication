package admin.example.apptruyentranh.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.dbroom.ItemDatabase;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.model.Banner;
import admin.example.apptruyentranh.model.Category;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.GetCategory;
import admin.example.apptruyentranh.model.ResponeCategory;
import admin.example.apptruyentranh.ui.RatingActivityy;
import admin.example.apptruyentranh.ui.ShowMoreActivity;
import admin.example.apptruyentranh.ui.adapter.AdapterBanner;
import admin.example.apptruyentranh.ui.adapter.AdapterCategoryRd;
import admin.example.apptruyentranh.ui.adapter.AdapterComics;
import admin.example.apptruyentranh.ui.adapter.AdapterVPgerMain;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;
import me.relex.circleindicator.CircleIndicator3;


public class FragmentHome extends Fragment {
    List<Banner> listBanner;
    AdapterComics adapterComicsUdapte;
    ViewPager2 vpg2;
    LinearLayout imgRating;
    ProgressBar progressBar;


    AdapterBanner adapterBanner;
    RecyclerView rlvComicsUpdate;

    AdapterCategoryRd adapterCategoryRd;
    RecyclerView rlvCategoryRd;
    CircleIndicator3 indicator3;
    int currentPage=0;

    List<Comics> listComicses;
    List<Comics> listComicsesAnother;

    ComicsesViewModel comicsesViewModel;
    Context context;
    AdapterVPgerMain adapterVPgerMain;
    private LinearLayout imgRoomChat;
    private TextView txtShowAddupdate;
    ILoadProgress iLoadProgress;


    public FragmentHome(List<Banner> listBanner,List<Comics> comics,List<Comics> listComicsesAnother,ComicsesViewModel comicsesViewModel, Context context,AdapterVPgerMain adapterVPgerMain) {

        this.listBanner = listBanner;
        this.listComicses=comics;
        this.comicsesViewModel=comicsesViewModel;
        this.context=context;
        this.listComicsesAnother=listComicsesAnother;
        this.adapterVPgerMain=adapterVPgerMain;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container);
        mapping(view);
        initial();


        createScroll();

        return view ;
    }

    private void initial() {
        iLoadProgress=new ILoadProgress() {
            @Override
            public void startProgress() {
                progressBar.setVisibility(View.VISIBLE);
                Log.d("statePro",progressBar.getVisibility()+"");

            }

            @Override
            public void endProgress() {
                progressBar.setVisibility(View.GONE);
                Log.d("statePro",progressBar.getVisibility()+"");

            }
        };
        imgRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, RatingActivityy.class));

            }
        });

        txtShowAddupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ShowMoreActivity.class);
                intent.putExtra("Comics", (Serializable) getComics("Truyện Mới"));
                startActivity(intent);
            }
        });
        if (listComicses == null || listComicses.get(0) == null || listComicses.get(0).getData() == null || listComicses.get(0).getData().getItems() == null) {
            Log.e("FragmentHome", "listComicses hoặc một phần tử của nó bị null");
            return;
        }else{


        }
        adapterBanner=new AdapterBanner(listBanner);
        vpg2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vpg2.setAdapter(adapterBanner);
        vpg2.setOffscreenPageLimit(2);
        indicator3.setViewPager(vpg2);
        adapterBanner.registerAdapterDataObserver(indicator3.getAdapterDataObserver());

        adapterComicsUdapte=new AdapterComics(getComics("Truyện Mới").getData().getItems(), AdapterComics.TYPESMALL, getContext(), comicsesViewModel, iLoadProgress);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),4);
        rlvComicsUpdate.setLayoutManager(gridLayoutManager);
        rlvComicsUpdate.setAdapter(adapterComicsUdapte);
        Log.d("datafragment","oke");
        List<Category> categoryList = null;
        GetCategory getCategory=new GetCategory();

        comicsesViewModel.getListCategory().observe((LifecycleOwner) context, (Observer<? super ResponeCategory>) new Observer<ResponeCategory>() {
            @Override
            public void onChanged(ResponeCategory responeCategory) {
                adapterCategoryRd=new AdapterCategoryRd(getCategory.getRandomCategory(responeCategory.getlist()),comicsesViewModel,context,iLoadProgress);
                GridLayoutManager gridLayoutManagerRd=new GridLayoutManager(getContext(),4);
                rlvCategoryRd.setLayoutManager(gridLayoutManagerRd);
                rlvCategoryRd.setAdapter(adapterCategoryRd);
                Log.d("datafragment","oke");

                comicsesViewModel.getListCategory().removeObserver(this);
            }
        });




        FragmentComicsFinish fragmentComicsFinish =new FragmentComicsFinish(comicsesViewModel,iLoadProgress);
        Bundle bundleFinish=new Bundle();
        bundleFinish.putSerializable("comics",getComics("Truyện tranh hoàn thành"));
        loadDataFragment(fragmentComicsFinish,bundleFinish,R.id.fragmentComicsFinish);


        FragmentComicsFavorite fragmentFavorite=new FragmentComicsFavorite(comicsesViewModel,iLoadProgress);
        Bundle bundleFavorite=new Bundle();
        bundleFavorite.putSerializable("comicsesFavorite",getComics("Truyện tranh sắp ra mắt"));
        loadDataFragment(fragmentFavorite,bundleFavorite,R.id.fragmentComicsFavorite);


        FragmentCategory fragmentCategory=new FragmentCategory(comicsesViewModel,context,iLoadProgress);
        Bundle bundleCategory=new Bundle();
        bundleCategory.putSerializable("comics",getComics("Action"));
        Log.d("cate",getComics("Action").getData().getTitlePage());

        loadDataFragment(fragmentCategory,bundleCategory,R.id.fragmentComicsCategory);

        FragmentReadContinue fragmentReadContinue=new FragmentReadContinue(comicsesViewModel,iLoadProgress);
        Bundle bundleReadCon=new Bundle();
        bundleReadCon.putParcelableArrayList("comics", (ArrayList<? extends Parcelable>) ItemDatabase.getInstance(context).ItemDao().getListComicss("Readed"));
        loadDataFragment(fragmentReadContinue,bundleReadCon,R.id.fragmentReadCotinue);

        FragmentComicsAnotherCate fragmentComicsAnotherCate=new FragmentComicsAnotherCate(comicsesViewModel,context,iLoadProgress);
        Bundle bundleAnotherCate=new Bundle();
        bundleAnotherCate.putParcelableArrayList("comics", (ArrayList<? extends Parcelable>) listComicsesAnother);
        loadDataFragment(fragmentComicsAnotherCate,bundleAnotherCate,R.id.fragmentComicsAnotherCate);


    }

    private void mapping(View view) {
        vpg2=view.findViewById(R.id.vpgBanner);
        indicator3=view.findViewById(R.id.indicator);
        rlvComicsUpdate=view.findViewById(R.id.rlvComicsUpdate);
        rlvCategoryRd=view.findViewById(R.id.rlvCatogoryRd);
        imgRating=view.findViewById(R.id.layoutRating);
        txtShowAddupdate=view.findViewById(R.id.txtComicsUpdateShowAdd);
        imgRoomChat=view.findViewById(R.id.layoutRoomChat);
        progressBar=view.findViewById(R.id.progress_home);


    }

    private void createScroll() {
        Handler handler=new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == adapterBanner.getItemCount()) {
                    currentPage = 0;
                }
                currentPage++;
                vpg2.setCurrentItem(currentPage, true);
                handler.postDelayed(this, 2200); // 3000 milliseconds = 3 seconds
            }
        };

        handler.postDelayed(runnable,2900);
    }
    void loadDataFragment(Fragment fragment,Bundle bundle,int id){
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();

    }


    Comics getComics(String title){


        for(Comics comics:listComicses){
            if(comics.getData().getTitlePage().equalsIgnoreCase(title)){
                return comics;
            }
        }
        return null;
    }

    @Override
    public void onStop() {
        progressBar.setVisibility(View.GONE);
        super.onStop();
    }
}