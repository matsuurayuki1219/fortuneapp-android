package jp.matsuura.fortuneapp.D;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.matsuura.fortuneapp.MyCustomView.MyRouletteView;
import jp.matsuura.fortuneapp.MyAnimation.MyRouletteAnimation;
import jp.matsuura.fortuneapp.R;

public class AppD01Activity extends AppCompatActivity implements Animation.AnimationListener, View.OnTouchListener, View.OnClickListener {

    /** 変数の定義 */
    private TextView title;
    private LinearLayout mainLayout;
    private Button homeButton;

    private MyRouletteAnimation animation;

    //ルーレットView
    private MyRouletteView rouleteView;
    //アニメーションの時間
    private final int ANIMATION_TIME = 5000;
    //ルーレットの項目数
    private final int NUM = 6;
    private boolean canTouch = true;

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
        homeButton = findViewById(R.id.homeButton);

    }

    /**
     * 初期化するメソッド
     */
    private void init(){

        mainLayout.setOnTouchListener(this);
        homeButton.setOnClickListener(this);

        animation = new MyRouletteAnimation(rouleteView, NUM);


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(canTouch) {

            //ルーレットが回っている間はタッチできないようにする
            canTouch = false;
            //アニメーションの時間を設定
            animation.setDuration(ANIMATION_TIME);
            //アニメーションを開始
            rouleteView.startAnimation(animation);
            //アニメーションにリスナを登録
            animation.setAnimationListener(this);
        }

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
    public void onClick(View view) {

        if(view.getId() == R.id.homeButton){
            //Activityを破棄(onDestroyメソッドが呼ばれる)
            finish();
            //Log.d("debug", "AppDo1Activity-onClick::ボタンを押しました");
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        //Log.d("debug", "AppDo1Activity-onPause");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //Log.d("debug", "AppDo1Activity-onDestroy");
    }

    @Override
    public void onAnimationStart(Animation animation) {

        title.setText("少々お待ちください");

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        canTouch = true;

        title.setText("画面をタッチしてください");
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


}