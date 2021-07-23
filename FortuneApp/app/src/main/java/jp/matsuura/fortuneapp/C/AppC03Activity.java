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
import jp.matsuura.fortuneapp.HomeActivity;
import jp.matsuura.fortuneapp.MyAnimation.MyCircleAnimation;
import jp.matsuura.fortuneapp.R;
import jp.matsuura.fortuneapp.Service.AsyncLoader;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AppC03Activity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

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
    private Button homeButton;

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
        setContentView(R.layout.activity_app_c03);

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

        homeButton = findViewById(R.id.homeButton);

    }

    /**
     * 初期化するメソッド
     */
    private void init(){

        homeButton.setOnClickListener(this);

        scrollView.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
    }

    /**
     * 非同期処理を開始するメソッド
     */
    private void doTask(){

        animation();

        //Log.d("debug", "AppC03Activity-doTask::ここ1です");

        //非同期処理のオブジェクトを生成する
        AsyncLoader asyncLoader = new AsyncLoader(content, item, money, total, job, color, love, rank, getTomorrowDate());

        //引数にAPIのURLをとる
        asyncLoader.execute(URL + getTomorrowDate());

        //Log.d("debug", "AppC03Activity-doTask::ここ2です");


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


    /** 明日の日付を取得する */
    private String getTomorrowDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String tomorrow = sdf.format(calendar.getTime());

        Log.d("debug", "AppC03Activity-getTomorrowDate::tomorrowは"+tomorrow);

        return tomorrow;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.homeButton){

            Intent intent = new Intent(getApplication(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        homeButton.setVisibility(View.VISIBLE);
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