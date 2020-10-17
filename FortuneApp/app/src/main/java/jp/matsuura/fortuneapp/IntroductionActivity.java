package jp.matsuura.fortuneapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.matsuura.fortuneapp.MyFragment.EightFragment;
import jp.matsuura.fortuneapp.MyFragment.FifthFragment;
import jp.matsuura.fortuneapp.MyFragment.FirstFragment;
import jp.matsuura.fortuneapp.MyFragment.FourthFragment;
import jp.matsuura.fortuneapp.MyFragment.SecondFragment;
import jp.matsuura.fortuneapp.MyFragment.SeventhFragment;
import jp.matsuura.fortuneapp.MyFragment.SixthFragment;
import jp.matsuura.fortuneapp.MyFragment.ThirdFragment;
import jp.matsuura.fortuneapp.MyAnimation.ZoomOutPageTransformer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class IntroductionActivity extends FragmentActivity implements TabLayoutMediator.TabConfigurationStrategy, View.OnClickListener {


    /** 変数の定義 */
    private ViewPager2 viewPager;
    private TextView title;
    private TextView message;
    private Button nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        init();

        //viewPagerにリスナーを登録する
        //スワイプされたときに呼ばれるコールバック関数

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                Log.d("debug", "IntroductionActivity-onPageScrolled::positionは"+position);

                switch (position){

                    case 0:
                        title.setText(getString(R.string.title1));
                        message.setText(getString(R.string.message1));

                        break;

                    case 1:
                        title.setText(getString(R.string.title2));
                        message.setText(getString(R.string.message2));
                        break;

                    case 2:
                        title.setText(getString(R.string.title3));
                        message.setText(getString(R.string.message3));
                        break;

                    case 3:
                        title.setText(getString(R.string.title4));
                        message.setText(getString(R.string.message4));
                        break;

                    case 4:
                        title.setText(getString(R.string.title5));
                        message.setText(getString(R.string.message5));
                        break;

                    case 5:
                        title.setText(getString(R.string.title6));
                        message.setText(getString(R.string.message6));
                        break;

                    case 6:
                        title.setText(getString(R.string.title7));
                        message.setText(getString(R.string.message7));
                        break;

                    case 7:
                        title.setText(getString(R.string.title8));
                        message.setText(getString(R.string.message8));
                        break;

                }
            }
        });
    }

    /**
     * 初期化するメソッド
     */
    private void init() {

        title = findViewById(R.id.title);
        message = findViewById(R.id.message);

        //Fragmentをスワイプ表示するためのView
        viewPager = findViewById(R.id.viewPager);
        //viewPager内のフラグメントを生成するためのadapter
        MyFragmentAdapter adapter = new MyFragmentAdapter(this);
        //viewPagerにadapterを適用する
        viewPager.setAdapter(adapter);
        //viewPagerにスワイプ時のアニメーションを適用する
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        //viewPagerの下部のインディケータ
        TabLayout indicator = findViewById(R.id.indicator);
        //viewPagerとインディケータを連動するためのインスタンス
        //第1引数：tablayoutのインスタンス
        //第2引数：viewPagerのインスタンス
        //第3引数：コールバックメソッド(TabLayoutMediator.TabConfigurationStrategy)
        TabLayoutMediator tab = new TabLayoutMediator(indicator, viewPager, this);
        //viewPagerとインディケータを連動する
        tab.attach();

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);

    }



    //Called to configure the tab for the page at the specified position
    //最小にtabインスタンスを生成したのちに呼ばれ、タブとフラグメントの連動をするためびのコールバック関数

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

        Log.d("debug", "IntroductionActivity-onConfigureTab::positionは"+position);
    }



    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.nextButton){

            Intent intent = new Intent(getApplication(), HomeActivity.class);
            startActivity(intent);

            //Activityと全フラグメントを破棄
            finish();

        }
    }


    /**
     * viewPageer2に表示するフラグメントを生成するためのアダプタークラス
     */

    private class MyFragmentAdapter extends FragmentStateAdapter{

        //表示するフラグメントのページ数
        final int PAGES = 8;

        /**
         * コンストラクタ
         * @param fa FragmentAcrtivity Google推奨のパラメータ
         */

        public MyFragmentAdapter(FragmentActivity fa) {
            super(fa);
        }

        //getItem(int posion)メソッドの代替メソッド
        //フラグメントの生成が必要になる際に呼ばれる
        //前後のフラグメントが存在しない場合に呼ばれる
        @Override
        public Fragment createFragment(int position) {

            //Log.d("debug", "IntroductionActivity-MyFragmentAdapter-createFragment::positionは"+position);

            Fragment fragment = null;

            switch (position){

                case 0:
                    fragment = new FirstFragment();
                    break;

                case 1:
                    fragment = new SecondFragment();
                    break;

                case 2:
                    fragment = new ThirdFragment();
                    break;

                case 3:
                    fragment = new FourthFragment();
                    break;

                case 4:
                    fragment = new FifthFragment();
                    break;

                case 5:
                    fragment = new SixthFragment();
                    break;

                case 6:
                    fragment = new SeventhFragment();
                    break;

                case 7:
                    fragment = new EightFragment();
                    break;

            }

            return fragment;
        }

        //getCountメソッドの代替メソッド
        //表示するフラグメントの合計ページ数
        //スワイプするたびに呼ばれるメソッド
        @Override
        public int getItemCount() {
            //Log.d("debug", "IntroductionActivity-MyFragmentAdapter-getItemCount");
            return PAGES;
        }
    }

    //バックキーが押されたときに呼ばれるコールバック関数

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();

        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("debug", "IntroductionActivity:アクティビティが破棄されました");
    }
}