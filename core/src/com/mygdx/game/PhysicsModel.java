package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class PhysicsModel {
    float currentSpeed;
    float currentAngle;

    boolean angleInc;
    boolean angleDec;
    boolean speedInc;
    boolean speedDec;

    boolean positionUpdateLeft;
    boolean positionUpdateRight;

    public abstract void update();
    public abstract Body getBody();
}
