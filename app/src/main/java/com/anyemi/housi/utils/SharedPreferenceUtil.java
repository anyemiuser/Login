package com.anyemi.housi.utils;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Surya Teja.
 */

public class SharedPreferenceUtil {


    private SharedPreferences preferences;
    private static final String TAG = "SharedPreferenceUtil";

    private SharedPreferenceUtil() {
        // Add a private constructor to hide the implicit public one.
        // no call here
    }

    /**
     * Landlord Side Constant Parameters
     */


    // Set At account creation, LOgin only
    // need to modify
    public static String setMobile_number(Context context, String header) {

        try {
            saveInPreference(context, "mobile_number", header);
        } catch (Exception e) {
            //PrintLog.print(TAG, e.getMessage());
            e.printStackTrace();
        }
        return header;
    }

    public static String getMobile_number(Context context) {
        try {
            return getFromPreference(context, "mobile_number", "");
        } catch (Exception e) {
            //  PrintLog.print(TAG, e.getMessage());
            e.printStackTrace();
            return "0";
        }
    }

    public static String setLastSyncDate(Context context, String header) {

        try {
            saveInPreference(context, "LastSyncDate", header);
        } catch (Exception e) {
            //PrintLog.print(TAG, e.getMessage());
            e.printStackTrace();
        }
        return header;
    }

    public static String getLastSyncDate(Context context) {
        try {
            return getFromPreference(context, "LastSyncDate", "");
        } catch (Exception e) {
            //  PrintLog.print(TAG, e.getMessage());
            e.printStackTrace();
            return "0";
        }
    }

    public static String setMediaData(Context context, String header) {

        try {
            saveInPreference(context, "MediaData", header);
        } catch (Exception e) {
            //PrintLog.print(TAG, e.getMessage());
            e.printStackTrace();
        }
        return header;
    }


    public static String getMediaData(Context context) {
        try {
            return getFromPreference(context, "MediaData", "");
        } catch (Exception e) {
            //  PrintLog.print(TAG, e.getMessage());
            e.printStackTrace();
            return "0";
        }
    }


    //***********filters*****************//

    public static void saveInPreference(Context context, String key, String value) {
        SharedPreferences spf = getSharedPreference(context);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getFromPreference(Context context, String key, String defaultValue) {
        SharedPreferences spf = getSharedPreference(context);
        return spf.getString(key, defaultValue);
    }

    public static void saveInPreference(Context context, String key, Boolean value) {
        SharedPreferences spf = getSharedPreference(context);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static Boolean getFromPreference(Context context, String key, Boolean defaultValue) {
        SharedPreferences spf = getSharedPreference(context);
        return spf.getBoolean(key, defaultValue);
    }

    public static void saveInPreference(Context context, String key, long value) {
        SharedPreferences spf = getSharedPreference(context);
        SharedPreferences.Editor editor = spf.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getFromPreference(Context context, String key, long defaultValue) {
        SharedPreferences spf = getSharedPreference(context);
        return spf.getLong(key, defaultValue);
    }

    public static SharedPreferences getSharedPreference(Context context) {
//        context.getSharedPreferences(Globals.LOGIN_SPF_NAME, Context.MODE_PRIVATE);
        return context.getSharedPreferences("JM_MEDIA", Context.MODE_PRIVATE);
    }

    /**
     * Reset the key and remove the value
     *
     * @param key
     * @param context
     */
    public static void resetKey(String key, Context context) {
        try {
            SharedPreferences spf = getSharedPreference(context);
            SharedPreferences.Editor editor = spf.edit();
            if (spf.contains(key)) {
                editor.remove(key);
                editor.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Clear all the keys and data
     *
     * @param context
     */
    public static void clearAll(Context context) {
        try {
            SharedPreferences spf = getSharedPreference(context);
            SharedPreferences.Editor editor = spf.edit();
            editor.clear();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}