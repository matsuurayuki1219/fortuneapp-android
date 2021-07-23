package jp.matsuura.fortuneapp;

import com.google.firebase.database.Exclude;

//DBへ様々な情報を保存するためのUserクラス

public class User {
    private String email;

    public User(String email){
        this.email = email;

    }

    public String getEmail(){
        return email;
    }

    public void setEmail(){
        this.email = email;
    }
}
