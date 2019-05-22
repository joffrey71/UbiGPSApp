/*
  IGPSTracker interface

  Interface for GPS tracker

  @author Joffrey Cousin
  @version 1.0
 */

package com.interview.ubigpsapp.model;

public interface IGPSTracker {

    //Register listener
    void registerListener(OnLocationChangeListener onLocationChangeListener);

    //Unregister listener
    void unregisterListener(OnLocationChangeListener onLocationChangeListener);

    //Set speed
    void setSpeed(double speed);

    //Set distance
    void setDistance(double speed);

    //Start capture
    void startCapture();

    //Stop capture
    void stopCapture();

    //Reset capture data
    void resetCapture();
}
