package jp.matsuura.fortuneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class OtherActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signOutButton;
    private Button deleteButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        initComponents();
        init();

    }

    private void initComponents(){
        signOutButton = findViewById(R.id.signOutButton);
        deleteButton = findViewById(R.id.deleteButton);
        progressBar = findViewById(R.id.progressBar);
    }

    private void init(){
        signOutButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {

        } else {

            switch (view.getId()) {

                case R.id.signOutButton:
                    signOut();
                    break;

                case R.id.deleteButton:
                    deleteAccount(user);
                    break;
            }

        }

    }

    /**
     * サインアウトをするC:\Users\matsu\AppData\Local\Android\Sdk\platform-tools
     */
    private void signOut(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("サインアウトを本当に行いますか？").setTitle("サインアウトの確認");
        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK buttonadb shell dumpsys activity

                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
                finish();
                FirebaseAuth.getInstance().signOut();
            }
        });
        builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("debug", "破棄されました");
    }

    /**
     * 現在ログインしているアカウントを削除する
     */
    private void deleteAccount(final FirebaseUser user){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("アカウントの削除を本当に行いますか？").setTitle("アカウント削除の確認");
        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                progressBar.setVisibility(View.VISIBLE);
                //DB情報を削除
                deleteDB();


                // User clicked OK button
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Intent intent = new Intent(getApplication(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
            }
        });
        builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * ユーザ情報をDBから削除する
     */
    private void deleteDB() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //ユーザ情報と紐づけた番号
        int uid = mAuth.getCurrentUser().getEmail().hashCode();
        DatabaseReference reference = database.getReference();
        reference.child("users").child(String.valueOf(uid)).setValue(null);

    }

    //バックキーが押されたときに呼ばれるコールバック関数
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplication(), HomeActivity.class);
        startActivity(intent);
        finish();

    }

}