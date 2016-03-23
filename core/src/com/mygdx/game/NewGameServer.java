package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class NewGameServer {
    World world;
    Server server;

    public NewGameServer() throws IOException {
        world = new World(new Vector2(0, 0), true);
        server = new Server();
        Network.register(server);

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if(object instanceof Network.Login) {
                    java.lang.String name = ((Network.Login)object).name;
                    System.out.println(name + " connected");

                    Network.WorldTransmit worldTransmit = new Network.WorldTransmit();
                    worldTransmit.world = world;
                    worldTransmit.time = System.currentTimeMillis();
                    connection.sendTCP(worldTransmit);
                }

                if(object instanceof Network.BodyDefTransmit) {
                    BodyDef bodyDef = ((Network.BodyDefTransmit)object).bodyDef;
                    world.createBody(bodyDef);

                    Network.WorldTransmit worldTransmit = new Network.WorldTransmit();
                    worldTransmit.world = world;
                    worldTransmit.time = System.currentTimeMillis();

                    server.sendToAllTCP(worldTransmit);
                }
            }
        });

        server.bind(60777);
        server.start();
    }
}
