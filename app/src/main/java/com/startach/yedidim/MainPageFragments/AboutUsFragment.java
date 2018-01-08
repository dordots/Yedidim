package com.startach.yedidim.MainPageFragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.startach.yedidim.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        WebView web = (WebView)view.findViewById(R.id.about_us_content);
        web.setBackgroundColor(Color.TRANSPARENT);
        web.loadUrl("file:///android_asset/aboutus.html");
        return view;
    }

}
