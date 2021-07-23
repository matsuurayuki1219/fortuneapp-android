package jp.matsuura.fortuneapp.B;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class AppB03Activity extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    /** 定数の定義 */
    private TextView title;
    private TextView dialog;
    private MyCircleView circle;
    private Handler handler;
    private AlphaAnimation alphaAnimation;
    private ScrollView scrollView;
    private Button homeButton;

    private TextView soulNumberCharacter;
    private TextView detailCharacter;
    private TextView person;
    private TextView otherSoulNumberCharacter;
    private TextView otherDetailCharacter;
    private TextView otherPerson;

    /** 定数の定義 */
    private final int ANIMATION_COUNT = 5;
    private final int ANIMATION_TIME = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_b03);

        initComponents();

        init();

        animation();

    }

    /**
     * Viewを初期化するメソッド
     */
    private void initComponents() {
        title = findViewById(R.id.title);
        circle = findViewById(R.id.circle);
        dialog = findViewById(R.id.dialog);
        scrollView = findViewById(R.id.contents);
        homeButton = findViewById(R.id.homeButton);
        soulNumberCharacter = findViewById(R.id.soulNumberCharacter);
        detailCharacter = findViewById(R.id.detailCharacter);
        person = findViewById(R.id.person);
        otherSoulNumberCharacter = findViewById(R.id.otherSoulNumberCharacter);
        otherDetailCharacter = findViewById(R.id.otherDetailCharacter);
        otherPerson = findViewById(R.id.otherPerson);

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



    /**
     * アニメーションが終了したときにUIを変更するメソッド
     */
    private void showResult(){

        String soulNumber = calcFortune(Util.birthYear, Util.birthMonth, Util.birthDay);
        String otherSoulNumber = calcFortune(Util.otherBirthYear, Util.otherBirthMonth, Util.otherBirthDay);

        //ソウルナンバーをTextViewにセットする
        soulNumberCharacter.setText(soulNumber);
        otherSoulNumberCharacter.setText(otherSoulNumber);

        setDetailCharacter(detailCharacter, soulNumber);
        setDetailCharacter(otherDetailCharacter, otherSoulNumber);

        setPerson(person, soulNumber);
        setPerson(otherPerson, otherSoulNumber);

        scrollView.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);

    }

    /**
     * TextViewにソウルナンバーの特徴をセットする
     * @param text TextView
     * @param soulNumber ソウルナンバー
     * */
    private void setDetailCharacter(TextView text, String soulNumber){

        switch (soulNumber){

            case "1":
                text.setText(getString(R.string.detailcharacter1));
                break;
            case "2":
                text.setText(getString(R.string.detailcharacter2));
                break;
            case "3":
                text.setText(getString(R.string.detailcharacter3));
                break;
            case "4":
                text.setText(getString(R.string.detailcharacter4));
                break;
            case "5":
                text.setText(getString(R.string.detailcharacter5));
                break;
            case "6":
                text.setText(getString(R.string.detailcharacter6));
                break;
            case "7":
                text.setText(getString(R.string.detailcharacter7));
                break;
            case "8":
                text.setText(getString(R.string.detailcharacter8));
                break;
            case "9":
                text.setText(getString(R.string.detailcharacter9));
                break;
            default:
                break;

        }
    }

    /**
     * TextViewに芸能人をセットするメソッド
     * @param soulNumber ソウルナンバー
     * */
    private void setPerson(TextView text, String soulNumber){

        switch (soulNumber){

            case "1":
                text.setText(getString(R.string.person1));
                break;
            case "2":
                text.setText(getString(R.string.person2));
                break;
            case "3":
                text.setText(getString(R.string.person3));
                break;
            case "4":
                text.setText(getString(R.string.person4));
                break;
            case "5":
                text.setText(getString(R.string.person5));
                break;
            case "6":
                text.setText(getString(R.string.person6));
                break;
            case "7":
                text.setText(getString(R.string.person7));
                break;
            case "8":
                text.setText(getString(R.string.person8));
                break;
            case "9":
                text.setText(getString(R.string.person9));
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

    /**
     * アニメーションが繰り返されたときに呼ばれるコールバックメソッド
     */
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

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.homeButton){

            Intent intent = new Intent(getApplication(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }
}