package admin.example.apptruyentranh.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.airbnb.lottie.animation.content.Content;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.ui.LoginActivity;
import admin.example.apptruyentranh.ui.adapter.AdapterComics;
import admin.example.apptruyentranh.ui.adapter.AdapterVPgerMain;
import admin.example.apptruyentranh.viewmodel.AuthViewModel;

public class FragmentNotLogin extends Fragment {
    Button btn;
    private int REQUESTCODE=111;
   AdapterVPgerMain adapterVPgerMain;
   AuthViewModel authViewModel;
   Context context;

    public FragmentNotLogin( AdapterVPgerMain adapterVPgerMain,Context context){
        this.adapterVPgerMain=adapterVPgerMain;
        this.context=context;
        authViewModel= new ViewModelProvider((ViewModelStoreOwner) context).get(AuthViewModel.class);
    }
    public FragmentNotLogin(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_love_notlogin,null,false);
        btn=view.findViewById(R.id.btn_loginfragLove);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mapp", "Button clicked, starting LoginActivity.");
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent,REQUESTCODE);
            }
        });

        listenerUserLogin();
        return view;
    }

    private void listenerUserLogin() {


        authViewModel.getLoginStatus().observe((LifecycleOwner) context, new Observer<Boolean>() {





            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    adapterVPgerMain.changeFragment(true,1);
                    adapterVPgerMain.changeFragment(true,3);

                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUESTCODE && resultCode== Activity.RESULT_OK){
            this.adapterVPgerMain.changeFragment(true,1);
            this.adapterVPgerMain.changeFragment(true,3);

        }
    }

}
