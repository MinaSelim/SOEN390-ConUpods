package com.conupods;
import android.app.Application;
import android.content.Context;

/**
 * This class is used purely to get the application context.
 * This context can be used to access assets through the asset manager.
 */
public class App extends Application {

    private static Application application;

    public static Application getApplication() {
        return application;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}