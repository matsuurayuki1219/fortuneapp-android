package com.example.fortuneapp.A;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fortuneapp.C.AppC03Activity;
import com.example.fortuneapp.R;
import com.example.fortuneapp.Utilize.Util;

public class AppA02Activity extends AppCompatActivity {

    /** 変数の定義 */
    private Typeface fontL;
    private Typeface fontR;
    private TextView title;
    private TextView soulNumber;
    private TextView soulNumberCharacter;
    private TextView detailCharacter;
    private TextView detailTitle;
    private TextView personTitle;
    private TextView person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_a02);

        initComponents();

        init();

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
        detailTitle = findViewById(R.id.detailTitle);
        personTitle = findViewById(R.id.personTitle);
        person = findViewById(R.id.person);

    }

    /**
     * 初期化するメソッド
     */
    private void init(){
        fontL = Typeface.createFromAsset(getAssets(), "fonts/yugothicl.ttc");
        fontR = Typeface.createFromAsset(getAssets(), "fonts/yugothicr.ttc");

        title.setTypeface(fontR);
        soulNumber.setTypeface(fontR);
        soulNumberCharacter.setTypeface(fontR);
        detailCharacter.setTypeface(fontR);
        detailTitle.setTypeface(fontR);
        personTitle.setTypeface(fontR);
        person.setTypeface(fontR);

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

            for(int i=1; i<split.length; i++){

                addBirth += Integer.parseInt(split[i]);
            }

            allCharacter = String.valueOf(addBirth);

        }while(allCharacter.length() != 1);

        return allCharacter;
    }


}