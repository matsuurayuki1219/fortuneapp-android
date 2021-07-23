package jp.matsuura.fortuneapp.E;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

import jp.matsuura.fortuneapp.R;

public class AppE02Activity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_e02);

        webView = findViewById(R.id.webView);
    }

    @Override
    public void onResume(){
        super.onResume();

        Intent intent = getIntent();

        String url = intent.getStringExtra("url");
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
}