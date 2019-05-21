package com.interview.ubigpsapp.model;

class GPSData {
    private boolean mIsRunning;
    private long mStartTS;
    private long mStopTS;
    private boolean mIsFirstTime;

    private double mDistanceM;
    private double mCurSpeed;

    GPSData() {
        mIsFirstTime = true;
        mIsRunning = false;
        mDistanceM = 0;
        mCurSpeed = 0;
        mStartTS = 0;
        mStopTS = 0;
    }

    void addDistance(double distance){
        mDistanceM += distance;
    }

    double getAverageSpeed(){
        long drivingTime = mStopTS - mStartTS;
        double average;
        if (drivingTime <= 0){
            average = 0.0;
        } else {
            average = (mDistanceM / (drivingTime / 1000.0)) * 3.6;
        }
        return average;
    }

    void setCurSpeed(double curSpeed) {
        this.mCurSpeed = curSpeed;
    }

    boolean isFirstTime() {
        return mIsFirstTime;
    }

    void setFirstTime(boolean isFirstTime) {
        this.mIsFirstTime = isFirstTime;
    }

    boolean isRunning() {
        return mIsRunning;
    }

    void setRunning(boolean isRunning) {
        this.mIsRunning = isRunning;
    }

    void setStopTS(long timeStopped) {
        this.mStopTS += timeStopped;
    }

    double getCurSpeed() {
        return mCurSpeed;
    }

    void setStartTS(long time) {
        this.mStartTS = time;
    }
}