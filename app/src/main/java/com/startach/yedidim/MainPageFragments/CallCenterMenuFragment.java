package com.startach.yedidim.MainPageFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.startach.yedidim.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallCenterMenuFragment extends Fragment {


    public CallCenterMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call_center_menu, container, false);
    }

}
