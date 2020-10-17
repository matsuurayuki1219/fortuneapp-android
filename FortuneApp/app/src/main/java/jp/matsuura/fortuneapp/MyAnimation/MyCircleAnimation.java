package jp.matsuura.fortuneapp.MyAnimation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import jp.matsuura.fortuneapp.MyCustomView.MyCircleView;

public class MyCircleAnimation extends Animation {

    private MyCircleView view;

    // アニメーション角度
    private final float OLD_ANGLE = 0;
    private final float NEW_ANGLE = 360;

    /**
     * コンストラクタ
     * @param view MyCircleView
     */
    public MyCircleAnimation(MyCircleView view){
        this.view = view;
    }

    /**
     * アニメーションを実行するメソッド
     * @param interpolatedTime 0～1
     * @param transformation Transformation
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {

        float angle = OLD_ANGLE + ((NEW_ANGLE - OLD_ANGLE) * interpolatedTime);
        view.setAngle(angle);
        view.requestLayout();
    }
}
