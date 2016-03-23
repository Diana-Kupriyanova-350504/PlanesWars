package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.World;

public class Airplane extends GameObject {
    private PlaneGraphic graphicModel;
    private PlanePhysics physicsModel;

    public Airplane(String pathToAtlas, World world) {
        graphicModel = new PlaneGraphic(pathToAtlas);
        physicsModel = new PlanePhysics(world, graphicModel.getSprite().getX(), graphicModel.getSprite().getY(), graphicModel.getSprite().getWidth(), graphicModel.getSprite().getHeight());
    }

    @Override
    public GraphicModel getGraphicModel() {
        return graphicModel;
    }

    @Override
    public PhysicsModel getPhysicsModel() {
        return physicsModel;
    }

    @Override
    public void update() {
        physicsModel.update();
        graphicModel.updatePosition(physicsModel.getBody().getPosition(), physicsModel.currentAngle);
        graphicModel.updateAnimation();

        if(graphicModel.rotateAnimationFrame == 20) {
            physicsModel.currentSpeed *= -1;
            physicsModel.update();
        }

    }
}
