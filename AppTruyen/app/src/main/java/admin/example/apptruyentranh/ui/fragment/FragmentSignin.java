package admin.example.apptruyentranh.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.model.User;
import admin.example.apptruyentranh.viewmodel.AuthViewModel;

public class FragmentSignin extends Fragment {
    EditText editEmail,editPass;
    IMoveSignIn ImoveSignin;
    Button btnSignin;
    Context context;
    AuthViewModel authViewModel;

    public FragmentSignin(AuthViewModel authViewModel, Context context, IMoveSignIn moveSignUp){
        this.ImoveSignin =moveSignUp;
        this.authViewModel=authViewModel;
        this.context=context;

    }
    TextView txtCreateAcc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signin,container,false);


        mapping(view);
        listener();
        return view;
    }

    private void listener() {
        authViewModel.getLoginStatus().observe((LifecycleOwner) context, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if(b!=null){
                    if(b){
                        Toast.makeText(context,"Dang nhap thanh cong",Toast.LENGTH_SHORT).show();
                        ImoveSignin.closeScreen();
                    }else{
                        Toast.makeText(context,"Dang nhap that bai",Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
    }

    private void mapping(View view) {
        txtCreateAcc=view.findViewById(R.id.txtcreateAccount);
        editEmail=view.findViewById(R.id.etxt_email);
        editPass=view.findViewById(R.id.etxt_pass);
        btnSignin=view.findViewById(R.id.btn_signin);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=editEmail.getText().toString();
                String pass=editPass.getText().toString();
                User user= new User(mail,pass);
                if(user.checkData(pass)){
                    authViewModel.signin(mail,pass);

                }else{
                    if(!user.checkPass()){
                        editPass.setError("Nhap mat khau tren 6 ki tu");
                    }else if(!user.checkMail()){
                        editEmail.setError("Vui long nhap dung dinh dang");

                    }
                }
            }
        });


        txtCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImoveSignin.moveScreen();

            }
        });
    }


    public interface IMoveSignIn {
        void moveScreen();
        void closeScreen();
    }
}
