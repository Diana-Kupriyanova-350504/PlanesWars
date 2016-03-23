package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class GraphicModel implements Drawable {
    boolean needRotate;

    public abstract Sprite getSprite();

    public abstract void updatePosition(Vector2 position, float angle);
    public abstract void updateAnimation();
}
