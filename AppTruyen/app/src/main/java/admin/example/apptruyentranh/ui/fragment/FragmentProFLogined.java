package admin.example.apptruyentranh.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.dbroom.ItemDAO;
import admin.example.apptruyentranh.dbroom.ItemDatabase;
import admin.example.apptruyentranh.ui.MainAppActivity;
import admin.example.apptruyentranh.ui.ShowMoreActivity;
import admin.example.apptruyentranh.ui.adapter.AdapterVPgerMain;

public class FragmentProFLogined extends Fragment {
    View mainView;
    ProgressBar progressBar;
    RelativeLayout layoutHistory, layoutChangePw, layoutLogout;
    TextView txtUsername;
   AdapterVPgerMain adapterVPgerMain;

    public FragmentProFLogined(AdapterVPgerMain adapterVPgerMain){
        this.adapterVPgerMain=adapterVPgerMain;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_prof_logined, null, false);
        mappingVer2(mainView);
        initial();
        return  mainView;
    }



    private void mappingVer2(View view) {
        layoutChangePw = view.findViewById(R.id.layout_changePassword);
        layoutHistory = view.findViewById(R.id.layout_historyProfile);
        layoutLogout = view.findViewById(R.id.layout_signoutProfile);
        txtUsername = view.findViewById(R.id.etxt_usernameProfile);
        progressBar=view.findViewById(R.id.progressLogin);
        progressBar.setIndeterminateDrawable(new FadingCircle());
    }

    private void initial() {
       txtUsername.setText(MainAppActivity.getUserCurrent().getEmail());
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                FirebaseAuth.getInstance().signOut();
                adapterVPgerMain.changeFragment(false,1);
                adapterVPgerMain.changeFragment(false,3);
                progressBar.setVisibility(View.GONE);

            }
        });

        layoutHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ShowMoreActivity.class);
                ArrayList<ItemDAO> items= (ArrayList<ItemDAO>) ItemDatabase.getInstance(getContext()).ItemDao()
                        .getListComicss("Readed");
                Log.d("listDaooo",items.size()+"");
                intent.putParcelableArrayListExtra("listDao",items);
                startActivity(intent);

            }
        });


    }
}
