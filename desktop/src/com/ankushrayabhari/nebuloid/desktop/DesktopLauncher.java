package com.ankushrayabhari.nebuloid.desktop;

import com.ankushrayabhari.nebuloid.client.ClientGameEngine;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ankushrayabhari.nebuloid.client.Window;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		//new LwjglApplication(new Window(), config);
		new ClientGameEngine();
	}
}
