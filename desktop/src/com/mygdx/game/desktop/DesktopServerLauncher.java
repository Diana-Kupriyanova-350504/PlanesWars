package com.mygdx.game.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GameServerGdx;

import java.io.IOException;

public class DesktopServerLauncher {
    public static void main (String[] arg) throws IOException {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        new LwjglApplication(new GameServerGdx(), config);
    }

}
