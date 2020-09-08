package com.example.fortuneapp.D;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fortuneapp.CustomView.MyRouletteView;
import com.example.fortuneapp.MyAnimation.MyRouletteAnimation;
import com.example.fortuneapp.R;

public class AppD01Activity extends AppCompatActivity implements Animation.AnimationListener, View.OnTouchListener{

    /** 変数の定義 */
    private TextView title;
    private Typeface fontL;
    private Typeface fontR;
    private LinearLayout mainLayout;

    //ルーレットView
    private MyRouletteView rouleteView;
    //アニメーションの時間
    private final int ANIMATION_TIME = 5000;
    //ルーレットの項目数
    private final int NUM = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_d01);

        initComponents();

        init();
    }

    /**
     * Viewを初期化するメソッド
     */
    private void initComponents(){
        title = findViewById(R.id.title);
        rouleteView = findViewById(R.id.roulette);
        mainLayout = findViewById(R.id.mainLayout);

    }

    /**
     * 初期化するメソッド
     */
    private void init(){
        fontL = Typeface.createFromAsset(getAssets(), "fonts/yugothicl.ttc");
        fontR = Typeface.createFromAsset(getAssets(), "fonts/yugothicr.ttc");
        title.setTypeface(fontR);
        mainLayout.setOnTouchListener(this);


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        MyRouletteAnimation animation = new MyRouletteAnimation(rouleteView, NUM);

        //アニメーションの時間を設定
        animation.setDuration(ANIMATION_TIME);
        //アニメーションを開始
        rouleteView.startAnimation(animation);
        //アニメーションにリスナを登録
        animation.setAnimationListener(this);

        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}