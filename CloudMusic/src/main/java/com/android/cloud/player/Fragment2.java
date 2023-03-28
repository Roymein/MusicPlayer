package com.android.cloud.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.cloud.R;

public class Fragment2 extends Fragment {

    //显示布局
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //创建一个View
        return inflater.inflate(R.layout.frag2_layout, null);
    }
}
