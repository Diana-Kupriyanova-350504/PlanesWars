package com.mygdx.game;


import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class NewGameClient {
    Client client;
    boolean bodyDefTransmitNeeded = true;
    BodyDef bodyDef; //!!!
    World world; //!!!

    public NewGameClient(String host, int tcpPort) throws IOException {
        client = new Client();

        client.start();
        Network.register(client);


        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if(object instanceof Network.WorldTransmit) {
                    World newWorld = ((Network.WorldTransmit)object).world;
                    world = newWorld;
                    world.createBody(bodyDef);

                    if(bodyDefTransmitNeeded) {
                        Network.BodyDefTransmit bodyDefTransmit = new Network.BodyDefTransmit();
                        bodyDefTransmit.bodyDef = bodyDef;
                        bodyDefTransmit.time = System.currentTimeMillis();
                        client.sendTCP(bodyDefTransmit);
                        bodyDefTransmitNeeded = false;
                    }
                }
            }
        });

        client.connect(30000, host, tcpPort);

        Network.Login login = new Network.Login();
        login.name = "Sergey";
        client.sendTCP(login);
    }
}
