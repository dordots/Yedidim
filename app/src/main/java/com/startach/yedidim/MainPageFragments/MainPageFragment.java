package com.startach.yedidim.MainPageFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.startach.yedidim.R;

import io.ghyeok.stickyswitch.widget.StickySwitch;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {


    public MainPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set Selected Change Listener
//        StickySwitch stickySwitch = (StickySwitch) findViewById(R.id.sticky_switch);
//        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
//            @Override
//            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
//                Log.d(TAG, "Now Selected : " + direction.name() + ", Current Text : " + text);
//            }
//        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }



}
