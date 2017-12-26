package com.startach.yedidim.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.startach.yedidim.MainPageFragments.AboutUsFragment;
import com.startach.yedidim.MainPageFragments.MainPageFragment;
import com.startach.yedidim.MainPageFragments.SettingsFragment;
import com.startach.yedidim.R;
import com.startach.yedidim.entities.usermanagement.UserManager;
import com.startach.yedidim.modules.App;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    
    private Fragment settingsFragment = new SettingsFragment();
    private Fragment aboutUsFragment = new AboutUsFragment();

    //    private Fragment developersFragment = new DevelopersFragment();
//    private Fragment personalFragment = new PersonalInfoFragment();
    private Fragment mainFragment = null;

    @Inject
    UserManager userManager;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getApplication())
                .getComponent()
                .inject(this);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (mainFragment == null) {
            mainFragment = new MainPageFragment();
            startFragment(mainFragment);
        }

        final View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = (TextView) headerView.findViewById(R.id.username);
        TextView emailTextView = (TextView) headerView.findViewById(R.id.email);

        userManager.getCurrentUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(volunteer -> {
                    Timber.d("User Data : %s,%s", volunteer.getLastName(), volunteer.getEmailAddress());
                    userNameTextView.setText(String.format("%s %s", volunteer.getFirstName(), volunteer.getLastName()));
                    emailTextView.setText(volunteer.getEmailAddress());
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

// if (id == R.id.nav_personal_info) {
//            setTitle(R.string.title_nav_personal_information);
//            startFragment(personalFragment);
//        } else
        if (id == R.id.nav_settings) {
            setTitle(R.string.title_nav_settings);
            startFragment(settingsFragment);
//        } else if (id == R.id.nav_developers) {
//            setTitle(R.string.title_nav_developers);
//            startFragment(developersFragment);
        } else if (id == R.id.nav_about_us) {
            setTitle(R.string.title_nav_about_us);
            startFragment(aboutUsFragment);
        } else if (id == R.id.nav_val_screen) {
            setTitle(R.string.title_nav_val_page);
            startFragment(mainFragment);
        }

        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.fragmentHolder, fragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }
}
