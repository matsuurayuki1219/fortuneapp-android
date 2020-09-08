package com.example.fortuneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fortuneapp.A.AppA01Activity;
import com.example.fortuneapp.B.AppB01Activity;
import com.example.fortuneapp.C.AppC01Activity;
import com.example.fortuneapp.D.AppD01Activity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    /** 変数の定義 */
    private TextView title;
    private TextView red;
    private TextView green;
    private TextView yellow;
    private TextView pink;
    private TextView orange;
    private TextView water;
    private Typeface fontL;
    private Typeface fontR;

    private LinearLayout buttonWater;
    private LinearLayout buttonRed;
    private LinearLayout buttonYellow;
    private LinearLayout buttonGreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponents();
        init();
    }

    /**
     * Viewを初期化するメソッド
     */
    private void initComponents(){
        title = findViewById(R.id.title);
        red = findViewById(R.id.red);
        green = findViewById(R.id.green);
        yellow = findViewById(R.id.yellow);
        pink = findViewById(R.id.pink);
        water = findViewById(R.id.water);
        orange = findViewById(R.id.orange);

        buttonWater = findViewById(R.id.buttonWater);
        buttonRed = findViewById(R.id.buttonRed);
        buttonYellow = findViewById(R.id.buttonYellow);
        buttonGreen = findViewById(R.id.buttonGreen);

    }

    /**
     * 初期化するメソッド
     */
    private void init(){
        fontL = Typeface.createFromAsset(getAssets(), "fonts/yugothicl.ttc");
        fontR = Typeface.createFromAsset(getAssets(), "fonts/yugothicr.ttc");
        title.setTypeface(fontR);
        red.setTypeface(fontR);
        green.setTypeface(fontR);
        yellow.setTypeface(fontR);
        pink.setTypeface(fontR);
        orange.setTypeface(fontR);
        water.setTypeface(fontR);

        buttonWater.setOnClickListener(this);
        buttonRed.setOnClickListener(this);
        buttonYellow.setOnClickListener(this);
        buttonGreen.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.buttonRed:
                Intent intentA = new Intent(getApplication(), AppA01Activity.class);
                startActivity(intentA);
                break;

            case R.id.buttonGreen:
                Intent intentB = new Intent(getApplication(), AppB01Activity.class);
                startActivity(intentB);
                break;

            case R.id.buttonWater:
                Intent intentC = new Intent(getApplication(), AppC01Activity.class);
                startActivity(intentC);
                break;

            case R.id.buttonYellow:
                Intent intentD = new Intent(getApplication(), AppD01Activity.class);
                startActivity(intentD);
                break;

        }

    }
}