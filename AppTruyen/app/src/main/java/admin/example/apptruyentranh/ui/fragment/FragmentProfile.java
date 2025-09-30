package admin.example.apptruyentranh.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import admin.example.apptruyentranh.R;


public class FragmentProfile extends Fragment {

//    IChangeScreen iChangeScreen;
//
//    public FragmentProfile(IChangeScreen iChangeScreen) {
//        this.iChangeScreen = iChangeScreen;
//    }

    View mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_profile, null, false);


        return mainView;
    }


}