package admin.example.apptruyentranh.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.model.Banner;
import admin.example.apptruyentranh.model.Category;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.ui.adapter.AdapterVPgerMain;
import admin.example.apptruyentranh.ui.fragment.FragmentInformation;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class MainAppActivity extends AppCompatActivity {
    ViewPager2 vger2;
    ArrayList<Banner> bannerList;
    TabLayout tab;

    ArrayList<Comics> comicsList;
    ArrayList<Comics> comicsListAnother;
    ComicsesViewModel comicsesViewModel;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        mapping();
        receiveData();
        createTransportVgerAndTab();
    }

    private void receiveData() {
        bannerList = getIntent().getParcelableArrayListExtra("listBanner");
        comicsList = getIntent().getParcelableArrayListExtra("listComicses");
        comicsListAnother=getIntent().getParcelableArrayListExtra("listComicsesAnother");

        if (bannerList != null && comicsList != null && comicsListAnother!=null )  {
          Log.d("yeah",comicsListAnother.size()+"");
        } else {
            Log.e("Error", "Dữ liệu bannerList hoặc comicsList bị null");
        }
    }


    private void mapping() {
        vger2=findViewById(R.id.vpgBanner);
        tab=findViewById(R.id.tlBanner);
        vger2.setOffscreenPageLimit(2);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        comicsesViewModel=new ViewModelProvider(this).get(ComicsesViewModel.class);


    }

    private void createTransportVgerAndTab() {
        AdapterVPgerMain adapter = new AdapterVPgerMain(getSupportFragmentManager(),getLifecycle(),bannerList,comicsList,comicsListAnother,comicsesViewModel,MainAppActivity.this);
        vger2.setAdapter(adapter);
        new TabLayoutMediator(tab, vger2, (tab, position) -> {
            if (position == 0) {

                tab.setIcon(R.drawable.baseline_home_24);
            } else if (position == 1) {

                tab.setIcon(R.drawable.baseline_favorite_border_24);

            } else if (position == 2) {

                tab.setIcon(R.drawable.baseline_search_24);


            } else if (position == 3) {

                tab.setIcon(R.drawable.baseline_account_circle_24);


            }

            if (position == vger2.getCurrentItem()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tab.getIcon().setColorFilter(getColor(R.color.isSelect), PorterDuff.Mode.SRC_IN);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tab.getIcon().setColorFilter(getColor(R.color.notSelect), PorterDuff.Mode.SRC_IN);
                }
            }
        }).attach();

        vger2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for (int i = 0; i < tab.getTabCount(); i++) {
                    if(i==position){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tab.getTabAt(i).getIcon().setColorFilter(getColor(R.color.isSelect), PorterDuff.Mode.SRC_IN);
                        }

                    }else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tab.getTabAt(i).getIcon().setColorFilter(getColor(R.color.notSelect), PorterDuff.Mode.SRC_IN);
                        }

                    }
                }
            }
        });


    }
    public static FirebaseUser getUserCurrent(){

        return FirebaseAuth.getInstance().getCurrentUser();
    }

}