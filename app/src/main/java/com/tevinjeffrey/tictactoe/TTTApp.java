package com.tevinjeffrey.tictactoe;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.LogLevel;

public class TTTApp extends Application {

    public static final String PREF_IMPOSSIBLE_DIFFICULTY = "PREF_IMPOSSIBLE_DIFFICULTY";
    public static final String PREF_PLAYER_ONE_NAME = "PREF_PLAYER_ONE_NAME";
    public static final String PREF_PLAYER_TWO_NAME = "PREF_PLAYER_TWO_NAME";
    public static final String PREF_PLAYER_ONE = "PREF_PLAYER_ONE";
    public static final String PREF_PLAYER_TWO = "PREF_PLAYER_TWO";

    public static final String FLAG_REST_GAME = "FLAG_REST_GAME";

    public static final String LOCAL = "Local";
    public static final String AI = "AI";
    public static final String[] PLAYERS = {LOCAL, AI};

    public static final String PREF_PLAYER_ONE_SCORE = "PREF_PLAYER_ONE_SCORE";
    public static final String PREF_PLAYER_TWO_SCORE = "PREF_PLAYER_TWO_SCORE";


    @Override
    public void onCreate() {
        Hawk.initWithoutEncryption(this, LogLevel.FULL);
        super.onCreate();
    }
}
