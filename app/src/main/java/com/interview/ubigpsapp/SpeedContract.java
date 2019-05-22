/*
  SpeedContract interface for View and Presenter

  @author Joffrey Cousin
 * @version 1.0
 */

package com.interview.ubigpsapp;

public interface SpeedContract
{
    interface View extends BaseView<Presenter>
    {
        void displaySpeed(double speed);
        void displaySpeedAverage(double speed);
    }

    interface Presenter extends BasePresenter
    {

    }
}
