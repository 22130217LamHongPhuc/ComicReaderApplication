package admin.example.apptruyentranh.ui.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import admin.example.apptruyentranh.model.Banner;
import admin.example.apptruyentranh.model.Category;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.ui.MainAppActivity;
import admin.example.apptruyentranh.ui.fragment.FragmentFavorite;
import admin.example.apptruyentranh.ui.fragment.FragmentHome;
import admin.example.apptruyentranh.ui.fragment.FragmentNotLogin;
import admin.example.apptruyentranh.ui.fragment.FragmentProFLogined;
import admin.example.apptruyentranh.ui.fragment.FragmentSearch;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class AdapterVPgerMain extends FragmentStateAdapter {
    private List<Banner> listBanner;
    private List<Comics> comicsList;
    private List<Comics> comicsListAnother;

    private List<Fragment> fragmentList;
    private ComicsesViewModel comicsesViewModel;
    private Context context;

    public AdapterVPgerMain(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Banner> listBanner, List<Comics> listComicses, List<Comics> listComicsesAnother, ComicsesViewModel comicsesViewModel, MainAppActivity mainAppActivity) {
        super(fragmentManager, lifecycle);
        this.comicsList = listComicses;
        this.listBanner = listBanner;
        this.comicsesViewModel = comicsesViewModel;
        this.context = mainAppActivity;
        this.fragmentList = new ArrayList<>();

        this.comicsListAnother=listComicsesAnother;
        addFragmentForList();
    }

    private void addFragmentForList() {
        fragmentList.add(new FragmentHome(listBanner, comicsList,comicsListAnother, comicsesViewModel, context,this));
        fragmentList.add(createFavoriteFragment());
        fragmentList.add(new FragmentSearch(comicsList, comicsesViewModel, context));
        fragmentList.add(createProfileFragment());
        Log.d("listfra", fragmentList.size() + "");
    }

    private Fragment createProfileFragment() {
        if (MainAppActivity.getUserCurrent() == null) {
            return new FragmentNotLogin(this,context);
        } else {
            return new FragmentProFLogined(this);
        }
    }


    private Fragment createFavoriteFragment() {
        if (MainAppActivity.getUserCurrent() == null) {
            return new FragmentNotLogin(this,context);
        } else {
            return new FragmentFavorite(comicsesViewModel,context);
        }
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    @Override
    public long getItemId(int position) {
        return fragmentList.get(position).hashCode();
    }

    @Override
    public boolean containsItem(long itemId) {
        for (Fragment fragment : fragmentList) {
            if (fragment.hashCode() == itemId) {
                return true;
            }
        }
        return false;
    }

    public void changeFragment(boolean isLogin, int position) {

        Fragment newFragment;
        if(position==1){
            if (isLogin) {
                newFragment = new FragmentFavorite(comicsesViewModel,context);
            } else {
                newFragment = new FragmentNotLogin(this,context);

            }
        }else{
            if (isLogin) {
                newFragment = new FragmentProFLogined(this);
            } else {
                newFragment = new FragmentNotLogin(this,context);

            }
        }


        fragmentList.set(position, newFragment);
        notifyItemChanged(position); // Notify the adapter to refresh the items
    }
}
