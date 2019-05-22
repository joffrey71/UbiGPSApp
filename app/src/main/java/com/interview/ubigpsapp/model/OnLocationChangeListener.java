/*
  Interface for Location Change Listener

  @author Joffrey Cousin
 * @version 1.0
 */

package com.interview.ubigpsapp.model;

public interface OnLocationChangeListener {
    void startCapturing();

    void stopCapturing();

    void speedChanged(double speed);
    void distanceChanged(double distance);
}
