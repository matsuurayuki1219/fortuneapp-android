package jp.matsuura.fortuneapp.C;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.matsuura.fortuneapp.MyCustomView.MyNumberPicker;
import jp.matsuura.fortuneapp.R;
import jp.matsuura.fortuneapp.Utilize.Util;

public class AppC01Activity extends AppCompatActivity implements View.OnClickListener {

    /** 変数の定義 */
    private TextView errorMessage;
    private Button resultButton;
    private MyNumberPicker monthPicker;
    private MyNumberPicker dayPicker;

    /** 定数の定義 */
    private final int JANUARY = 1;
    private final int DECEMBER = 12;
    private final int FIRST = 1;
    private final int THIRTY_FIRST = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_c01);

        initComponents();

        init();

        initDrumRoll();


    }


    /**
     * Viewを初期化するメソッド
     */
    private void initComponents() {

        resultButton = findViewById(R.id.resultButton);
        errorMessage = findViewById(R.id.errorMessage);

    }

    /**
     * ドラムロールの初期化をするメソッド
     */
    private void initDrumRoll(){
        monthPicker = findViewById(R.id.monthPicker);
        monthPicker.setMinValue(JANUARY);
        monthPicker.setMaxValue(DECEMBER);
        dayPicker = findViewById(R.id.dayPicker);
        dayPicker.setMinValue(FIRST);
        dayPicker.setMaxValue(THIRTY_FIRST);
    }

    /**
     * 初期化するメソッド
     */
    private void init(){

        resultButton.setOnClickListener(this);

    }

    @Override
    public void onResume(){
        super.onResume();

        errorMessage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view){

        if(view.getId() == R.id.resultButton){

            int month = monthPicker.getValue();
            int day = dayPicker.getValue();

            Log.d("debug", "AppC02Activity-onClick::month "+month);
            Log.d("debug", "AppC02Activity-onClick::day "+day);

            if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){

                transitionScreen(month, day);

            }else if(month == 2){

                if(day >= 30 && day <= 31){
                    //エラー
                    error();
                }else{
                    transitionScreen(month, day);
                }

            }else{

                if(day == THIRTY_FIRST){
                    //エラー
                    error();
                }else{
                    transitionScreen(month, day);
                }
            }

        }
    }

    /**
     * 生年月日入力に不備がない場合、次の画面に遷移するメソッド
     * @param month 入力した誕生日の月
     * @param day 入力した誕生日の日
     */
    private void transitionScreen(int month, int day){
        setSign(month, day);
        Util.birthMonth = month;
        Util.birthDay = day;
        Intent intent = new Intent(getApplication(), AppC02Activity.class);
        startActivity(intent);
    }

    /**
     * エラー時の処理
     */
    private void error(){
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(R.string.errorC);
    }


    /**
     * 星座を取得し、Utilクラスにセットするメソッド
     * @param month 入力した誕生日の月
     * @param day 入力した誕生日の日
     */
    private void setSign(int month, int day){

        if( (month == 3 && day >= 21) || (month == 4 && day <= 19) ){
            //牡羊座
            Util.mySign = "牡羊座";
            Util.position = 0;
        }else if( (month == 4 && day >= 20) || (month == 5 && day <= 20) ){
            //牡牛座
            Util.mySign = "牡牛座";
            Util.position = 1;
        }
        else if( (month == 5 && day >= 21) || (month == 6 && day <= 21) ){
            //ふたご座
            Util.mySign = "双子座";
            Util.position = 2;
        }
        else if( (month == 6 && day >= 22) || (month == 7 && day <= 22) ){
            //かに座
            Util.mySign = "蟹座";
            Util.position = 3;
        }
        else if( (month == 7 && day >= 23) || (month == 8 && day <= 22) ){
            //しし座
            Util.mySign = "獅子座";
            Util.position = 4;
        }
        else if( (month == 8 && day >= 23) || (month == 9 && day <= 22) ){
            //おとめ座
            Util.mySign = "乙女座";
            Util.position = 5;
        }
        else if( (month == 9 && day >= 23) || (month == 10 && day <= 23) ){
            //てんびん座
            Util.mySign = "天秤座";
            Util.position = 6;
        }
        else if( (month == 10 && day >= 24) || (month ==11 && day <= 22) ){
            //さそり座
            Util.mySign = "蠍座";
            Util.position = 7;
        }
        else if( (month ==11 && day >= 23) || (month == 12 && day <= 23) ){
            //いて座
            Util.mySign = "射手座";
            Util.position = 8;
        }
        else if( (month == 12 && day >= 24) || (month == 1 && day <= 19) ){
            //やぎ座
            Util.mySign = "山羊座";
            Util.position = 9;
        }
        else if( (month == 1 && day >= 20) || (month == 2 && day <= 18) ){
            //みずがめ座
            Util.mySign = "水瓶座";
            Util.position = 10;
        }
        else if( ( month == 2 && day >= 19 ) || ( (month == 3) && (day <= 20) ) ){
            //うお座
            Util.mySign = "魚座";
            Util.position = 11;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }


}