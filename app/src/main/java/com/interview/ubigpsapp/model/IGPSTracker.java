/*
  IGPSTracker interface

  Interface for GPS tracker

  @author Joffrey Cousin
  @version 1.0
 */

package com.interview.ubigpsapp.model;

import android.content.Context;

public interface IGPSTracker {

    //Register listener
    void registerListener(OnLocationChangeListener onLocationChangeListener);

    //Unregister listener
    void unregisterListener(OnLocationChangeListener onLocationChangeListener);

    //Set speed
    void setSpeed(double speed);

    //Set distance
    void setDistance(double speed);

    //Get distance
    double getDistance();

    //Get speed
    double getSpeed();

    //Start capture
    void startCapture(Context context);

    //Stop capture
    void stopCapture();

    //Reset capture data
    void resetCapture();
}
