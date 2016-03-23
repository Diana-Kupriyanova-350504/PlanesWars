package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;

import java.io.IOException;

public class GameClientGdx extends ApplicationAdapter {
    public GameClientGdx() throws IOException {
        new NewGameClient("localhost", 60777);
    }

}
