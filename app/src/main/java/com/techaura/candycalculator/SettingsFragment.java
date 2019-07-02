package com.techaura.candycalculator;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
        if (colorPickerPreference != null) {
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

        Preference feedbackPreference = findPreference("send_feedback");
        if (feedbackPreference != null) {
            feedbackPreference.setOnPreferenceClickListener((Preference preference) -> {
                if (getActivity() != null) sendFeedback(getActivity());
                return true;
            });
        }

    }

    private void sendFeedback(Context context) {
        String body = null;

        try {
            body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            body = "\n\n-----------------------------\nPlease don't remove this information\n\n Device OS: Android \n Device OS version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;

        } catch (PackageManager.NameNotFoundException e) {
            body = "Please enter the following information alongside your feedback\n\n Device OS: Android" +
                    "\n Device OS Version: \n Device Brand: \n Device Model: \n App Version: ";
        }

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{context.getString(R.string.feedback_summary)});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Candy Calculator Feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        if (emailIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(emailIntent);
        }

    }

}
