package com.t25.hbv601g.timerunner.entities;

/**
 * Created by dingo on 13.3.2017.
 */

public class Settings {
    
    private boolean mLayoutSettings;
    private boolean mNotificationsOn;
    private boolean mVibrateOn;
    private boolean mSoundOn;

    public Settings(boolean layoutSettings, boolean notificationsOn, boolean vibrateOn, boolean soundOn) {
        mLayoutSettings = layoutSettings;
        mNotificationsOn = notificationsOn;
        mVibrateOn = vibrateOn;
        mSoundOn = soundOn;
    }

    public boolean isLayoutSettings() {
        return mLayoutSettings;
    }

    public void setLayoutSettings(boolean layoutSettings) {
        mLayoutSettings = layoutSettings;
    }

    public boolean isNotificationsOn() {
        return mNotificationsOn;
    }

    public void setNotificationsOn(boolean notificationsOn) {
        mNotificationsOn = notificationsOn;
    }

    public boolean isVibrateOn() {
        return mVibrateOn;
    }

    public void setVibrateOn(boolean vibrateOn) {
        mVibrateOn = vibrateOn;
    }

    public boolean isSoundOn() {
        return mSoundOn;
    }

    public void setSoundOn(boolean soundOn) {
        mSoundOn = soundOn;
    }
}
