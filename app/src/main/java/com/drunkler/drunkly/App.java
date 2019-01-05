package com.drunkler.drunkly;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * The class is just to get the context of the app
 *
 * @author Oscar
 */
public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * Getter for the context
     * @return The context
     */
    public static Context getContext(){
        return mContext;
    }
}