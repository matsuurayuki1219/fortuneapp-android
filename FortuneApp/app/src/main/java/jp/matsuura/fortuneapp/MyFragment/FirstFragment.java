package jp.matsuura.fortuneapp.MyFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.matsuura.fortuneapp.R;

public class FirstFragment extends Fragment {

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_first, container, false);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("debug", "FirstFragment-onDestroy:フラグメント1が破棄されました");
    }
}