package com.example.user.injapanapp.app;

import android.app.Application;

public class ThisApplication extends Application {
    private static ThisApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ThisApplication getInstance() {
        return instance;
    }

}
