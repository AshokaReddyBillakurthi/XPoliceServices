package com.xpoliceservices.app;

import android.app.Application;

import com.xpoliceservices.app.utils.PreferenceUtils;

public class XPoliceServiceApplication extends Application {

    public  PreferenceUtils preferenceUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        preferenceUtils = new PreferenceUtils(getApplicationContext());
    }
}
