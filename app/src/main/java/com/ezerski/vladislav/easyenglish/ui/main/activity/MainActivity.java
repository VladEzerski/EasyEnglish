package com.ezerski.vladislav.easyenglish.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ezerski.vladislav.easyenglish.R;
import com.ezerski.vladislav.easyenglish.ui.auth.SignInActivity;
import com.ezerski.vladislav.easyenglish.ui.main.fragment.WordsListFragment;
import com.ezerski.vladislav.easyenglish.ui.profile.ProfileFragment;
import com.ezerski.vladislav.easyenglish.ui.tests.activity.TestsActivity;
import com.ezerski.vladislav.easyenglish.ui.tests.fragment.TestsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
        } else {
            mUsername = mFirebaseUser.getEmail();
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);

        View headerLayout = navigationView.getHeaderView(0);
        TextView headerTitle = headerLayout.findViewById(R.id.tv_header_title);
        headerTitle.setText(mUsername);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_main_content_container);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_main_content_container, new WordsListFragment())
                    .commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_main_content_container);
            switch (item.getItemId()) {
                case R.id.action_dictionary:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_main_content_container, new WordsListFragment())
                            .commit();
                    break;
                case R.id.action_tests:
                    startActivity(new Intent(MainActivity.this, TestsActivity.class));
                    break;
                case R.id.action_profile:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_main_content_container, new ProfileFragment())
                            .commit();
                    break;
            }
            return false;
        }
    };

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_first_item:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_main_content_container, new WordsListFragment())
                        .commit();
                break;
            case R.id.nav_second_item:
                mFirebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}