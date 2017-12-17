package com.startach.yedidim.MainPageFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.startach.yedidim.R;
import com.startach.yedidim.modules.App;
import com.startach.yedidim.modules.auth.AuthModule;
import com.startach.yedidim.modules.settingsfragment.SettingsFragmentModule;
import com.startach.yedidim.modules.settingsfragment.SettingsFragmentViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    @Inject
    SettingsFragmentViewModel settingsFragmentViewModel;

    @BindView(R.id.loguut_button)
    Button logoutButton;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this,view);
        ((App) getActivity().getApplication()).getComponent()
                .newSettingsFragmentSubComponent(new SettingsFragmentModule(this),new AuthModule())
                .inject(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RxView.clicks(logoutButton)
                .subscribe(ignore->logOutApplication());
    }

    private void logOutApplication() {
        settingsFragmentViewModel.logout();
        getActivity().finish();
    }

}
