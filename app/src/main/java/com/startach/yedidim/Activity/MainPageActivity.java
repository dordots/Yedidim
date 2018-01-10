package com.startach.yedidim.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.startach.yedidim.BuildConfig;
import com.startach.yedidim.MainPageFragments.AboutStartachFragment;
import com.startach.yedidim.MainPageFragments.AboutUsFragment;
import com.startach.yedidim.MainPageFragments.EventsFragment;
import com.startach.yedidim.MainPageFragments.MainPageFragment;
import com.startach.yedidim.MainPageFragments.SettingsFragment;
import com.startach.yedidim.R;
import com.startach.yedidim.entities.usermanagement.UserManager;
import com.startach.yedidim.modules.App;
import com.testfairy.TestFairy;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    private Fragment settingsFragment = new SettingsFragment();
    private Fragment aboutUsFragment = new AboutUsFragment();
    private Fragment aboutStratAchFragment = new AboutStartachFragment();
    private Fragment eventsFragment = new EventsFragment();

    //    private Fragment developersFragment = new DevelopersFragment();
//    private Fragment personalFragment = new PersonalInfoFragment();
    private Fragment mainFragment = null;

    @Inject
    UserManager userManager;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.version_number)
    TextView versionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication())
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


//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (mainFragment == null) {
            mainFragment = new MainPageFragment();
            startFragment(mainFragment);
        }

        versionNumber.setText(BuildConfig.VERSION_NAME);

        final View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = (TextView) headerView.findViewById(R.id.username);
        TextView emailTextView = (TextView) headerView.findViewById(R.id.email);

        userManager.getCurrentUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(volunteer -> {
                    if (volunteer.getId() == null || volunteer.getId().isEmpty()) {
                        Timber.e("no user id, user probably not exists in volunteer table");
                        new AlertDialog.Builder(this)
                                .setTitle(R.string.error_no_volunteer_found_title)
                                .setMessage(R.string.error_no_volunteer_found_message)
                                .setPositiveButton(android.R.string.ok, (dialog, which) -> this.finish())
                                .setCancelable(false)
                                .show();
                    } else {
                        Timber.d("User Data : %s,%s", volunteer.getLastName(), volunteer.getEmailAddress());
                        final String userName = String.format("%s %s", volunteer.getFirstName(), volunteer.getLastName());
                        userNameTextView.setText(userName);
                        emailTextView.setText(volunteer.getEmailAddress());
                        TestFairy.setUserId(userName);
                    }
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        }
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
        } else if (id == R.id.nav_events) {
            setTitle(R.string.title_events);
            startFragment(eventsFragment);
        } else if (id == R.id.nav_about_us) {
            setTitle(R.string.title_nav_about_us);
            startFragment(aboutUsFragment);
        } else if (id == R.id.nav_about_startach) {
            setTitle(R.string.title_nav_about_startach);
            startFragment(aboutStratAchFragment);
        } else if (id == R.id.nav_val_screen) {
            setTitle(R.string.title_nav_val_page);
            startFragment(mainFragment);
        }

        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {

            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
