package jp.matsuura.fortuneapp.MyCustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import jp.matsuura.fortuneapp.R;

/**
 * ローディングアイコンを作成するクラス
 */
public class MyCircleView extends View {

    private Paint innerPaint;
    private Paint innerDammy;
    private Paint outerPaint;
    private Paint outerDammy;

    private int centerX;
    private int centerY;

    private final int STROKE_WIDTH = 10;
    private final int START_ANGLE = 270;
    private final int END_ANGLE = 360;
    private final int SWEEP_ANGLE = 60;
    private final int DIFFERENCE = 180;

    private RectF outerRct;
    private RectF innerRect;

    private int innerRadius;
    private int outerRadius;

    private float angle;

    /**
     * コンストラクタ
     * このコンストラクタを用意すると、xmlファイルからViewのインスタンスを作成＆編集が可能になる
     * @param context コンテキスト
     * @param attrs AttributeSet ※Viewの属性をセットできる
     */
    public MyCircleView(Context context, AttributeSet attrs){
        super(context, attrs);

        init();
    }

    /**
     * 初期化処理
     */
    private void init(){

        //Paint変数の初期化
        innerPaint = new Paint();
        innerDammy = new Paint();
        outerPaint = new Paint();
        outerDammy = new Paint();

        //innerPaintの設定
        innerPaint.setAntiAlias(true);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setStrokeWidth(STROKE_WIDTH);

        //innerPaintの設定
        innerDammy.setAntiAlias(true);
        innerDammy.setStyle(Paint.Style.STROKE);
        innerDammy.setStrokeWidth(STROKE_WIDTH);

        //outerPaintの設定
        outerPaint.setAntiAlias(true);
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeWidth(STROKE_WIDTH);

        //outerPaintの設定
        outerDammy.setAntiAlias(true);
        outerDammy.setStyle(Paint.Style.STROKE);
        outerDammy.setStrokeWidth(STROKE_WIDTH);

        //色
        innerPaint.setColor(getResources().getColor(R.color.white));
        innerDammy.setColor(getResources().getColor(R.color.baseColor));
        outerPaint.setColor(getResources().getColor(R.color.white));
        outerDammy.setColor(getResources().getColor(R.color.baseColor));

        //Arcの範囲
        innerRect = new RectF();
        outerRct = new RectF();


    }


    /**
     * Canvasに描画するメソッド
     * @param canvas Canvas
     */
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //背景の色
        canvas.drawColor(getResources().getColor(R.color.baseColor));

        //Canvas 中心点
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        //半径
        innerRadius = getWidth() / 4;
        outerRadius = getWidth() / 4 + STROKE_WIDTH * 3 / 2;

        //円弧の領域設定
        outerRct.set(centerX - outerRadius, centerY - outerRadius, centerX + outerRadius, centerY + outerRadius);
        innerRect.set(centerX - innerRadius, centerY - innerRadius, centerX + innerRadius, centerY + innerRadius);

        //円弧の描画
        //1：RectF
        //2:startAngle
        //3:sweepAngle
        //4:true→中心線あり false→中心線なし
        //5:Paint
        canvas.drawArc(innerRect, START_ANGLE, END_ANGLE, false, innerDammy);
        canvas.drawArc(outerRct, START_ANGLE, END_ANGLE, false, outerDammy);

        canvas.drawArc(innerRect, angle + DIFFERENCE, SWEEP_ANGLE , false, innerPaint);
        canvas.drawArc(outerRct, angle, SWEEP_ANGLE, false, outerPaint);
    }

    //Animationへ現在のangleを返す
    public float getAngle() {
        return angle;
    }

    //Animationから新しいangleが設定される
    public void setAngle(float angle) {
        this.angle = angle;
    }

}
