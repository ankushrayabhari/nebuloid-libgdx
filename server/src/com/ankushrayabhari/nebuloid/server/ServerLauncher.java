package com.ankushrayabhari.nebuloid.server;

import com.badlogic.gdx.backends.headless.HeadlessApplication;

public class ServerLauncher {
	public static void main (String[] arg) {
		new HeadlessApplication(new ServerGameEngine());
	}
}
