package jp.matsuura.fortuneapp.E;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import jp.matsuura.fortuneapp.R;

public class AppE01Activity extends AppCompatActivity implements View.OnClickListener {

    /** 変数の定義 */
    private TextView title;
    private TextView urlTitle1;
    private TextView url1;
    private TextView urlTitle2;
    private TextView url2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_e01);

        initComponents();

        init();
    }

    /**
     * Viewの初期化
     */
    private void initComponents(){

        title = findViewById(R.id.title);
        urlTitle1 = findViewById(R.id.urlTitle1);
        url1 = findViewById(R.id.url1);
        urlTitle2 = findViewById(R.id.urlTitle2);
        url2 = findViewById(R.id.url2);
    }

    /**
     * 初期化処理
     */

    private void init(){

        url1.setOnClickListener(this);
        url2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String url = null;

        if(view.getId() == R.id.url1){

            url = getString(R.string.url1);

        }else if(view.getId() == R.id.url2){

            url = getString(R.string.url2);
        }

        Intent intent = new Intent(getApplication(), AppE02Activity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
}