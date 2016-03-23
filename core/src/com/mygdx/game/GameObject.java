package com.mygdx.game;

public abstract class GameObject {
    public abstract GraphicModel getGraphicModel();
    public abstract PhysicsModel getPhysicsModel();

    public abstract void update();
    public GameObject() {

    }

    public GameObject(GraphicModel graphicModel, PhysicsModel physicsModel) {

    }
}
