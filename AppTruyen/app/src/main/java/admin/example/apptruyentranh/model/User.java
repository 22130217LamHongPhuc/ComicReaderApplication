package admin.example.apptruyentranh.model;

import android.text.TextUtils;
import android.util.Patterns;

public class User {
    private String email;
    private String password;
    private String id;

    // Constructor, getters and setters

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password,String id) {
        this.email = email;
        this.password = password;
        this.id=id;
    }

    public User(){

    }
    public boolean checkData(String repass){
        return checkMail() && checkPass() && checkRepass(repass);
    }

    public boolean checkRepass(String repass) {
        return this.password.equals(repass);
    }

    public boolean checkPass() {
        return !this.password.isEmpty() && this.password.length()>=6;
    }

    public boolean checkMail() {
        return !this.email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
