package com.james.minigame;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by James on 14.09.2015.
 */
public class MiniGameApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
