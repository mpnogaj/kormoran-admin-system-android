package com.mpnogaj.kormoranadminsystem;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Application _app;

    public static Application getApplication(){
        return _app;
    }

    public static Context getAppContext(){
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _app = this;
    }
}
