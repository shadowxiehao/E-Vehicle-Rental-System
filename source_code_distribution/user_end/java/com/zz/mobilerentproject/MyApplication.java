package com.zz.mobilerentproject;

import android.app.Application;
import android.os.Build;

import com.tencent.mmkv.MMKV;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);
    }
}
