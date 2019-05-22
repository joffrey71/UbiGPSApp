/*
  Singleton for location management

  This singleton is used to deal with location event.
  It computes speed and update GPSData model

  @author Joffrey Cousin
 * @version 1.0
 */

package com.interview.ubigpsapp.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class GPSTrackerSingleton implements IGPSTracker, LocationListener {

    private static final long MIN_TIME_BW_UPDATES = 250; // 0.25 seconds

    //singleton instance
    private static volatile GPSTrackerSingleton mInstance = null;

    //app context
    private Context mAppContext;

    //location change listener
    private OnLocationChangeListener mOnLocationChangeListener;

    //GPS data model storer
    private GPSData mGPSData = new GPSData();

    //location manager
    private LocationManager mLocationManager = null;

    //singleton access
    public static GPSTrackerSingleton getInstance() {
        if (mInstance == null) {
            synchronized (GPSTrackerSingleton.class) {
                if (mInstance == null) {
                    mInstance = new GPSTrackerSingleton();
                }
            }
        }
        return mInstance;
    }

    //constructor
    private GPSTrackerSingleton() {
    }

    public void initContext(Context context){
        mAppContext = context;
    }

    @Override
    //start capture location
    public void startCapture() {
        //check context not null
        if (mAppContext != null) {
            //check permissions first
            if (ActivityCompat.checkSelfPermission(mAppContext, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mAppContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                //request location update
                mLocationManager = (LocationManager) mAppContext.getSystemService(Context.LOCATION_SERVICE);
                mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        0,
                        this);

                if (mOnLocationChangeListener != null)
                    mOnLocationChangeListener.startCapturing();
            }
        }
    }

    //set callback
    public void registerListener(OnLocationChangeListener onLocationChangeListener) {
        mOnLocationChangeListener = onLocationChangeListener;
    }

    @Override
    public void unregisterListener(OnLocationChangeListener onLocationChangeListener) {
        if(onLocationChangeListener == mOnLocationChangeListener)
            mOnLocationChangeListener = null;

    }

    @Override
    public void setSpeed(double speed) {
        if(mOnLocationChangeListener != null) {
            mOnLocationChangeListener.speedChanged(speed);
        }
    }

    @Override
    public void setDistance(double distance) {
        if(mOnLocationChangeListener != null) {
            mOnLocationChangeListener.distanceChanged(distance);
        }
    }

    @Override
    public void stopCapture() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }

        if(mOnLocationChangeListener != null)
            mOnLocationChangeListener.stopCapturing();
    }

    @Override
    public void resetCapture() {
        mGPSData = new GPSData();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(mGPSData != null) {
            mGPSData.setLocation(location);
            setDistance(mGPSData.getLastDistance());
            setSpeed(mGPSData.getSpeed());
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}