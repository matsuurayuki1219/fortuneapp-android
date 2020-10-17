package jp.matsuura.fortuneapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import jp.matsuura.fortuneapp.A.AppA01Activity;
import jp.matsuura.fortuneapp.B.AppB01Activity;
import jp.matsuura.fortuneapp.C.AppC01Activity;
import jp.matsuura.fortuneapp.D.AppD01Activity;
import jp.matsuura.fortuneapp.E.AppE01Activity;
import jp.matsuura.fortuneapp.F.AppF01Activity;
import jp.matsuura.fortuneapp.Utilize.Util;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    /** 変数の定義 */

    //本人占い
    private LinearLayout method1;
    //相性占い
    private LinearLayout method2;
    //星座占い
    private LinearLayout method3;
    //おみくじ
    private LinearLayout method4;
    //タロット
    private LinearLayout method5;
    //リンク集
    private LinearLayout method6;
    //シェア
    private LinearLayout method7;
    //マニュアル
    private LinearLayout method8;
    //その他
    private LinearLayout method9;

    /** 定数の定義 */
    //SNSにシェアできた時のコード
    final int REQUESTCODE_SNS_SHARE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponents();
        init();

        Log.d("debug", "homeが生成されました");

        //アカウント登録をしなかった場合、サインアウトなどをする選択ボタンを削除する
        if(Util.doRegisterAccount == false){
            method9.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("debug", "homeが破棄されました");
    }

    /**
     * Viewを初期化するメソッド
     */
    private void initComponents(){

        method1 = findViewById(R.id.method1);
        method2 = findViewById(R.id.method2);
        method3 = findViewById(R.id.method3);
        method4 = findViewById(R.id.method4);
        method5 = findViewById(R.id.method5);
        method6 = findViewById(R.id.method6);
        method7 = findViewById(R.id.method7);
        method8 = findViewById(R.id.method8);
        method9 = findViewById(R.id.method9);

    }

    /**
     * 初期化するメソッド
     */
    private void init(){

        method1.setOnClickListener(this);
        method2.setOnClickListener(this);
        method3.setOnClickListener(this);
        method4.setOnClickListener(this);
        method5.setOnClickListener(this);
        method6.setOnClickListener(this);
        method7.setOnClickListener(this);
        method8.setOnClickListener(this);
        method9.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.method1:
                Intent intentA = new Intent(getApplication(), AppA01Activity.class);
                startActivity(intentA);
                break;

            case R.id.method2:
                Intent intentB = new Intent(getApplication(), AppB01Activity.class);
                startActivity(intentB);
                break;

            case R.id.method3:
                Intent intentC = new Intent(getApplication(), AppC01Activity.class);
                startActivity(intentC);
                break;

            case R.id.method4:
                Intent intentD = new Intent(getApplication(), AppD01Activity.class);
                startActivity(intentD);
                break;

            case R.id.method6:
                Intent intentE = new Intent(getApplication(), AppE01Activity.class);
                startActivity(intentE);
                break;

            case R.id.method5:
                Intent intentF = new Intent(getApplication(), AppF01Activity.class);
                startActivity(intentF);
                break;

            case R.id.method7:
                //shareするアプリのダイアログ表示を行う
                openShare();
                break;

            case R.id.method8:
                Intent intentG = new Intent(getApplication(), IntroductionActivity.class);
                startActivity(intentG);
                break;

            case R.id.method9:
                Intent intentH = new Intent(getApplication(), OtherActivity.class);
                startActivity(intentH);
                finish();
                break;
        }

    }

    /**
     * SNSなどでシェアする機能
     */
    private void openShare() {
        //builderの生成
        ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(HomeActivity.this);
        //シェアするタイトル
        builder.setSubject(getString(R.string.shareTitle));
        //シェアするテキスト
        builder.setText(getString(R.string.shareMessage));
        //シェアするタイプ
        builder.setType("text/plain");
        //Shareアプリ一覧のDialogの表示
        Intent intent = builder.createChooserIntent();

        //暗黙的インテントを受け取ることができるアプリが存在しない場合、遷移処理を行うとクラッシュしてしまう。
        //resolveActivityメソッドを使用し、インテントを処理できるアプリが少なくとも1つある場合に、呼び出す。
        if (intent.resolveActivity(getPackageManager()) != null) {
            //呼び出し先のActivityから情報を受け取ることを可能にする
            //1:intentデータ
            //2(requestCode):遷移先のアクティビティに対する一意の値
            startActivityForResult(intent, REQUESTCODE_SNS_SHARE);

            Log.d("debug", "HomeActivity-openShare:if文に入りました");
        }
    }

    //アクティビティのインスタンスが存在している場合に呼ばれるコールバック関数
    //resultCode:戻のアクティビティに戻ってくる前に、どのような処理が行われたかを判断する整数値
    //バックボタンを押した場合、RESULT_CANCELLEDが割り当てられる
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent date) {
        super.onActivityResult(requestCode, resultCode, date);

        Log.d("debug", "HomeActivity-onActivityResult:入りました");

        if(requestCode == REQUESTCODE_SNS_SHARE){
            //openShareメソッドのif文内を通った場合=SNSの通知が完了した場合
            if(resultCode == RESULT_OK){
                //バックボタンなどを押さず、操作が完了した場合
                Log.d("debug", "HomeActivity-onActivityResult:サンキュー！");

                //mp.start();

                //ダイアログを表示する
                new AlertDialog.Builder(this)
                        .setTitle("シェアのお礼")
                        .setMessage("この度はFortune Appをシェアしていただき、ありがとうございます！")
                        .setPositiveButton("確認", null)
                        .show();

            }
        }
    }

    //バックキーが押されたときに呼ばれるコールバック関数
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


}