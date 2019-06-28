package com.example.android.calculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public abstract class BaseActivity extends AppCompatActivity {

    private String currentTheme;
    private SharedPreferences sharedPreferences;

    protected boolean isDark = false;
    protected int errorColor = R.color.colorText;

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

        // light
        if (TextUtils.equals(currentTheme, "light")) {
            isDark = false;
            errorColor = R.color.colorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            // dark
        } else if (TextUtils.equals(currentTheme, "dark")) {
            isDark = true;
            errorColor = R.color.darkColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            // purple
        } else if (TextUtils.equals(currentTheme, "purple")) {
            isDark = true;
            errorColor = R.color.purpleColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.BaseTheme_Purple);

            // dark cyan
        } else if (TextUtils.equals(currentTheme, "darkCyan")) {
            isDark = true;
            errorColor = R.color.darkCyanColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.BaseTheme_DarkCyan);

            // teal
        } else if (TextUtils.equals(currentTheme, "teal")) {
            isDark = false;
            errorColor = R.color.tealColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.BaseTheme_Teal);

            // amber
        } else if (TextUtils.equals(currentTheme, "amber")) {
            isDark = false;
            errorColor = R.color.amberColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.BaseTheme_Amber);

            // dark pink
        } else if (TextUtils.equals(currentTheme, "darkPink")) {
            isDark = true;
            errorColor = R.color.darkPinkColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.BaseTheme_DarkPink);

            // blue
        } else if (TextUtils.equals(currentTheme, "blue")) {
            isDark = true;
            errorColor = R.color.blueColorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.BaseTheme_Blue);

            // default light
        } else {
            isDark = false;
            errorColor = R.color.colorAccent;
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }
}
