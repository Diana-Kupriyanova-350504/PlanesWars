package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.height = Integer.parseInt(arg[1]);
		config.width = Integer.parseInt(arg[0]);

        //MyGdxGame.ServerIP = arg[2];
        //MyGdxGame.ServerPort = arg[3];

        //MyGdxGame.ServerIP = "localhost";


        //config.height = 1920;
		//config.width = 1080;

		new LwjglApplication(new MyGdxGame(), config);
	}
}
