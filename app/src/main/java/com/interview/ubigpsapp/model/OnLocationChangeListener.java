/*
  Interface for Location Change Listener

  @author Joffrey Cousin
 * @version 1.0
 */

package com.interview.ubigpsapp.model;

public interface OnLocationChangeListener {
    void speedChanged(double speed);
    void stoppedDrive(double speedAverage);

}
