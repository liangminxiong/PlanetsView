package com.lmx.costumview_java;

import androidx.multidex.MultiDexApplication;

/**
 * Created by lmx on 2020/12/12
 * Describe:
 */
public class App extends MultiDexApplication {

    private static App app;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
