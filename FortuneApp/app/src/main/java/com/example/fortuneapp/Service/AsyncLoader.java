package com.example.fortuneapp.Service;

/** AsyncTask<String, Integer, JSONObject> */
/** 第1引数 doInBackground
 *  第2引数 onProgressUpdate
 *  第3引数　onPostExecute */

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.fortuneapp.Utilize.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/** このクラスの非同期を実行するにはexcecute(...)をメインスレッドから呼び出す */

public class AsyncLoader extends AsyncTask<String, Void, JSONObject> {

    private TextView text_content;
    private TextView text_item;
    private TextView text_money;
    private TextView text_total;
    private TextView text_job;
    private TextView text_color;
    private TextView text_love;
    private TextView text_rank;

    private String date;

    /**
     * コンストラクタ
     * フィールドを初期化
     * @param text1 context
     * @param text2 item
     * @param text3 money
     * @param text4 total
     * @param text5 job
     * @param text6 color
     * @param text7 love
     * @param text8 rank
     * @retrun json onPostExecuteに渡す
     * */
    public AsyncLoader(TextView text1, TextView text2, TextView text3, TextView text4, TextView text5, TextView text6, TextView text7, TextView text8,  String date){
        this.text_content = text1;
        this.text_item = text2;
        this.text_money = text3;
        this.text_total = text4;
        this.text_job = text5;
        this.text_color = text6;
        this.text_love = text7;
        this.text_rank = text8;
        this.date = date;
    }

    /** バックグランドで行うメインの処理
     * @param params executeの引数
     * */
    @Override
    protected JSONObject doInBackground(String... params) {
        HttpURLConnection con = null;
        StringBuilder builder = new StringBuilder();
        JSONObject json = new JSONObject();
        try {
            URL url = new URL(params[0]);
            con = (HttpURLConnection) url.openConnection();
            InputStream stream = con.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line = "";
            while ((line = reader.readLine()) != null)
                builder.append(line);
            stream.close();

            json = new JSONObject(builder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally{
            con.disconnect();
        }

        //HTTP通信により得たjsonファイルをonPostExceuteメソッドの引数に渡す
        return json;
    }

    /** 非同期処理が終了後、結果をメインスレッドに返す */
    @Override
    protected void onPostExecute(JSONObject json) {

        //content:今日の運勢
        StringBuilder content = new StringBuilder();
        //item:ラッキーアイテム
        StringBuilder item = new StringBuilder();
        //money:金運
        StringBuilder money = new StringBuilder();
        //total:総合運
        StringBuilder total = new StringBuilder();
        //job:仕事運
        StringBuilder job = new StringBuilder();
        //color:ラッキーカラー
        StringBuilder color = new StringBuilder();
        //love:恋愛運
        StringBuilder love = new StringBuilder();
        //rank:星座別の総合ランキング
        StringBuilder rank = new StringBuilder();


        try {
            JSONArray array = json.getJSONObject("horoscope").getJSONArray(date);
            for (int i = 0; i < array.length(); i++) {

                if(i == Util.position) {
                    JSONObject obj = array.getJSONObject(i);
                    //keyに対する値を取得する
                    content.append(obj.getString("content"));
                    item.append(obj.getString("item"));
                    money.append(obj.getString("money"));
                    total.append(obj.getString("total"));
                    job.append(obj.getString("job"));
                    color.append(obj.getString("color"));
                    love.append(obj.getString("love"));
                    rank.append(obj.getString("rank"));
                }
            }

            text_content.setText(content.toString());
            text_item.setText(item.toString());
            text_money.setText(money.toString());
            text_total.setText(total.toString());
            text_job.setText(job.toString());
            text_color.setText(color.toString());
            text_love.setText(love.toString());
            text_rank.setText(rank.toString() + "位");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** 本日の日付を取得する */
    private String getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String result = sdf.format(date);
        return result;
    }
}