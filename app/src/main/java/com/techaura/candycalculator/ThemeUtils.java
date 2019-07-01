package com.techaura.candycalculator;

import android.util.Log;

public class ThemeUtils {

    public static String identifyTheme(int theme) {
        Log.e("NEW THEME", theme + "");

        switch (theme) {
            case -14606047:
                // dark
                return "dark";
            case -10354450:
                // purple
                return "purple";
            case -16524602:
                // teal
                return "teal";
            case -16752540:
                // dark cyan
                return "darkCyan";
            case -6785:
                // amber
                return "amber";
            case -3862174:
                // dark pink
                return "darkPink";
            case -14972955:
                // blue
                return "blue";
        }

        return "light";
    }

}
