package com.mygdx.game.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GameClientGdx;

import java.io.IOException;

public class DesktopClientLauncher {
    public static void main (String[] arg) throws IOException {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        new LwjglApplication(new GameClientGdx(), config);
    }

}
