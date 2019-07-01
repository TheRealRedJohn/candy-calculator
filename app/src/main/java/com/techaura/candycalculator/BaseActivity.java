package com.techaura.candycalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * Base class for CalculatorActivity and SettingsActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

    private String currentTheme; // current theme of app
    private SharedPreferences sharedPreferences; // access new theme of app

    protected boolean isDark = false; // light or dark attribute of new theme (default: light)
    protected int errorColor = R.color.colorAccent; // text color of error message (default: light accent)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentTheme = sharedPreferences.getString("current_theme", "light");
        setAppTheme(currentTheme);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String theme = sharedPreferences.getString("current_theme", "light");
        if (!TextUtils.equals(currentTheme, theme)) {
            recreate();
        }
    }

    public void setAppTheme(String currentTheme) {

        // Light Theme (Default)
        if (TextUtils.equals(currentTheme, "light")) {
            isDark = false;
            errorColor = R.color.colorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            // Dark Theme
        } else if (TextUtils.equals(currentTheme, "dark")) {
            isDark = true;
            errorColor = R.color.darkColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            // Purple Theme
        } else if (TextUtils.equals(currentTheme, "purple")) {
            isDark = true;
            errorColor = R.color.purpleColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.BaseTheme_Purple);

            // Dark Cyan Theme
        } else if (TextUtils.equals(currentTheme, "darkCyan")) {
            isDark = true;
            errorColor = R.color.darkCyanColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.BaseTheme_DarkCyan);

            // Teal Theme
        } else if (TextUtils.equals(currentTheme, "teal")) {
            isDark = false;
            errorColor = R.color.tealColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.BaseTheme_Teal);

            // Amber Theme
        } else if (TextUtils.equals(currentTheme, "amber")) {
            isDark = false;
            errorColor = R.color.amberColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.BaseTheme_Amber);

            // Dark Pink Theme
        } else if (TextUtils.equals(currentTheme, "darkPink")) {
            isDark = true;
            errorColor = R.color.darkPinkColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.BaseTheme_DarkPink);

            // Blue Theme
        } else if (TextUtils.equals(currentTheme, "blue")) {
            isDark = true;
            errorColor = R.color.blueColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.BaseTheme_Blue);

            // Light Theme (Default)
        } else {
            isDark = false;
            errorColor = R.color.colorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

}
