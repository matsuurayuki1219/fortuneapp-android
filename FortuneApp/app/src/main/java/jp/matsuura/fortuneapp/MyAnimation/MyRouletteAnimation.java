package jp.matsuura.fortuneapp.MyAnimation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import jp.matsuura.fortuneapp.MyCustomView.MyRouletteView;

import java.util.Random;

/**
 * ルーレットを回すアニメーションクラス
 * */
public class MyRouletteAnimation extends Animation {

    /** 変数の定義 */
    MyRouletteView view;
    private int NUM;
    int angle;
    int count;
    int position = 0;
    Random random;

    public MyRouletteAnimation(MyRouletteView view, int num){
        this.view = view;
        this.NUM = num;
        angle = 360 / NUM;

        random = new Random();
        count = random.nextInt(3) + 1;
    }

    @Override
    public void applyTransformation(float interpolatedTime, Transformation t){
        super.applyTransformation(interpolatedTime, t);

        count++;

        if(count % 3 == 0) {

            view.setColors(position % 6);

            position++;
        }

        view.requestLayout();

    }

}
