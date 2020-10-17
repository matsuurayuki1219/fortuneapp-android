package jp.matsuura.fortuneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import jp.matsuura.fortuneapp.Utilize.Util;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    /** 変数の定義 */
    private TextView mainTitle;
    private TimerTask task;
    //private Button mainButton;
    //private Button introductionButton;
    private Button loginButton;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // WindowManagerのインスタンス取得
        WindowManager manager = getWindowManager();
        // Displayのインスタンス取得
        Display display = manager.getDefaultDisplay();
        // DisplayMetricsのインスタンス生成
        DisplayMetrics metrics = new DisplayMetrics();
        // ディスプレイ情報の取得
        display.getMetrics(metrics);

        Util.widthDisplay = metrics.widthPixels;
        Log.d("debug", "HomeActivity-onCreate::widthDisplayは"+Util.widthDisplay);


        mainTitle = findViewById(R.id.mainTitle);
        //mainButton = findViewById(R.id.mainButton);
        //introductionButton = findViewById(R.id.introductionButton);
        loginButton = findViewById(R.id.loginButton);

        //mainButton.setOnClickListener(this);
        //introductionButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        handler = new Handler();

        final int ANIMATION_TIME = 1500;

        textAnimation(mainTitle, ANIMATION_TIME);


        task = new TimerTask() {

            public void run() {

                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();

                        //ユーザがすでにログイン済みの場合、HomeActivityに遷移する

                        if(user != null){
                            Intent intent = new Intent(getApplication(), HomeActivity.class);
                            Util.doRegisterAccount = true;
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(getApplication(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                });
            }
        };
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("debug", "破棄されました");
    }


    /**
     * TextViewのアニメーションを処理するメソッド
     * @param time 1回のアニメーションにかかる時間
     */
    private void textAnimation(View view, int time){

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(time);
        alphaAnimation.setFillAfter(false);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(this);
    }

    //テキストのアニメーションが始まったときに呼ばれるコールバック関数
    @Override
    public void onAnimationStart(Animation animation) {

    }

    //テキストのアニメーションが終わったときに呼ばれるコールバック関数
    @Override
    public void onAnimationEnd(Animation animation) {

        //1秒後に画面を遷移させる
        final int CHANGE_UI_TIME = 1000;

        Timer timer = new Timer();
        timer.schedule(task, CHANGE_UI_TIME);
    }

    //テキストのアニメーションが繰り返されたときに呼ばれるコールバック関数
    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View view){

        /*
        if(view.getId() == R.id.mainButton){

            Intent intent = new Intent(getApplication(), HomeActivity.class);
            startActivity(intent);

        }else if(view.getId() == R.id.introductionButton){

            Intent intent = new Intent(getApplication(), IntroductionActivity.class);
            startActivity(intent);

        }else if(view.getId() == R.id.loginButton){

            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);

        }

        if(view.getId() == R.id.loginButton) {

            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
        }

         */
    }


}