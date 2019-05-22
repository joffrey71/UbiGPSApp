/*
  MainApp

  This class overrides onCreate to init singleton context

  @author Joffrey Cousin
  @version 1.0
 */

package com.interview.ubigpsapp;

import android.app.Application;

import com.interview.ubigpsapp.model.GPSTrackerSingleton;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // init singleton context
        GPSTrackerSingleton.getInstance().initContext(this);
    }
}