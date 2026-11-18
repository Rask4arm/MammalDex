package com.example.mammaldex.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages the user's location using SharedPreferences.
 * Determines where the application is allowed to automatically
 * retrieve the user's location.
 */
public class LocationPreferences {

    private static final String PREF_NAME = "location_preferences";
    private static final String KEY_LOCATION_ENABLED = "location_enabled";

    /**
     * Checks if location retrieval is enabled in the settings
     * @param context application context used to access SharedPreferences
     * @return if the settings are enabled or not
     */
    public static boolean isLocationEnabled(Context context){
        SharedPreferences preferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_LOCATION_ENABLED, false);
    }

    /**
     * Updates the user's location preference
     * @param context application context used to access SharedPreferences
     * @param enabled whether the location retrieval is enabled or disabled
     */
    public static void setKeyLocationEnabled(Context context, boolean enabled){
        SharedPreferences preferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(KEY_LOCATION_ENABLED, enabled).apply();
    }
}
