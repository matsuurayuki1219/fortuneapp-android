package com.example.fortuneapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /** 変数の定義 */
    private TextView title;
    private TextView errorMessage;
    private Button loginButton;
    private EditText password;
    private Typeface fontL;
    private Typeface fontR;
    private LinearLayout mainLayout;
    private InputMethodManager inputMethodManager;

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
    private void initComponents(){
        title = findViewById(R.id.title);
        title = findViewById(R.id.title);
        title = findViewById(R.id.title);
        title = findViewById(R.id.title);
        title = findViewById(R.id.title);
        title = findViewById(R.id.title);

        loginButton = findViewById(R.id.loginButton);
        mainLayout = findViewById(R.id.mainLayout);
        password = findViewById(R.id.password);
        errorMessage = findViewById(R.id.errorMessage);
    }

    /**
     * 初期化するメソッド
     */
    private void init(){
        fontL = Typeface.createFromAsset(getAssets(), "fonts/yugothicl.ttc");
        fontR = Typeface.createFromAsset(getAssets(), "fonts/yugothicr.ttc");
        title.setTypeface(fontR);
        loginButton.setTypeface(fontR);
        errorMessage.setTypeface(fontR);
        //キーボード表示を制御するためのオブジェクト
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        loginButton.setOnClickListener(this);
    }

    /**
     * タッチがされたときに呼ばれるメソッド
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        //キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //背景にフォーカスを移す
        mainLayout.requestFocus();
        return false;
    }

    /**
     * ボタンがクリックされた時に呼ばれるメソッド
     */
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginButton){
            //EditTextに入力した値
            String input = password.getText().toString();
            if(input.equals("000000")) {
                Intent intent = new Intent(getApplication(), HomeActivity.class);
                startActivity(intent);
                errorMessage.setVisibility(View.INVISIBLE);
            }else if(input.equals("")){
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(R.string.errorMessage1);
                password.getEditableText().clear();
            }else{
                errorMessage.setVisibility(View.VISIBLE);
                password.getEditableText().clear();
                errorMessage.setText(R.string.errorMessage2);
            }
        }
    }
}