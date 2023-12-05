package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Context;
import android.content.SharedPreferences;

// Create a utility class, for example, SharedPreferencesUtil.java
public class SharedPreferencesUtil {

    public static String getStoredUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }

    // You can add more utility methods here if needed
}