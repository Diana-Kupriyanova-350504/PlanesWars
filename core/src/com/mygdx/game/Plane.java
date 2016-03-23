package com.mygdx.game;

public class Plane {
    State currentState;

    float currentVelocity;
    float currentAngle;

    float currentHealth;
    float currentArmor;

    Model currentModel;

    enum Model {
        P51Mustang,
        MitsubishiA6M,
        Custom,
    };
    enum State {

    };
}
