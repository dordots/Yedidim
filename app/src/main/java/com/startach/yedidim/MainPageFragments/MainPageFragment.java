package com.startach.yedidim.MainPageFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.startach.yedidim.MainPageViewModel;
import com.startach.yedidim.MainPageViewModelImpl;
import com.startach.yedidim.R;
import com.startach.yedidim.entities.notification.NotificationDeviceIdSyncer;
import com.startach.yedidim.entities.usermanagement.UserManager;
import com.startach.yedidim.modules.App;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.ghyeok.stickyswitch.widget.StickySwitch;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {

    @BindView(R.id.sticky_switch)
    StickySwitch stickySwitch;

    @Inject
    UserManager userManager;

    @Inject
    NotificationDeviceIdSyncer deviceIdSyncer;

    MainPageViewModel mainPageViewModel;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Disposable disposable;
    Disposable changeStateDisposable = Disposables.empty();

    public MainPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        ButterKnife.bind(this,view);
        ((App)getActivity().getApplication()).getComponent().inject(this);

        mainPageViewModel = new MainPageViewModelImpl(userManager,deviceIdSyncer);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disposable = mainPageViewModel.isActive()
                .subscribe(this::setStickySwitchDirection);
        compositeDisposable.add(disposable);

        stickySwitch.setOnSelectedChangeListener(
                (direction, s) -> {
                    changeStateDisposable.dispose();
                    changeStateDisposable =
                            mainPageViewModel.setActivateState(direction.equals(StickySwitch.Direction.LEFT))
                                    .subscribe(
                                            () -> {
                                            }
                                            , throwable -> Timber.e(throwable, "can't refresh token"));
                    compositeDisposable.add(changeStateDisposable);
                });
    }

    private void setStickySwitchDirection(Boolean aBoolean) {
        stickySwitch.setDirection(aBoolean ? StickySwitch.Direction.LEFT : StickySwitch.Direction.RIGHT);
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
        super.onDestroyView();
    }
}
