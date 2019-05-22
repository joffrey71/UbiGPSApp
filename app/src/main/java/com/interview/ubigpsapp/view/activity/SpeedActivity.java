/*
  Activy for manager View / Presenter of speed infos

  This activy connects View and Presenter
  This updates speed layout

  @author Joffrey Cousin
 * @version 1.0
 */

package com.interview.ubigpsapp.view.activity;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.interview.ubigpsapp.R;
import com.interview.ubigpsapp.SpeedContract;
import com.interview.ubigpsapp.model.GPSTrackerSingleton;
import com.interview.ubigpsapp.presenter.SpeedPresenter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpeedActivity
        extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback, SpeedContract.View {

    @BindView(R.id.currentSpeed)
    TextView mCurrentSpeedTV;

    @BindView(R.id.averageSpeedBS)
    View mAverageBSView;

    @BindView(R.id.averageSpeed)
    TextView mAverageSpeed;

    //Speed presenter
    private SpeedPresenter mPresenter;

    //Bottom sheet behavior to display average speed
    BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init ButterKnife for this activy
        ButterKnife.bind(this);

        //Load BottomSheetBehavior from View
        mBottomSheetBehavior = BottomSheetBehavior.from(mAverageBSView);

        //Instanciate presenter
        mPresenter = new SpeedPresenter(this, GPSTrackerSingleton.getInstance());
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Ask permission
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        //Start presenter operations
                        mPresenter.start();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(
                            PermissionRequest permission,
                            PermissionToken token) {
                    }
                }).check();

        //Hide BottomSheet
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Stop presenter operations
        mPresenter.stop();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    //update current speed text view
    private void setCurrentSpeedTV(double speed) {
        String units = "km/h";
        SpannableString s = new SpannableString(String.format(Locale.FRANCE,
                "%.0f %s", speed, units));
        s.setSpan(new RelativeSizeSpan(0.25f),
                s.length() - units.length() - 1,
                s.length(),
                0);
        mCurrentSpeedTV.setText(s);
    }

    //update average speed text view
    private void setAverageSpeedTV(double speed) {
        String units = "km/h";
        SpannableString s = new SpannableString(String.format(Locale.FRANCE,
                "%.0f %s", speed, units));
        s.setSpan(new RelativeSizeSpan(0.25f),
                s.length() - units.length() - 1,
                s.length(), 0);
        mAverageSpeed.setText(s);
    }

    @Override
    public void displaySpeed(double speed) {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED ||
                mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        setCurrentSpeedTV(speed);
    }

    @Override
    public void displaySpeedAverage(double speed) {
        setAverageSpeedTV(speed);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
