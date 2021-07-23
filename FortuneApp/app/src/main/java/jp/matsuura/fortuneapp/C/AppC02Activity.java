package jp.matsuura.fortuneapp.C;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import jp.matsuura.fortuneapp.MyCustomView.MyCircleView;
import jp.matsuura.fortuneapp.MyAnimation.MyCircleAnimation;
import jp.matsuura.fortuneapp.Service.AsyncLoader;
import jp.matsuura.fortuneapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppC02Activity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

    /** 変数の定義 */

    private TextView title;
    private TextView content;
    private TextView item;
    private TextView money;
    private TextView total;
    private TextView color;
    private TextView job;
    private TextView love;
    private TextView rank;
    private Button tomorrowButton;

    private TextView dialog;
    private MyCircleView circle;
    private Handler handler;
    private AlphaAnimation alphaAnimation;
    private ScrollView scrollView;

    /** 定数の定義 */
    private final int ANIMATION_COUNT = 5;
    private final int ANIMATION_TIME = 500;

    private final String URL = "http://api.jugemkey.jp/api/horoscope/free/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_c02);

        initComponents();

        init();

        doTask();
    }


    /**
     * Viewを初期化するメソッド
     */
    private void initComponents() {
       title = findViewById(R.id.title);

       content = findViewById(R.id.content);
       item = findViewById(R.id.item);
       money = findViewById(R.id.money);
       total = findViewById(R.id.total);
       color = findViewById(R.id.color);
       job = findViewById(R.id.job);
       love = findViewById(R.id.love);
       rank = findViewById(R.id.rank);

       circle = findViewById(R.id.circle);
       dialog = findViewById(R.id.dialog);

        scrollView = findViewById(R.id.scrollView);

       tomorrowButton = findViewById(R.id.tomorrowButton);

    }

    /**
     * 初期化するメソッド
     */
    private void init(){

        tomorrowButton.setOnClickListener(this);

        scrollView.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        tomorrowButton.setVisibility(View.INVISIBLE);
    }

    /**
     * 非同期処理を開始するメソッド
     */
    private void doTask(){

        animation();

        //非同期処理のオブジェクトを生成する
        AsyncLoader asyncLoader = new AsyncLoader(content, item, money, total, job, color, love, rank, getCurrentDate());


        //引数にAPIのURLをとる
        asyncLoader.execute(URL + getCurrentDate());


    }

    /**
     * 診断中に表示するダイアログのアニメーション表示を処理するメソッド
     */
    private void animation(){

        handler= new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        textAnimation(dialog, ANIMATION_TIME, ANIMATION_COUNT);

                    }
                });

            }
        }).start();

        circleAnimation();
    }

    /**
     * ローディングアイコンのアニメーションを処理するメソッド
     */
    private void circleAnimation(){
        MyCircleAnimation animation = new MyCircleAnimation(circle);
        animation.setDuration(ANIMATION_TIME);
        circle.startAnimation(animation);
        animation.setRepeatCount(ANIMATION_COUNT);
        animation.setAnimationListener(this);

    }

    /**
     * TextViewのアニメーションを処理するメソッド
     * @param time 1回のアニメーションにかかる時間
     * @param count アニメーションを実行する回数
     */
    private void textAnimation(View view, int time, int count){

        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(time);
        alphaAnimation.setFillAfter(false);
        alphaAnimation.setRepeatCount(count);
        view.startAnimation(alphaAnimation);
    }

    /** 本日の日付を取得する */
    private String getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String result = sdf.format(date);
        Log.d("debug", "AppC02Activity-getCurrentDate::"+result);
        return result;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.tomorrowButton){

            Intent intent = new Intent(getApplication(), AppC03Activity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        dialog.setVisibility(View.INVISIBLE);
        circle.setVisibility(View.INVISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        tomorrowButton.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
}