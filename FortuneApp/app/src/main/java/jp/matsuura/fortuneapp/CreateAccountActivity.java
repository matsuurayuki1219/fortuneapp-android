package jp.matsuura.fortuneapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 変数の定義
     */
    private TextView title;
    private Typeface myFont;
    private EditText id;
    private EditText password;
    private Button createButton;
    private TextView errorMessage;
    //FirebaseAuthの変数の定義
    //ユーザのメール認証などを可能にするオブジェクト
    private FirebaseAuth mAuth;

    /**
     * 定数の定義
     */
    //メールアドレスの追加が成功した場合のリクエストコード
    private final int SUCCESS_CODE = 1;
    //メールアドレスの追加が失敗した場合のリクエストコード
    private final int FAILURE_CODE = 9;
    //バックキーを押した場合のリクエストコード
    private final int BACK_CODE = 3;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initComponents();

        init();

    }

    /**
     * Viewの初期化をするメソッド
     */
    private void initComponents() {
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        createButton = findViewById(R.id.createButton);
        title = findViewById(R.id.title);
        progressBar = findViewById(R.id.progressBar);
        errorMessage = findViewById(R.id.errorMessage);
    }

    /**
     * 初期化をするメソッド
     */
    private void init() {
        createButton.setOnClickListener(this);
        myFont = Typeface.createFromAsset(getAssets(), "fonts/yugothicr.ttc");
        title.setTypeface(myFont);
        createButton.setTypeface(myFont);
        progressBar.setVisibility(View.INVISIBLE);
        //FirebaseAuthのインスタンス化
        mAuth = FirebaseAuth.getInstance();

    }

    //バックキーが押されたときに呼ばれるコールバック関数
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        //LoginActivityへバックキーが押されたことを伝える
        setResult(BACK_CODE, intent);
        finish();

    }

    @Override
    public void onClick(View view) {

        errorMessage.setVisibility(View.INVISIBLE);

        //入力したID
        String userId = id.getText().toString();
        //入力したパスワード
        String userPassword = password.getText().toString();

        switch (view.getId()) {

            case R.id.createButton:

                if (userId.equals("") || userPassword.equals("")) {
                    //アドレスもしくはパスワードの記入がなかった場合のみ、エラー情報を表示する
                    errorMessage.setVisibility(View.VISIBLE);

                } else {
                    //アカウントを作成する
                    progressBar.setVisibility(View.VISIBLE);
                    createAccount(userId, userPassword);
                }
        }


    }



    /**
     * アカウントの作成を行うメソッド
     *
     * @param email    メールアドレス
     * @param password パスワード
     */
    private void createAccount(final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.INVISIBLE);

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //DBへメールアドレスとパスワードを保存する
                            //upDateDB(email, password);
                            Intent intent = new Intent();
                            intent.putExtra("email", email);
                            //LoginActivityへアカウント作成が成功したことを伝える
                            setResult(SUCCESS_CODE, intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Intent intent = new Intent();
                            //LoginActivityへアカウント作成が失敗したことを伝える
                            setResult(FAILURE_CODE, intent);
                            finish();
                        }

                    }
                });
    }

}