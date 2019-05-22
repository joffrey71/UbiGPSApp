package com.interview.ubigpsapp.model;

import android.content.Context;

public interface IGPSTracker {

    void registerListener(OnLocationChangeListener onLocationChangeListener);
    void unregisterListener(OnLocationChangeListener onLocationChangeListener);
    void setSpeed(double speed);
    void setDistance(double speed);
    double getDistance();
    double getSpeed();
    void startCapture(Context context);
    void stopCapture();
    void resetCapture();
}
