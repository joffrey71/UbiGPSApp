package com.interview.ubigpsapp;

import com.interview.ubigpsapp.model.IGPSTracker;
import com.interview.ubigpsapp.presenter.SpeedPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of {@link SpeedPresenter}
 */
public class PresenterTest {


    @Mock
    private SpeedContract.View mView;
    @Mock
    private IGPSTracker mTracker;
    @Mock
    private SpeedPresenter mSpeedPresenter;

    @Before
    public void setupTasksPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mSpeedPresenter = new SpeedPresenter(mView, mTracker);
    }

    @Test
    public void presenterStart() {
        //Presenter start
        mSpeedPresenter.start();

        verify(mTracker, times(1)).startCapture(mView.getContext());
    }

    @Test
    public void presenterStop() {
        //Presenter start
        presenterStart();

        //Presenter stop
        mSpeedPresenter.stop();

        verify(mTracker, times(1)).stopCapture();
    }

    @Test
    public void presenterSpeedToView() {
        //Speed displayed
        mSpeedPresenter.speedChanged(25);

        verify(mView, times(1)).displaySpeed(25);
    }

    @Test
    public void presenterAverageSpeedToView() {
        //Speed displayed
        mSpeedPresenter.speedChanged(25);
        verify(mView, times(1)).displaySpeed(25);

        //When speed too low => test if speed average displayed
        mSpeedPresenter.speedChanged(0);
        verify(mView, times(1)).displaySpeedAverage(0);
    }
}