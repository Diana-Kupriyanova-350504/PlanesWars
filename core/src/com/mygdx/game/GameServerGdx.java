package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;

import java.io.IOException;

public class GameServerGdx extends ApplicationAdapter {
    public GameServerGdx() throws IOException {
        new NewGameServer();
    }

}
