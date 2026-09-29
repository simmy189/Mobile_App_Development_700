package com.smartpantry.manager;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smartpantry.manager.database.DatabaseSeeder;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "SmartPantryPrefs";
    private static final String KEY_DB_SEEDED = "db_seeded";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up Navigation Component
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            NavigationUI.setupWithNavController(bottomNav, navController);
        }

        // Seed database on first run
        seedDatabaseIfNeeded();
    }

    private void seedDatabaseIfNeeded() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean alreadySeeded = prefs.getBoolean(KEY_DB_SEEDED, false);
        if (!alreadySeeded) {
            DatabaseSeeder.seedIfEmpty(getApplicationContext());
            prefs.edit().putBoolean(KEY_DB_SEEDED, true).apply();
        }
    }
}
