package com.example.civilink;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "CiviLinkSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_AUTH_TOKEN = "authToken";
    
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    
    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }
    
    public void createLoginSession(String userId, String email, String authToken) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_AUTH_TOKEN, authToken);
        editor.commit();
    }
    
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    
    public void clearSession() {
        editor.clear();
        editor.commit();
    }
    
    public String getUserId() {
        return pref.getString(KEY_USER_ID, null);
    }
    
    public String getAuthToken() {
        return pref.getString(KEY_AUTH_TOKEN, null);
    }
}