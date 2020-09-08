package com.example.fortuneapp.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.fortuneapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ルーレットを表示するViewクラス
 */
public class MyRouletteView extends View {

    /** 変数の定義 */
    //ルーレット
    Paint paint;
    //ルーレット上のテキスト
    Paint textPaint;
    private int angle = 0;
    //ルーレットの項目数
    private final int NUM = 6;
    private int init = 0;
    // Canvas 中心点
    private float xc = 0.0f;
    private float yc = 0.0f;
    private int position = 0;
    //ルーレットを描くオブジェクト
    private RectF rectF = null;
    //ルーレット上に配置する文字をシャッフルするためのList\
    List<String> result = new ArrayList<String>();


    /** 定数の定義 */
    //canvasの色
    private final int backColor = getResources().getColor(R.color.baseColor);
    //選ばれた場所の色
    private final int selectedRoulette = getResources().getColor(R.color.buttonYellow);
    //選ばれてない場所の色
    private final int nonSelectedRoulette = getResources().getColor(R.color.baseColor);
    //ルーレット上の文字の色
    private final int rouletteText = getResources().getColor(R.color.white);
    //ルーレットの線の色
    private final int rouletteStroke = getResources().getColor(R.color.white);
    //ルーレット上の文字のサイズ
    private final int TEXT_SIZE = 40;
    //ルーレットの枠線の太さ
    private final int STROKE = 5;
    //ルーレットの端のマージン
    private final int MARGIN = 20;

    private int[] colors = {
            selectedRoulette,
            nonSelectedRoulette,
            nonSelectedRoulette,
            nonSelectedRoulette,
            nonSelectedRoulette,
            nonSelectedRoulette
    };

    private String[] testStrings = {
            "大吉",
            "中吉",
            "小吉",
            "末吉",
            "凶",
            "大凶",
    };

    /** コンストラクタ
     * @param context コンテキスト
     * @param attrs
     * */
    public MyRouletteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //ルーレットを描画するためのPaintクラス
        paint = new Paint();

        initTextPaint();

        //1つあたりの項目の角度
        angle = 360 / NUM;

        shuffleText();

    }

    /** ルーレット上に配置する文字の位置をシャッフルするメソッド */
    private void shuffleText(){
        for(int i=0; i<NUM; i++){
            result.add(testStrings[i]);
        }
        Collections.shuffle(result);
    }

    /** TextPaintの初期化 */
    private void initTextPaint(){
        textPaint = new Paint();
        //文字色
        textPaint.setColor(rouletteText);
        //文字サイズ
        textPaint.setTextSize(TEXT_SIZE);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //背景の色
        canvas.drawColor(backColor);

        //描画範囲を指定
        if(init == 0) {
            // Canvas 中心点
            if(xc == 0.0f) xc = canvas.getWidth() / 2;
            if(yc == 0.0f) yc = canvas.getHeight() / 2;

            // 画面の中心から横幅に合わせた正方形を作る
            if(rectF == null) {
                rectF = new RectF(0.0f + MARGIN, yc - xc + MARGIN, canvas.getWidth() - MARGIN, yc + xc - MARGIN);
            }

            init = 1;
        }

        drawCircle(canvas);

        drawTextPaint(canvas);


    }

    /** ルーレットを描画するメソッド
     * @param canvas キャンバス
     */
    private void drawCircle(Canvas canvas){
        //パネルの描画
        for (int i = 0; i < NUM; i++) {
            //円弧の線の処理
            //枠線の色
            paint.setColor(rouletteStroke);
            //枠線の幅
            paint.setStrokeWidth(STROKE);
            //Paintクラスのスタイル
            paint.setStyle(Paint.Style.STROKE);
            //ふちを滑らかにする処理
            paint.setAntiAlias(true);
            canvas.drawArc(rectF, (i * angle), angle, true, paint);

            //円弧の塗りつぶしの処理
            paint.setColor(colors[i]);
            paint.setStyle(Paint.Style.FILL);
            //ふちを滑らかにする処理
            paint.setAntiAlias(true);
            canvas.drawArc(rectF, (i * angle), angle, true, paint);

        }

    }

    /** TextPaintの描画をするメソッド */
    private void drawTextPaint(Canvas canvas){
        //テキストの描画
        for (int j = 0; j < NUM; j++) {

            int intTextAngle = 0;

            if(j == 0) {
                intTextAngle = angle / 2;
            } else{
                intTextAngle = angle;
            }

            canvas.rotate(intTextAngle, xc, yc);

            //ふちを滑らかにする処理
            textPaint.setAntiAlias(true);

            canvas.drawText(result.get(j), xc + 130, yc + 10, textPaint);

        }

    }


    /** アニメーションを実行するためのセッター
     * @param num 選択されているルーレットの位置
     */
    public void setColors(int num){

        for(int i=0; i<NUM; i++){

            if(i == num){
                colors[i] =  selectedRoulette;
                position = num;
            }else{
                colors[i] = nonSelectedRoulette;
            }

        }

    }

}
