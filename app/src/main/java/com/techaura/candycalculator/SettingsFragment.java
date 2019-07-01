package com.techaura.candycalculator;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import com.takisoft.preferencex.ColorPickerPreference;
import com.takisoft.preferencex.PreferenceFragmentCompat;

/**
 * Fragment for SettingsActivity
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
        // Empty constructor
    }

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.calculator_preferences, rootKey);

        final int[] colors = {
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.darkColorPrimary),
                getResources().getColor(R.color.purpleColorPrimary),
                getResources().getColor(R.color.tealColorPrimary),
                getResources().getColor(R.color.darkCyanColorPrimary),
                getResources().getColor(R.color.amberColorPrimary),
                getResources().getColor(R.color.darkPinkColorPrimary),
                getResources().getColor(R.color.blueColorPrimary)};

        ColorPickerPreference colorPickerPreference = findPreference("color_picker");
        if (colorPickerPreference == null) return;
        colorPickerPreference.setColors(colors);

        if (getContext() != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();

            colorPickerPreference.setOnPreferenceChangeListener((Preference preference, Object newValue) -> {
                String theme = ThemeUtils.identifyTheme(Integer.parseInt(newValue.toString()));

                editor.putString("current_theme", theme);
                editor.apply();

                if (getActivity() != null) getActivity().recreate();
                return true;
            });
        }

    }

}
