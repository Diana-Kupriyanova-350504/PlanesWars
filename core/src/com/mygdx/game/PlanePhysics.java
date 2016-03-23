package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class PlanePhysics extends PhysicsModel {
    private Body body;

    public PlanePhysics(World world, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((x + width/2) / ConfigGlobal.getInstance().PIXELS_TO_METERS, (y + height/2) / ConfigGlobal.getInstance().PIXELS_TO_METERS);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2 / ConfigGlobal.getInstance().PIXELS_TO_METERS, height/2 / ConfigGlobal.getInstance().PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void update() {
        if(angleInc) {
            currentAngle += ConfigGlobal.getInstance().ANGLE_STEP;
        }
        if(angleDec) {
            currentAngle -= ConfigGlobal.getInstance().ANGLE_STEP;
        }

        if(speedInc) {
            currentSpeed += ConfigGlobal.getInstance().SPEED_STEP;
        }
        if(speedDec) {
            currentSpeed -= ConfigGlobal.getInstance().SPEED_STEP;
        }

        //19.83628
        if(body.getPosition().x < 1920/ConfigGlobal.getInstance().PIXELS_TO_METERS) {
            body.setTransform(body.getPosition().x + 1920/ConfigGlobal.getInstance().PIXELS_TO_METERS, body.getPosition().y, currentAngle);
            positionUpdateRight = true;
        }

        if(body.getPosition().x > 3840/ConfigGlobal.getInstance().PIXELS_TO_METERS) {
            body.setTransform(body.getPosition().x - 1920/ConfigGlobal.getInstance().PIXELS_TO_METERS, body.getPosition().y, currentAngle);
            positionUpdateLeft = true;
        }

        if(body.getPosition().y < 1f) {
            body.setTransform(body.getPosition().x, 1f, currentAngle);
        }

        body.setTransform(body.getPosition(), currentAngle);
        body.setLinearVelocity( (float) Math.cos(currentAngle) * currentSpeed, (float) Math.sin(currentAngle) * currentSpeed );
    }
}
