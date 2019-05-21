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

public class GPSTrackerSingleton implements LocationListener{

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meters
    private static final long MIN_TIME_BW_UPDATES = 500; // 0.5 seconds
    private static final long MIN_SPEED = 5; // 5 km/h => to avoid non accurate speed

    //singleton instante
    private static volatile GPSTrackerSingleton mInstance = null;

    //location change listener
    private OnLocationChangeListener mOnLocationChangeListener;

    //GPS data model storer
    private GPSData mGPSData = new GPSData();

    //last GPS location
    private Location mLastlocation = new Location("last");

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

    //start capture location
    public void startCaptureLocation(Context context)
    {
        //check permissions first
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)
        {
            //request location update
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                    this);
        }
    }

    //set callback
    public void setLocationCapturerListener(OnLocationChangeListener onLocationChangeListener) {
        this.mOnLocationChangeListener = onLocationChangeListener;
    }

    @Override
    public void onLocationChanged(Location location) {
        // m/s => km/h
        float speedInKmH = location.getSpeed() * 3.6f;

        //Check if driving
        if(!mGPSData.isRunning()) {
            //Check if speed > threshold
            if (speedInKmH >= MIN_SPEED) {
                mGPSData.setRunning(true);

                //Check if first time location changed
                if(mGPSData.isFirstTime())
                {
                    //set lastlocation
                    mLastlocation.set(location);
                    mGPSData.setFirstTime(false);

                    //save start timestamp
                    mGPSData.setStartTS(System.currentTimeMillis());
                }
                else
                {
                    //compute distance from last location
                    double distance = mLastlocation.distanceTo(location);

                    //update distance
                    mGPSData.addDistance(distance);
                    mLastlocation.set(location);
                }

                //update current speed
                mGPSData.setCurSpeed(speedInKmH);
                if (mOnLocationChangeListener != null) {
                    //callback
                    mOnLocationChangeListener.speedChanged(mGPSData.getCurSpeed());
                }

            }
        }
        //if driving
        else {

            //add distance
            double distance = mLastlocation.distanceTo(location);
            mGPSData.addDistance(distance);
            mLastlocation.set(location);

            //if speed too low => no driving anymore
            if (speedInKmH < MIN_SPEED) {
                //set stopped timestamps
                mGPSData.setStopTS(System.currentTimeMillis());
                if (mOnLocationChangeListener != null) {
                    //callback average speed
                    mOnLocationChangeListener.stoppedDrive(mGPSData.getAverageSpeed());
                }

                //reset data model
                mGPSData = new GPSData();
            }
            //if speed OK
            else{
                //update current speed
                mGPSData.setCurSpeed(speedInKmH);

                if (mOnLocationChangeListener != null) {
                    //callback current speed
                    mOnLocationChangeListener.speedChanged(mGPSData.getCurSpeed());
                }
            }
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