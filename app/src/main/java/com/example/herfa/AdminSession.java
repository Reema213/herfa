package com.example.herfa;

import android.content.Context;
import android.content.SharedPreferences;


public class AdminSession {

    private static final String PREFS_NAME = "herfa_admin";
    private static final String KEY_IS_ADMIN = "is_admin";
    private static final String ADMIN_PIN = "1234";


    public static boolean isAdmin(Context context) {
        return getPrefs(context).getBoolean(KEY_IS_ADMIN, false);
    }


    public static boolean login(Context context, String pin) {
        if (ADMIN_PIN.equals(pin)) {
            getPrefs(context).edit().putBoolean(KEY_IS_ADMIN, true).apply();
            return true;
        }
        return false;
    }


    public static void logout(Context context) {
        getPrefs(context).edit().putBoolean(KEY_IS_ADMIN, false).apply();
    }


    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}