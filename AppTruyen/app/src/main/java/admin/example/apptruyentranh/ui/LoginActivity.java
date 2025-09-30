package admin.example.apptruyentranh.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.ui.fragment.FragmentSignin;
import admin.example.apptruyentranh.ui.fragment.FragmentSignup;
import admin.example.apptruyentranh.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    FragmentSignin fragmentSignin;
    FragmentSignup fragmentSignup;
    AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authViewModel=new ViewModelProvider(this).get(AuthViewModel.class);

         fragmentSignin = new FragmentSignin(authViewModel,LoginActivity.this,new FragmentSignin.IMoveSignIn() {
            @Override
            public void moveScreen() {
                // Tạo một FragmentTransaction mới khi thực hiện thay thế Fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FragmentSignup fragmentSignup=new FragmentSignup(authViewModel, LoginActivity.this, new FragmentSignup.IMoveSignUp() {
                    @Override
                    public void moveScreen() {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentlogin, fragmentSignin)
                                 .addToBackStack(null)
                                .commit();

                    }
                });

                transaction.replace(R.id.fragmentlogin,fragmentSignup);
                transaction.addToBackStack(null);
                transaction.commit();

            }

             @Override
             public void closeScreen() {


                 setResult(Activity.RESULT_OK);

                finish();

             }
         });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentlogin, fragmentSignin)
                .commit();
    }
}