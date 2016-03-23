package com.mygdx.game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;


public class NetworkEntities {

    static public final int port = 60777;

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();

        kryo.register(Login.class);


        kryo.register(Player.class);
        kryo.register(AddPlayer.class);
        kryo.register(RemovePlayer.class);
        kryo.register(UpdatePlayer.class);
    }


    static public class Login {
        public String name;
    }

    static public class AddPlayer {
        public Player player;
    }

    static public class RemovePlayer {
        public int id;
    }

    static public class UpdatePlayer {
        Position position;
        Player.Type type;
        Byte other[];
    }
}
