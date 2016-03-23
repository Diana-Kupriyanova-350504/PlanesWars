package com.mygdx.game;

public class ConfigGlobal {
    private static ConfigGlobal ourInstance = new ConfigGlobal();

    final float PIXELS_TO_METERS = 100f;

    final float SPEED_MIN = 0.0f;
    final float SPEED_MAX = 25f;
    final float SPEED_STEP = 0.05f;

    final float ANGLE_MIN = 0.0f;
    final float ANGLE_MAX = 2 * (float)Math.PI;
    final float ANGLE_STEP = 0.05f;


    public static ConfigGlobal getInstance() {
        return ourInstance;
    }

    private ConfigGlobal() {
    }
}