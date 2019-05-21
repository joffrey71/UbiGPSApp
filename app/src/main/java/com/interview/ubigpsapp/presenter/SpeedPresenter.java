/*
  Presenter that deals with view and model

  @author Joffrey Cousin
 * @version 1.0
 */

package com.interview.ubigpsapp.presenter;

import android.support.annotation.NonNull;
import com.interview.ubigpsapp.SpeedContract;
import com.interview.ubigpsapp.model.GPSTrackerSingleton;
import com.interview.ubigpsapp.model.OnLocationChangeListener;

public class SpeedPresenter implements SpeedContract.Presenter, OnLocationChangeListener {
    //store view
    private final SpeedContract.View mSpeedView;

    public SpeedPresenter(@NonNull SpeedContract.View view) {
        mSpeedView = view;
        GPSTrackerSingleton.getInstance().setLocationCapturerListener(this);
    }

    @Override
    public void start() {
        GPSTrackerSingleton.getInstance().startCaptureLocation(mSpeedView.getContext());
    }

    @Override
    public void speedChanged(double speed) {
        mSpeedView.displaySpeed(speed);
    }

    @Override
    public void stoppedDrive(double speedAverage) {
        mSpeedView.displaySpeedAverage(speedAverage);

    }
}
