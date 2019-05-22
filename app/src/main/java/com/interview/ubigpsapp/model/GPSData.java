package com.interview.ubigpsapp.model;

import android.location.Location;

class GPSData {
    private double mDistanceM;
    private double mSpeed;
    private Location mLocation;

    GPSData() {
        mDistanceM = 0;
        mSpeed = 0;
        mLocation = null;
    }

    void setLocation(Location loc){
        if(mLocation != null){
            mDistanceM += mLocation.distanceTo(loc);
        }
        mLocation = loc;
        if(loc.hasSpeed())
            mSpeed = loc.getSpeed();
    }

    double getDistance()
    {
        return mDistanceM;
    }

    double getSpeed() {
        return mSpeed;
    }

}