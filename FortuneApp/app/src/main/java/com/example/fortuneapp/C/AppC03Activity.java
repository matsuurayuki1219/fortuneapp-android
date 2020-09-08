package com.example.fortuneapp.C;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fortuneapp.HomeActivity;
import com.example.fortuneapp.R;
import com.example.fortuneapp.Service.AsyncLoader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppC03Activity extends AppCompatActivity implements View.OnClickListener {

    /** 変数の定義 */
    private Typeface fontL;
    private Typeface fontR;
    private TextView title;
    private TextView content;
    private TextView item;
    private TextView money;
    private TextView total;
    private TextView color;
    private TextView job;
    private TextView love;
    private TextView rank;
    private TextView contentTitle;
    private TextView itemTitle;
    private TextView moneyTitle;
    private TextView totalTitle;
    private TextView colorTitle;
    private TextView jobTitle;
    private TextView loveTitle;
    private TextView rankTitle;
    private Button homeButton;

    /** 定数の定義 */
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
        contentTitle = findViewById(R.id.contentTitle);
        itemTitle = findViewById(R.id.itemTitle);
        moneyTitle = findViewById(R.id.moneyTitle);
        totalTitle = findViewById(R.id.totalTitle);
        colorTitle = findViewById(R.id.colorTitle);
        jobTitle = findViewById(R.id.jobTitle);
        loveTitle = findViewById(R.id.loveTitle);
        rankTitle = findViewById(R.id.rankTitle);

        homeButton = findViewById(R.id.homeButton);

    }

    /**
     * 初期化するメソッド
     */
    private void init(){
        fontL = Typeface.createFromAsset(getAssets(), "fonts/yugothicl.ttc");
        fontR = Typeface.createFromAsset(getAssets(), "fonts/yugothicr.ttc");

        title.setTypeface(fontR);
        content.setTypeface(fontR);
        item.setTypeface(fontR);
        money.setTypeface(fontR);
        total.setTypeface(fontR);
        color.setTypeface(fontR);
        job.setTypeface(fontR);
        love.setTypeface(fontR);
        rank.setTypeface(fontR);

        contentTitle.setTypeface(fontR);
        itemTitle.setTypeface(fontR);
        moneyTitle.setTypeface(fontR);
        totalTitle.setTypeface(fontR);
        colorTitle.setTypeface(fontR);
        jobTitle.setTypeface(fontR);
        loveTitle.setTypeface(fontR);
        rankTitle.setTypeface(fontR);

        homeButton.setOnClickListener(this);
    }

    /**
     * 非同期処理を開始するメソッド
     */
    private void doTask(){

        //非同期処理のオブジェクトを生成する
        AsyncLoader asyncLoader = new AsyncLoader(content, item, money, total, job, color, love, rank, getTomorrowDate());

        //引数にAPIのURLをとる
        asyncLoader.execute(URL + getTomorrowDate());
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
            startActivity(intent);
        }
    }
}