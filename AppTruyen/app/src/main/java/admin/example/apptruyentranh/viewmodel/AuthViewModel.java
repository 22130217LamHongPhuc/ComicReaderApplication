package admin.example.apptruyentranh.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import admin.example.apptruyentranh.repository.AuthResponsitory;

public class AuthViewModel extends ViewModel {
    private AuthResponsitory authRepository;


    public AuthViewModel() {
        authRepository = AuthResponsitory.getInstance();

    }

    public void signin(String email, String password) {
        authRepository.signin(email, password);
    }

    public void signup(String email, String password) {
        authRepository.signup(email, password);
    }



    public LiveData<Boolean> getLoginStatus() {
        return authRepository.getLoggedStatus();
    }

    public LiveData<Boolean> getSignupStatus() {
        return authRepository.getSignupStatus();
    }
}
