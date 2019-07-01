package com.techaura.candycalculator;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Allows users to modify look and feel of the app
 */
public class CalculatorSettings extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();
    }

    /**
     * Updates theme
     *
     * @param currentTheme - current theme of the app
     */
    @Override
    public void setAppTheme(String currentTheme) {

        // light
        if (TextUtils.equals(currentTheme, "light")) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            // dark
        } else if (TextUtils.equals(currentTheme, "dark")) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            // purple
        } else if (TextUtils.equals(currentTheme, "purple")) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.PreferenceTheme_Purple);

            // dark cyan
        } else if (TextUtils.equals(currentTheme, "darkCyan")) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.PreferenceTheme_DarkCyan);

            // teal
        } else if (TextUtils.equals(currentTheme, "teal")) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.PreferenceTheme_Teal);

            // amber
        } else if (TextUtils.equals(currentTheme, "amber")) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.PreferenceTheme_Amber);

            // dark pink
        } else if (TextUtils.equals(currentTheme, "darkPink")) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.PreferenceTheme_Pink);

            // blue
        } else if (TextUtils.equals(currentTheme, "blue")) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.PreferenceTheme_Blue);

            // default light
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
