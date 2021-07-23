package jp.matsuura.fortuneapp.B;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.matsuura.fortuneapp.MyCustomView.MyNumberPicker;
import jp.matsuura.fortuneapp.R;
import jp.matsuura.fortuneapp.Utilize.Util;

public class AppB01Activity extends AppCompatActivity implements View.OnClickListener {

    /** 定数の定義 */
    private TextView title;
    private TextView errorMessage;
    private MyNumberPicker yearPicker;
    private MyNumberPicker monthPicker;
    private MyNumberPicker dayPicker;
    private Button nextButton;

    /** 定数の定義 */
    private final int YEAR_FIRST = 1950;
    private final int YEAR_END = 2020;
    private final int JANUARY = 1;
    private final int DECEMBER = 12;
    private final int FIRST = 1;
    private final int THIRTY_FIRST = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_b01);

        initComponents();

        init();

        initDrumRoll();
    }

    /**
     * Viewを初期化するメソッド
     */
    private void initComponents() {
        title = findViewById(R.id.title);
        nextButton = findViewById(R.id.nextButton);
        errorMessage = findViewById(R.id.errorMessage);
    }

    /**
     * ドラムロールの初期化をするメソッド
     */
    private void initDrumRoll(){
        yearPicker = findViewById(R.id.yearPicker);
        yearPicker.setMinValue(YEAR_FIRST);
        yearPicker.setMaxValue(YEAR_END);
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

        nextButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.nextButton){

            int year = yearPicker.getValue();
            int month = monthPicker.getValue();
            int day = dayPicker.getValue();

            if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){

                transitionScreen(year, month, day);

            }else if(month == 2){

                if(day >= 29){

                    if(year % 4  == 0){
                        transitionScreen(year, month, day);
                    }else{
                        error();
                    }

                }else{
                    transitionScreen(year, month, day);
                }

            }else{

                if(day == THIRTY_FIRST){
                    //エラー
                    error();
                }else{
                    transitionScreen(year, month, day);
                }
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {

            //本Acitivtyを破棄(onDestroyメソッドを呼び出す)
            finish();
        }
        return false;
    }


    /**
     * 生年月日入力に不備がない場合、次の画面に遷移するメソッド
     * @param month 入力した誕生日の月
     * @param day 入力した誕生日の日
     */
    private void transitionScreen(int year, int month, int day){
        Util.birthYear = year;
        Util.birthMonth = month;
        Util.birthDay = day;
        Intent intent = new Intent(getApplication(), AppB02Activity.class);
        startActivity(intent);
    }

    /**
     * エラー時の処理
     */
    private void error(){
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(R.string.errorC);
    }
}