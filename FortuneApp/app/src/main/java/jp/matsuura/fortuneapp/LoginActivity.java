package jp.matsuura.fortuneapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import jp.matsuura.fortuneapp.Utilize.Util;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /** 変数の定義 */
    private Typeface myFont;
    private TextView title;
    private TextView errorMessage;
    private Button loginButton;
    private EditText userId;
    private EditText userPassword;
    private LinearLayout main;
    private InputMethodManager inputMethodManager;
    private TextView createText;
    private TextView toHome;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    /** 定数の定義 */
    //メールアドレスの追加が成功した場合のリクエストコード
    private final int SUCCESS_CODE = 1;
    //メールアドレスの追加が失敗した場合のリクエストコード
    private final int FAILURE_CODE = 9;
    //バックキーを押した場合のリクエストコード
    private final int BACK_CODE = 3;
    //CreateActivityへ遷移するときのコード
    private final int CREATEACTIVITY_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        init();

    }


    /**
     * Viewを初期化するメソッド
     */
    private void initComponents() {

        title = findViewById(R.id.title);
        loginButton = findViewById(R.id.loginButton);
        main = findViewById(R.id.main);
        userId = findViewById(R.id.id);
        userPassword = findViewById(R.id.password);
        errorMessage = findViewById(R.id.errorMessage);
        createText = findViewById(R.id.createAccount);
        progressBar = findViewById(R.id.progressBar);
        toHome = findViewById(R.id.toHome);

    }

    /**
     * 初期化するメソッド
     */
    private void init() {

        //キーボード表示を制御するためのオブジェクト
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        loginButton.setOnClickListener(this);
        myFont = Typeface.createFromAsset(getAssets(), "fonts/yugothicr.ttc");
        title.setTypeface(myFont);
        loginButton.setTypeface(myFont);
        createText.setOnClickListener(this);
        toHome.setOnClickListener(this);
        progressBar.setVisibility(View.INVISIBLE);
        //FierbaseAuthのインスタンス化
        mAuth = FirebaseAuth.getInstance();

    }



    /**
     * タッチがされたときに呼ばれるメソッド
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        //キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(main.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //背景にフォーカスを移す
        main.requestFocus();

        return true;
    }

    /**
     * ボタンがクリックされた時に呼ばれるメソッド
     */
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.loginButton) {
            //EditTextに入力した値
            String enterId = userId.getText().toString();
            String enterPassword = userPassword.getText().toString();

            //何も入力していない場合
            if (enterId.equals("") || enterPassword.equals("")) {

                errorMessage.setVisibility(View.VISIBLE);
                userId.getEditableText().clear();
                userPassword.getEditableText().clear();
                errorMessage.setText(R.string.errorMessage1);
                return;

            }else{

                progressBar.setVisibility(View.VISIBLE);
                signIn(enterId, enterPassword);

            }

        } else if (view.getId() == R.id.createAccount) {
            Intent intent = new Intent(getApplication(), CreateAccountActivity.class);
            startActivityForResult(intent, CREATEACTIVITY_CODE);
        } else if(view.getId() == R.id.toHome){

            Intent intent = new Intent(getApplication(), HomeActivity.class);
            Util.doRegisterAccount = false;
            startActivity(intent);
            finish();
        }
    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(getApplication(), HomeActivity.class);
                            Util.doRegisterAccount = true;
                            startActivity(intent);
                            finish();
                            errorMessage.setVisibility(View.INVISIBLE);

                        } else {

                            errorMessage.setVisibility(View.VISIBLE);
                            userId.getEditableText().clear();
                            userPassword.getEditableText().clear();
                            errorMessage.setText(R.string.errorMessage2);

                        }

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    //バックキーが押されたときに呼ばれるコールバック関数
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("debug", "LoginActivity:アクティビティが破棄されました");
    }

    //アクティビティのインスタンスが存在している場合に呼ばれるコールバック関数
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent date) {
        super.onActivityResult(requestCode, resultCode, date);

        progressBar.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);

        switch (requestCode) {

            case CREATEACTIVITY_CODE:
                //CreateAcitivtyからの結果の通知のとき

                if (resultCode == SUCCESS_CODE) {
                    //アカウントが作成できた時

                    //ダイアログを表示する
                    new AlertDialog.Builder(this)
                            .setTitle("アカウントの作成が成功")
                            .setMessage("アカウントの作成が完了しました。")
                            .setPositiveButton("確認", null)
                            .show();

                    String email = date.getStringExtra("email");
                    upDateDB(email, "aaaa");


                } else if(resultCode == BACK_CODE) {

                }else {
                    //アカウントが作成失敗したとき

                    //ダイアログを表示する
                    new AlertDialog.Builder(this)
                            .setTitle("アカウントの作成が失敗")
                            .setMessage("アカウントの作成が失敗しました。")
                            .setPositiveButton("確認", null)
                            .show();
                }

                break;

        }
    }

    /**
     * ユーザ情報を格納するDBへ保存する
     */
    private void upDateDB(String email, String password) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        int uid = email.hashCode();
        DatabaseReference reference = database.getReference();
        User user = new User(email);
        reference.child("users").child(String.valueOf(uid)).setValue(user);
    }


}