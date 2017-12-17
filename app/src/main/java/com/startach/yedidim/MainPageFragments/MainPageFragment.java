package com.startach.yedidim.MainPageFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.startach.yedidim.R;
import com.startach.yedidim.entities.usermanagement.UserManager;
import com.startach.yedidim.modules.App;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.ghyeok.stickyswitch.widget.StickySwitch;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {

    @BindView(R.id.sticky_switch)
    StickySwitch stickySwitch;

    @Inject
    UserManager userManager;

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
        final View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        ButterKnife.bind(this,view);
        ((App)getActivity().getApplication()).getComponent().inject(this);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stickySwitch.setDirection(userManager.getActive() ? StickySwitch.Direction.LEFT : StickySwitch.Direction.RIGHT);
        stickySwitch.setOnSelectedChangeListener(
                (direction, s) -> {
                    userManager.setActive(direction.equals(StickySwitch.Direction.LEFT));
                    Toast.makeText(MainPageFragment.this.getActivity(), "Direction : " + direction.name() + " String : " + s, Toast.LENGTH_SHORT).show();
                });
    }
}
