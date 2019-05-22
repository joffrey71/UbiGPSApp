/*
  Interface for Location Change Listener

  @author Joffrey Cousin
 * @version 1.0
 */

package com.interview.ubigpsapp.model;

public interface OnLocationChangeListener {
    //Provider starts capturing
    void startCapturing();

    //Provider stop capturing
    void stopCapturing();

    //Speed changed
    void speedChanged(double speed);

    //Distance changed
    void distanceChanged(double distance);
}
