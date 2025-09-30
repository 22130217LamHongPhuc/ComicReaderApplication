package admin.example.apptruyentranh.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import admin.example.apptruyentranh.model.User;

public class AuthResponsitory {

    private FirebaseAuth firebaseAuth;
    public static AuthResponsitory authCurrent;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<Boolean> loggedStatus;

    private MutableLiveData<Boolean> signupStatus;

    private AuthResponsitory() {
        firebaseAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        loggedStatus = new MutableLiveData<>();
        signupStatus=new MutableLiveData<>();
    }


    public static AuthResponsitory getInstance(){
        if(authCurrent==null){
            authCurrent=new AuthResponsitory();
        }
        return authCurrent;
    }

    public void signin(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loggedStatus.setValue(true);

                    } else {
                        loggedStatus.postValue(false);
                    }
                });
    }


    public void signup(String email, String password) {

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    signupStatus.setValue(true);
                    createUserFirebase(email,password);
                    
                }else{
                    Log.d("mai;","s");
                    signupStatus.setValue(false);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signupStatus.setValue(false);

            }
        });
    }

    private void createUserFirebase(String email, String password) {
        User user=new User(FirebaseAuth.getInstance().getCurrentUser().getUid(),email, password);
        FirebaseDatabase.getInstance().getReference("User").child(user.getId()).setValue(user);
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Boolean> getLoggedStatus() {
        return loggedStatus;
    }

    public MutableLiveData<Boolean> getSignupStatus() {
        return signupStatus;
    }
}
