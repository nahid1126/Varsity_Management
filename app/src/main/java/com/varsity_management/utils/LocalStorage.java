package com.varsity_management.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    private static final String TAG = "LocalStorage";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public LocalStorage(Context context) {
        sharedPreferences = context.getSharedPreferences("VM", 0);
        editor = sharedPreferences.edit();
    }

    public void putLoginResponse(boolean isLogin) {
        editor.putBoolean(VMConstants.LOGIN_RESPONSE, isLogin);
        editor.apply();
    }

    public boolean getLoginResponse() {
        return sharedPreferences.getBoolean(VMConstants.LOGIN_RESPONSE, false);
    }

    public void putProfession(String prof) {
        editor.putString(VMConstants.PROFESSION, prof);
        editor.apply();
    }

    public String getProfession() {
        return sharedPreferences.getString(VMConstants.PROFESSION, "");
    }

    public void putId(String id) {
        editor.putString(VMConstants.DISPLAY_ID, id);
        editor.apply();
    }

    public String getID() {
        return sharedPreferences.getString(VMConstants.DISPLAY_ID, "");
    }

    public void putName(String name) {
        editor.putString(VMConstants.DISPLAY_NAME, name);
        editor.apply();
    }

    public String getName() {
        return sharedPreferences.getString(VMConstants.DISPLAY_NAME, "");
    }

    public void putDept(String dept) {
        editor.putString(VMConstants.DEPARTMENT_NAME, dept);
        editor.apply();
    }

    public String getDept() {
        return sharedPreferences.getString(VMConstants.DEPARTMENT_NAME, "");
    }
}
