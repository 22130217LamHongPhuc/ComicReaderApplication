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

public class FragmentSignup extends Fragment {

    Context context;

    AuthViewModel authViewModel;
    EditText editEmail,editPass,editRepass;
    Button btnSingup;
    IMoveSignUp moveSignUp;



    public FragmentSignup(AuthViewModel authViewModel, Context context,IMoveSignUp moveSignUp){
        this.authViewModel=authViewModel;
        this.context=context;
        this.moveSignUp=moveSignUp;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signup,container,false);
        mapping(view);
        listenerStatus();
        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=editEmail.getText().toString();
                String pass=editPass.getText().toString();
                String repass=editRepass.getText().toString();
                User user= new User(mail,pass);
                if(user.checkData(repass)){
                    authViewModel.signup(mail,pass);

                }else{
                    if(!user.checkPass()){
                        editPass.setError("Nhap mat khau tren 6 ki tu");
                    }else if(!user.checkMail()){
                        editEmail.setError("Vui long nhap dung dinh dang");

                    }else if(!user.checkRepass(repass)){
                        editRepass.setError("Mat khau khong trung khop");

                    }
                }
            }
        });
        return view;
    }

    private void listenerStatus() {
        authViewModel.getSignupStatus().observe((LifecycleOwner) context, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if(b!=null){
                    if(b){
                        moveSignUp.moveScreen();
                        Toast.makeText(context,"Dang ky thanh cong",Toast.LENGTH_SHORT).show();


                    }else{
                        Toast.makeText(context,"Email da ton tai",Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });
    }

    private void mapping(View view) {
        editRepass=view.findViewById(R.id.etxt_repass);
        editEmail=view.findViewById(R.id.etxt_email);
        editPass=view.findViewById(R.id.etxt_pass);
        btnSingup=view.findViewById(R.id.btn_signup);
    }

    public interface IMoveSignUp {
        void moveScreen();
    }

}
