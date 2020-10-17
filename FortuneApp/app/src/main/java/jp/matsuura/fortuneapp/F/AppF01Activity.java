package jp.matsuura.fortuneapp.F;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import jp.matsuura.fortuneapp.R;

public class AppF01Activity extends AppCompatActivity implements Animation.AnimationListener {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_f01);

        title = findViewById(R.id.title);

        final int ANIMATION_TIME = 3000;

        textAnimation(title, ANIMATION_TIME);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    private void textAnimation(View view, int time){

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(time);
        alphaAnimation.setFillAfter(false);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}