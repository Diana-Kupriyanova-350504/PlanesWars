package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashSet;

public class GameServer {
    Server server;
    HashSet<Player> loggedInPlayers = new HashSet();

    World world;

    public GameServer() throws IOException {

        world = new World(new Vector2(0, 0), true);


        server = new Server() {
            protected Connection newConnection () {
                return new PlayerConnection();
            }
        };
        NetworkEntities.register(server);

        server.addListener(new Listener() {
            @Override
            public void received(Connection c, Object object) {
                PlayerConnection connection = (PlayerConnection)c;
                Player player = connection.player;

                if(object instanceof NetworkEntities.Login) {
                    if(player != null) {
                        return;
                    }

                    String name = ((NetworkEntities.Login)object).name;

                    //System.out.printf("Player %s connected\n", name);

                    /*for (Player other : loggedInPlayers) {
                        if (other.name.equals(name)) {
                            c.close();
                            return;
                        }
                    }*/

                    loggedIn(connection, player);
                    return;
                }

                if(object instanceof NetworkEntities.UpdatePlayer) {
                    updatePlayer((NetworkEntities.UpdatePlayer)object, player);
                }
            }

            @Override
            public void disconnected(Connection c) {
                PlayerConnection connection = (PlayerConnection) c;
                if(connection.player != null) {
                    loggedInPlayers.remove(connection.player);
                    NetworkEntities.RemovePlayer removePlayer = new NetworkEntities.RemovePlayer();
                    removePlayer.id = connection.player.id;
                    server.sendToAllTCP(removePlayer);
                }
            }
        });
        server.bind(NetworkEntities.port);
        server.start();
    }

    void updatePlayer(NetworkEntities.UpdatePlayer u, Player player) {
        if(player == null) {
            return;
        }

    }
    void loggedIn(PlayerConnection c, Player player) {
        c.player = player;

        for(Player other : loggedInPlayers) {
            NetworkEntities.AddPlayer addPlayer = new NetworkEntities.AddPlayer();
            addPlayer.player = other;
            c.sendTCP(addPlayer);
        }

        loggedInPlayers.add(player);

        NetworkEntities.AddPlayer addPlayer = new NetworkEntities.AddPlayer();
        addPlayer.player = player;
        server.sendToAllTCP(addPlayer);
    }

    static class PlayerConnection extends Connection {
        public Player player;
    }

    public static void main(String[] args) throws IOException {
        new GameServer();
    }
}
