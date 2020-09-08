package com.example.fortuneapp.CustomView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.fortuneapp.R;

/**
 * NumberPickerクラスを継承したカスタムView
 * スクロールする度にレイアウトに対してViewが追加されているため、addViewメソッドで設定をアップデートする関数を処理する
 */
public class MyNumberPicker extends NumberPicker {

    /** 変数の定義 */
    private Typeface fontR;

    /** 定数の定義 */
    private final int SIZE = 18;

    /**
     * コンストラクタ
     */
    public MyNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        fontR = Typeface.createFromAsset(context.getAssets(), "fonts/yugothicr.ttc");
    }

    /**
     * 子ビューを追加するときに追加される位置を指定するメソッド
     */
    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    /**
     * 子ビューを追加するときに追加される位置を指定するメソッド
     * @param child 追加したいView
     */
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    /**
     * 子ビューを追加するときに、NumberPickerの表示を変更するメソッド
     */
    private void updateView(View child) {
        if (child instanceof EditText) {
            //文字サイズ
            ((EditText) child).setTextSize(SIZE);
            //文字色
            ((EditText) child).setTextColor(getContext().getResources().getColor(R.color.white));
            //フォント
            ((EditText) child).setTypeface(fontR);

        }
    }
}
