package com.mygdx.game;

public class Player {
    public enum Type {
        Plane,
        Human,
    }

    Type type;
    Position position;

    Plane plane;

    String name;
    String ip;
    String port;
    int id;

    long lastUpdate; //System.currentTimeMillis();
}
