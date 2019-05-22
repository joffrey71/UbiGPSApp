/*
  GPSData storer

  This class is used to store GPS infos (speed, distance, location)

  @author Joffrey Cousin
  @version 1.0
 */

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

    //Set current location and update Speed / Distance
    void setLocation(Location loc){
        if(mLocation != null){
            mDistanceM += mLocation.distanceTo(loc);
        }
        mLocation = loc;
        if(loc.hasSpeed())
            mSpeed = loc.getSpeed();
    }

    //Return distance
    double getDistance()
    {
        return mDistanceM;
    }

    //Return current speed
    double getSpeed() {
        return mSpeed;
    }
}