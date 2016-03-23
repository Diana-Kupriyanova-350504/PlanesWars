package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.LongMap;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Login.class);
        kryo.register(WorldTransmit.class);
        kryo.register(BodyDefTransmit.class);

        kryo.register(World.class);
        kryo.register(LongMap.class);
        kryo.register(long[].class);
        kryo.register(Object.class);
        kryo.register(Object[].class);
        kryo.register(Contact.class);
        kryo.register(float[].class);
    }

    static public class Login {
        String name;
    }

    static public class WorldTransmit {
        World world;
        long time;
    }

    static public class BodyDefTransmit {
        BodyDef bodyDef;
        long time;
    }

}
