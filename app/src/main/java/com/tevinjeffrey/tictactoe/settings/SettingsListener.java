package com.tevinjeffrey.tictactoe.settings;


public interface SettingsListener {
    void onSettingsChanged(SettingsEnum setting);
    void onReset();
}