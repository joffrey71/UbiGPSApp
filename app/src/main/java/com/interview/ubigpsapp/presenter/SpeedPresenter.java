/*
  Presenter that deals with view and model

  @author Joffrey Cousin
 * @version 1.0
 */

package com.interview.ubigpsapp.presenter;

import android.support.annotation.NonNull;

import com.interview.ubigpsapp.SpeedContract;
import com.interview.ubigpsapp.model.IGPSTracker;
import com.interview.ubigpsapp.model.OnLocationChangeListener;

public class SpeedPresenter implements SpeedContract.Presenter, OnLocationChangeListener {
    //store view
    private final SpeedContract.View mSpeedView;

    private static final long MIN_SPEED = 10; // 10 km/h => to avoid non accurate speed

    private boolean mStarted = false;
    private boolean mDriving = false;
    private long mStartTS;
    private long mStopTS;

    private IGPSTracker mGPSTracker;

    public SpeedPresenter(@NonNull SpeedContract.View view, @NonNull IGPSTracker tracker) {
        mSpeedView = view;
        mGPSTracker = tracker;
    }

    @Override
    public void start() {
        mGPSTracker.registerListener(this);
        mGPSTracker.startCapture(mSpeedView.getContext());

        mStarted = true;
        mDriving = false;
        mStartTS = 0;
        mStopTS = 0;

        mSpeedView.displaySpeed(0);
    }

    @Override
    public void stop() {
        if(mStarted) {
            mStarted = false;

            //stop GPS to capture
            mGPSTracker.stopCapture();

            //unregister listener
            mGPSTracker.unregisterListener(this);

            //reset GPS tracker for next capture
            mGPSTracker.resetCapture();

            //update view
            mSpeedView.displaySpeed(0);
        }
    }

    @Override
    public void startCapturing() {

    }

    @Override
    public void stopCapturing() {

    }

    @Override
    public void speedChanged(double speed) {
        //Check if driving
        if(!mDriving)
        {
            //Check if speed > threshold
            if (speed >= MIN_SPEED) {
                mDriving = true;

                //save start timestamp
                mStartTS = System.currentTimeMillis();

                //update view speed
                mSpeedView.displaySpeedAverage(0);
                mSpeedView.displaySpeed(speed);
            }
        }
        //if driving
        else
        {
            //if speed too low => no driving anymore
            if(speed < MIN_SPEED){

                //stop driving
                mDriving = false;

                //get stop timestamp
                mStopTS = System.currentTimeMillis();

                //compute average speed
                double averageSpeed = getAverageSpeed(mGPSTracker.getDistance());

                //update view speed
                mSpeedView.displaySpeed(0);
                mSpeedView.displaySpeedAverage(averageSpeed);

                //reset capture
                mGPSTracker.resetCapture();
            }
            //if speed OK
            else{
                //update view speed
                mSpeedView.displaySpeed(speed);
            }
        }
    }

    @Override
    public void distanceChanged(double distance) {
    }

    private double getAverageSpeed(double distance){
        long drivingTime = mStopTS - mStartTS;
        double average;
        if (drivingTime <= 0){
            average = 0.0;
        } else {
            average = (distance / (drivingTime / 1000.0)) * 3.6;
        }
        return average;
    }
}
