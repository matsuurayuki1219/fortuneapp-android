package jp.matsuura.fortuneapp.A;

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
import jp.matsuura.fortuneapp.Utilize.Util;

public class AppA02Activity extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    /** 変数の定義 */
    private TextView title;
    private TextView soulNumber;
    private TextView soulNumberCharacter;
    private TextView detailCharacter;
    private TextView person;
    private MyCircleView circle;
    private TextView dialog;
    private ScrollView scrollView;
    private Handler handler;
    private AlphaAnimation alphaAnimation;
    private Button homeButton;

    /** 定数の定義 */
    private final int ANIMATION_COUNT = 5;
    private final int ANIMATION_TIME = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_a02);

        initComponents();

        init();

        animation();

        Log.d("debug", "AppA02Activity-onCreate::birthYearは"+Util.birthYear);
        Log.d("debug", "AppA02Activity-onCreate::birthMonthは"+Util.birthMonth);
        Log.d("debug", "AppA02Activity-onCreate::birthDayは"+Util.birthDay);

        String soulNumber = calcFortune(Util.birthYear, Util.birthMonth, Util.birthDay);

        //ソウルナンバーをTextViewにセットする
        soulNumberCharacter.setText(soulNumber);

        setDetailCharacter(soulNumber);

        setPerson(soulNumber);

    }

    /**
     * Viewを初期化するメソッド
     */
    private void initComponents() {
        title = findViewById(R.id.title);
        soulNumber = findViewById(R.id.soulNumber);
        soulNumberCharacter = findViewById(R.id.soulNumberCharacter);
        detailCharacter = findViewById(R.id.detailCharacter);
        homeButton = findViewById(R.id.homeButton);
        person = findViewById(R.id.person);

        circle = findViewById(R.id.circle);
        dialog = findViewById(R.id.dialog);
        scrollView = findViewById(R.id.contents);
    }

    /**
     * 初期化するメソッド
     */
    private void init(){

        scrollView.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
        homeButton.setOnClickListener(this);


    }

    /**
     * アニメーションを実行するメソッド
     */
    private void animation(){

        //アンドロイドのGUIはシングルスレッドにしか対応していないため、
        //ウィジェット等のＧＵＩオブジェクトを生成したスレッドとは別のスレッドから、
        //ウィジェットに直接アクセスする事はできない。
        //GUIを別スレッドから操作するためにはHandlerクラスを使用する必要がある

        //Handlerクラスのインスタンスのpostメソッドで指定したRunnableクラスのコードは、
        //Handlerクラスのインスタンスの属する（つまりウィジェットの属する）スレッドで実行される

        //Handlerのインスタンス化はUIスレッドで行う
        //
        handler= new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                //Handler#postは指定された処理（Runnableオブジェクト）をLooperにpostする処理
                //別スレッドからUI（メイン）スレッドを書き換えたいときの処理をLooperというQueueにためて、順番に処理していく

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

    /**
     * アニメーションが開始されたときに呼ばれるコールバックメソッド
     */
    @Override
    public void onAnimationStart(Animation animation) {

    }

    /**
     * アニメーションが終了したときに呼ばれるコールバックメソッド
     */
    @Override
    public void onAnimationEnd(Animation animation) {

        //ANIMATION_COUNT回、アニメーションが実行された場合に、呼び出される
        circle.setVisibility(View.INVISIBLE);
        dialog.setVisibility(View.INVISIBLE);

        scrollView.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        homeButton.setVisibility(View.VISIBLE);

        showResult();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    /**
     * アニメーションが終了したときにUIを変更するメソッド
     */
    private void showResult(){

        String soulNumber = calcFortune(Util.birthYear, Util.birthMonth, Util.birthDay);

        //ソウルナンバーをTextViewにセットする
        soulNumberCharacter.setText(soulNumber);

        setDetailCharacter(soulNumber);

        setPerson(soulNumber);

        scrollView.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);

    }
    /**
     * TextViewにソウルナンバーの特徴をセットする
     * @param soulNumber ソウルナンバー
     * */
    private void setDetailCharacter(String soulNumber){

        switch (soulNumber){

            case "1":
                detailCharacter.setText(getString(R.string.detailcharacter1));
                break;
            case "2":
                detailCharacter.setText(getString(R.string.detailcharacter2));
                break;
            case "3":
                detailCharacter.setText(getString(R.string.detailcharacter3));
                break;
            case "4":
                detailCharacter.setText(getString(R.string.detailcharacter4));
                break;
            case "5":
                detailCharacter.setText(getString(R.string.detailcharacter5));
                break;
            case "6":
                detailCharacter.setText(getString(R.string.detailcharacter6));
                break;
            case "7":
                detailCharacter.setText(getString(R.string.detailcharacter7));
                break;
            case "8":
                detailCharacter.setText(getString(R.string.detailcharacter8));
                break;
            case "9":
                detailCharacter.setText(getString(R.string.detailcharacter9));
                break;
            default:
                break;

        }
    }

    /**
     * TextViewに芸能人をセットするメソッド
     * @param soulNumber ソウルナンバー
     * */
    private void setPerson(String soulNumber){

        switch (soulNumber){

            case "1":
                person.setText(getString(R.string.person1));
                break;
            case "2":
                person.setText(getString(R.string.person2));
                break;
            case "3":
                person.setText(getString(R.string.person3));
                break;
            case "4":
                person.setText(getString(R.string.person4));
                break;
            case "5":
                person.setText(getString(R.string.person5));
                break;
            case "6":
                person.setText(getString(R.string.person6));
                break;
            case "7":
                person.setText(getString(R.string.person7));
                break;
            case "8":
                person.setText(getString(R.string.person8));
                break;
            case "9":
                person.setText(getString(R.string.person9));
                break;
            default:
                break;

        }
    }

    /** ソウルナンバーを計算するメソッド
     * @param year
     * @param month
     * @param day
     * @return String ソウルナンバー
     */
    private String calcFortune(int year, int month, int day){

        String allCharacter = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);

        do{
            int addBirth = 0;

            String[] split = allCharacter.split("");

            for(int i=0; i<split.length; i++){

                addBirth += Integer.parseInt(split[i]);
            }

            allCharacter = String.valueOf(addBirth);

        }while(allCharacter.length() != 1);

        return allCharacter;
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
            Intent intent = new Intent(getApplication(), HomeActivity.class);

            //遷移画面までのActivityのスタックを削除する
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("debug", "AppA02Activity-onDestroy::破棄されました");
    }
}