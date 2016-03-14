package com.pingstart.sample;

import android.app.Application;

import com.pingstart.sample.utils.MyCrashHandler;

public class PingStartApplication extends Application {

    @Override
    public void onCreate() {
        MyCrashHandler.getInstance().register(this);

    }
}