package com.bettingtipsking.app.activity;

import static com.bettingtipsking.app.Helper.HelperClass.IS_LOGIN;
import static com.bettingtipsking.app.Helper.HelperClass.REVIEW_TIME;
import static com.bettingtipsking.app.Helper.HelperClass.SHARED_PREFERENCE;
import static com.bettingtipsking.app.Helper.HelperClass.isSubValid;
import static com.bettingtipsking.app.Helper.HelperClass.logout;
import static com.bettingtipsking.app.Helper.HelperClass.msgDialogue;
import static com.bettingtipsking.app.Helper.HelperClass.reviewDialogue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bettingtipsking.app.activity.Favourite.FavouriteFragment;
import com.bettingtipsking.app.activity.Home.HomeFragment;
import com.bettingtipsking.app.activity.fixtures.FixturesFragment;
import com.bettingtipsking.app.R;

import com.bettingtipsking.app.activity.fixtures.SubscriptionsActivity;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    NavigationView navigationView;
    MenuItem nav_login_logout;
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isSubValid(this)) {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.color11)));


        FirebaseMessaging.getInstance().subscribeToTopic("news");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_subscription:
                        startActivity(new Intent(MainActivity.this, SubscriptionsActivity.class));
                        break;
                    case R.id.nav_login_logout:
                        if (isLogin){
                            logout(MainActivity.this);
                            nav_login_logout.setTitle("Login");
                            nav_login_logout.setIcon(R.drawable.ic_login);
                        }else {
                           // startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                        break;
                    case R.id.nav_forgot_password:
                     //   startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
                        break;
                    case R.id.nav_d_rateus:
                        launchMarket();
                        break;
                    case R.id.nav_d_share:
                        Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
                        intent2.setType("text/plain");
                        intent2.putExtra(Intent.EXTRA_TEXT, "If you want to take the betting tips for football you can download this app https://play.google.com/store/apps/details?id=com.bettingtipsking.app" );
                        startActivity(Intent.createChooser(intent2, "Share via"));
                        break;

                    case R.id.nav_d_policy:
                        String urlString = "https://docs.google.com/document/d/1wjzSktM2_ycljU9QTY_YF9OGGGgvxFUECo_G6wEGfRg/edit?usp=sharing";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setPackage("com.android.chrome");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException ex) {
                            // Chrome browser presumably not installed so allow user to choose instead
                            intent.setPackage(null);
                            startActivity(intent);
                        }
                        break;


                }


                return true;

            }
        });


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.fragment_container);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE);
        long reviewTime = sharedPreferences.getLong(REVIEW_TIME, 0);
        if (reviewTime == 0){
            msgDialogue(this);
        }
    }

    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.nav_fixtures:
                            selectedFragment = new FixturesFragment();
                            break;

                        case R.id.nav_favourit:
                            selectedFragment = new FavouriteFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    protected void onStart() {
        super.onStart();
        checkLoginStatus();
    }

    public void checkLoginStatus(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean(IS_LOGIN, false);

        Menu menu = navigationView.getMenu();
        nav_login_logout = menu.findItem(R.id.nav_login_logout);
        if (isLogin){
            nav_login_logout.setTitle("Logout");
            nav_login_logout.setIcon(R.drawable.ic_logout);
        }else {
            nav_login_logout.setTitle("Login");
            nav_login_logout.setIcon(R.drawable.ic_login);
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE);
        long difference = new Date().getTime() - sharedPreferences.getLong(REVIEW_TIME, 0);
        if (difference > (7 * 24 * 60 * 60 * 1000))
            reviewDialogue(this);
        else super.onBackPressed();
    }
}