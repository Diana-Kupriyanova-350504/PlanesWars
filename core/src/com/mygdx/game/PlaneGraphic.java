package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class PlaneGraphic extends GraphicModel {
    public Sprite sprite;
    private TextureAtlas textureAtlas;
    public int rotateAnimationFrame = 1;
    private String currentAtlasKey;
    private float currentAngle = 0.0f;

    private int planeShake[] = {31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31,
                                32, 32, 32, 32, 32, 32, 32, 32,
                                33, 33, 33, 33, 33,
                                34, 34, 34,
                                35, 35,
                                36, 36, 36,
                                37, 37, 37, 37, 37,
                                38, 38, 38, 38, 38, 38, 38, 38,
                                39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39,
                                38, 38, 38, 38, 38, 38, 38, 38,
                                37, 37, 37, 37, 37,
                                36, 36, 36,
                                35, 35,
                                34, 34, 34,
                                33, 33, 33, 33, 33,
                                32, 32, 32, 32, 32, 32, 32, 32};

    private int planeShakeIndex = 0;

    boolean leftToRight = false;

    public PlaneGraphic(String pathToAtlas) {
        textureAtlas = new TextureAtlas(Gdx.files.internal(pathToAtlas));
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion("0.0001");
        sprite = new Sprite(region);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void updatePosition(Vector2 position, float angle) {
        sprite.setPosition( (position.x * ConfigGlobal.getInstance().PIXELS_TO_METERS) - sprite.getWidth()/2,
                            (position.y * ConfigGlobal.getInstance().PIXELS_TO_METERS) - sprite.getHeight()/2 );

        currentAngle = (float)Math.toDegrees(angle);
    }

    @Override
    public void updateAnimation() {
        if(needRotate) {
            rotateAnimationFrame++;
            if (rotateAnimationFrame > 30) {
                rotateAnimationFrame = 1;
                needRotate = false;
                leftToRight = !leftToRight;
            }
            currentAtlasKey = String.format("0.%04d", rotateAnimationFrame);
        } else {
            currentAtlasKey = String.format("0.%04d", planeShake[planeShakeIndex]);
            planeShakeIndex++;
            if(planeShakeIndex > planeShake.length-1) planeShakeIndex = 0;
        }
        sprite.setRegion(textureAtlas.findRegion(currentAtlasKey));
        sprite.setFlip(leftToRight, false);
    }


    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), 1, 1, currentAngle);
        batch.draw(sprite, sprite.getX() - 1920, sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), 1, 1, currentAngle);
        batch.draw(sprite, sprite.getX() + 1920, sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), 1, 1, currentAngle);
    }
}
